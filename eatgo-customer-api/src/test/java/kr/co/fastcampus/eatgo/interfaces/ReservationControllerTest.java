package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ReservationService reservationService;

    @Test
    public void create() throws Exception {

        String token = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0." +
                "8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .restaurantId(1L)
                .userId(1004L)
                .name("John")
                .date("2020-07-29")
                .time("18:10")
                .partySize(20)
                .build();

        given(reservationService.addReservation(eq(1L), any())).willReturn(mockReservation);

        mvc.perform(post("/restaurants/1/reservations")
                .header("Authorization", "Bearer "+ token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2020-07-29\",\"time\":\"18:10\",\"partySize\":20}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reservations/1"));

        verify(reservationService).addReservation(eq(1L), any());

    }
}