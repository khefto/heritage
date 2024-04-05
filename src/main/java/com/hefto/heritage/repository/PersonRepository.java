package com.hefto.heritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    List<PersonEntity> findByFather(PersonEntity personEntity);
    List<PersonEntity> findByMother(PersonEntity personEntity);
}
