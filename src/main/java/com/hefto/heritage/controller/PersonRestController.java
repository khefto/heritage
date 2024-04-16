package com.hefto.heritage.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.hefto.heritage.dto.Person;
import com.hefto.heritage.messaging.PersonProducer;
import com.hefto.heritage.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class PersonRestController {

    @Autowired
    private PersonProducer personProducer;

    @Autowired
    private PersonService personService;

    public static final Class<PersonRestController> CONTROLLER = PersonRestController.class;

    @GetMapping("/Persons")
    ResponseEntity<List<Person>> all() {
        List<Person> all = personService.findAll();
        List<Person> persons = all.stream()
                .map(this::createPersonLinks)
                .collect(Collectors.toList());
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/Person/{id}")
    ResponseEntity<Person> one(@PathVariable Long id) {
        Person person = createPersonLinks(personService.findPerson(id));
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/Person/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        Person person = personService.findPerson(id);
        personProducer.sendMessage(id, person);
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Person createPersonLinks(Person person) {
        person.add(linkTo(methodOn(CONTROLLER).one(person.getId())).withSelfRel());
        if (person.hasMother()) {
            person.add(linkTo(methodOn(CONTROLLER).one(person.getMother())).withRel("mother"));
        }
        if (person.hasFather()) {
            person.add(linkTo(methodOn(CONTROLLER).one(person.getFather())).withRel("father"));
        }
        if (!personService.hasChildren(person.getId())) {
            person.add(linkTo(methodOn(CONTROLLER).delete(person.getId())).withRel("delete"));
        }
        return person;
    }
}