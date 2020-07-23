package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue
    public long id;

    @NotNull
    private Long categoryId;

    @NotEmpty
    public String name;

    @NotEmpty
    public String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Review> reviews;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getInformation() {
        return this.name + " in " + this.address;
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
