package com.hefto.heritage.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    PersonEntity henrik;
    PersonEntity margrethe;
    PersonEntity frederik;

    @BeforeEach
    public void setUp() {
        henrik = new PersonEntity().name("Henrik");
        margrethe = new PersonEntity().name("Margrethe");
        frederik = new PersonEntity().name("Frederik").father(henrik).mother(margrethe);
        entityManager.persist(henrik);
        entityManager.persist(margrethe);
        entityManager.persist(frederik);
        entityManager.flush();
    }

    @Test
    public void whenFindByFatherWithChildren_thenReturnChildren() {
        List<PersonEntity> found = personRepository.findByFather(henrik);
        assertTrue(found.size() == 1);
        assertTrue(found.get(0) == frederik);
    }

    @Test
    public void whenFindByFatherWithNoChildren_thenReturnNoChildren() {
        List<PersonEntity> found = personRepository.findByFather(frederik);
        assertTrue(found.isEmpty());
    }

    @Test
    public void whenFindByMotherWithChildren_thenReturnChildren() {
        List<PersonEntity> found = personRepository.findByMother(margrethe);
        assertTrue(found.size() == 1);
        assertTrue(found.get(0) == frederik);
    }

    @Test
    public void whenFindByMotherWithNoChildren_thenReturnNoChildren() {
        List<PersonEntity> found = personRepository.findByMother(frederik);
        assertTrue(found.isEmpty());
    }

}