package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {


    ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation(){

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .restaurantId(1L)
                .userId(1004L)
                .name("John")
                .date("2020-07-29")
                .time("18:10")
                .partySize(20)
                .build();

        given(reservationRepository.save(any())).willReturn(mockReservation);

        Reservation reservation = reservationService.addReservation(1L, mockReservation);
        assertThat(reservation.getName(), is("John"));

        verify(reservationRepository).save(any());

    }

}