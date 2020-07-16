package kr.co.fastcampus.eatgo.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    @Column(nullable = true)
    private String name;

    //@Transient
    @ColumnDefault("false")
    private Boolean destory;
}
