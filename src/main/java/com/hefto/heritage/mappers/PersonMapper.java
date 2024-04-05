package com.hefto.heritage.mappers;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.repository.PersonEntity;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {

    public Person toPerson(PersonEntity personEntity) {
        return new Person(
                personEntity.id(),
                personEntity.name(),
                personEntity.father() == null ? 0 : personEntity.father().id(),
                personEntity.mother() == null ? 0 : personEntity.mother().id());
    }
}
