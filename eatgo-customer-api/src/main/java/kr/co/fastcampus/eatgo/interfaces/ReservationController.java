package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReservationController {
    //private Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Reservation resource
            ) throws URISyntaxException {

        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);

        resource.setRestaurantId(restaurantId);
        resource.setUserId(userId);
        resource.setName(name);

        //logger.info("restaurantId===>"+ restaurantId);
        //logger.info("userId===>"+ userId);
        //logger.info("name===>"+ name);

        Reservation reservation = reservationService.addReservation(restaurantId, resource);

        String url = "/restaurants/"+ restaurantId +"/reservations/"+ reservation.getId();
        return ResponseEntity.created(new URI(url)).body("{}");

    }
}
