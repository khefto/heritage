package com.hefto.heritage.services;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.exceptions.PersonNotFoundException;
import com.hefto.heritage.mappers.PersonMapper;
import com.hefto.heritage.repository.PersonEntity;
import com.hefto.heritage.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public Person findPerson(Long id) throws PersonNotFoundException {
        PersonEntity personEntity = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return personMapper.toPerson(personEntity);
    }

    @Override
    public List<Person> findAll() {
        List<PersonEntity> personEntities = repository.findAll();
        return personEntities.stream()
                .map(personMapper::toPerson)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean hasChildren(Long id) {
        PersonEntity personEntity = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        List<PersonEntity> fathers = repository.findByFather(personEntity);
        List<PersonEntity> mothers = repository.findByMother(personEntity);
        return fathers.size() + mothers.size() > 0;
    }
}
