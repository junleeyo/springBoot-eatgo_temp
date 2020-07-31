package kr.co.fastcampus.eatgo.application;

import jdk.internal.dynalink.linker.LinkerServices;
import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    public void gwtReservations(){

        List<Reservation> reservation = reservationService.getReservations(1L);
        //assertThat(reservation.getName(), is("John"));

        verify(reservationRepository).findAllByRestaurantId(1L);

    }

}