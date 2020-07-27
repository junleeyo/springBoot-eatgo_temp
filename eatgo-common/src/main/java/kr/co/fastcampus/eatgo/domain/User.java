package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.soap.SAAJResult;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    private String password;

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
}
