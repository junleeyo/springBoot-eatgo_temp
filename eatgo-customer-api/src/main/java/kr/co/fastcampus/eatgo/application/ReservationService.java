package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(Long restaurantId, Reservation resource) {

        Reservation reservation = Reservation.builder()
                .restaurantId(restaurantId)
                .userId(resource.getUserId())
                .name(resource.getName())
                .date(resource.getDate())
                .time(resource.getTime())
                .partySize(resource.getPartySize())
                .build();

        return reservationRepository.save(reservation);
    }
}
