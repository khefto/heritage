package com.hefto.heritage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class Person extends RepresentationModel<Person> {
    private Long id;
    private String name;
    private long father;
    private long mother;

    public Boolean hasFather() {
        return this.father != 0;
    }

    public Boolean hasMother() {
        return this.mother != 0;
    }

}