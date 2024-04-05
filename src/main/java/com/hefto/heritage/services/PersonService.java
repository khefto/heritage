package com.hefto.heritage.services;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.exceptions.PersonNotFoundException;

import java.util.List;

public interface PersonService {
    Person findPerson(Long id) throws PersonNotFoundException;

    List<Person> findAll();

    void delete(Long id);

    Boolean hasChildren(Long id);
}
