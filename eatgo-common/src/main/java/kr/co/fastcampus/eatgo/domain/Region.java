package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
}
