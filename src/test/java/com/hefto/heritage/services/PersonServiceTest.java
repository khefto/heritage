package com.hefto.heritage.services;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.exceptions.PersonNotFoundException;
import com.hefto.heritage.repository.PersonEntity;
import com.hefto.heritage.repository.PersonRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        PersonEntity henrik = new PersonEntity().id(1L).name("Henrik");
        PersonEntity frederik = new PersonEntity().id(2L).name("Frederik").father(henrik);

        Mockito.when(personRepository.findById(henrik.id()))
                .thenReturn(Optional.of(henrik));

        Mockito.when(personRepository.findById(frederik.id()))
                .thenReturn(Optional.of(frederik));

        Mockito.when(personRepository.findAll())
                .thenReturn(List.of(henrik, frederik));

        Mockito.when(personRepository.findByFather(henrik))
                .thenReturn(List.of(frederik));

    }


    @Test
    public void whenValidId_thenPersonShouldBeFound() {
        Person person = personService.findPerson(1L);
        assertTrue(person.getName() == "Henrik");
    }

    @Test
    public void whenInValidId_thenException() {
        Exception exception = assertThrows(PersonNotFoundException.class, () ->
                personService.findPerson(99L));
    }

    @Test
    public void whenAll_thenListAll() {
        List<Person> all = personService.findAll();
        assertTrue(all.size() == 2);
    }

    @Test
    public void whenHasChildren_thenTrue() {
        Boolean hasChildren = personService.hasChildren(1L);
        assertTrue(hasChildren);
    }

    @Test
    public void whenHasNoChildren_thenFalse() {
        Boolean hasChildren = personService.hasChildren(2L);
        assertFalse(hasChildren);
    }
}