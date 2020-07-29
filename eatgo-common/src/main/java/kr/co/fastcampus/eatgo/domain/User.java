package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.soap.SAAJResult;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotEmpty
    private String email;

    @Setter
    @NotEmpty
    private String name;

    @Setter
    private String password;

    private Long restaurantId;

    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }


    @Setter
    @NotNull
    private Long level;

    public Boolean isAdmin() {
        return level >= 100;
    }

    public Boolean isActive() {
        return level > 0;
    }

    public void deativate() {
        level = 0L;
    }

    public Boolean isRestaurantOwner() {
        return this.level == 50L;
    }
}
