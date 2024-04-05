package com.hefto.heritage.repository;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    @ManyToOne
    private PersonEntity father;
    @ManyToOne
    private PersonEntity mother;

    PersonEntity(String name, PersonEntity father, PersonEntity mother) {
        this.name = name;
        this.father = father;
        this.mother = mother;
    }
}