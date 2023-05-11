package com.corenttech.engine.repository;

import com.corenttech.engine.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepositoryImpl repository = null;

    @BeforeEach
    void setUp() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void findByIdBlock() {
        Person person = repository.findById(1).block();
        System.out.println("person = " + person);
    }

    @Test
    void findByIdSubscribe() {
        repository.findById(1).subscribe(person -> {
            System.out.println("person = " + person);
        });
    }

    @Test
    void findByIdMap() {
        repository.findById(1).map(person -> person.getFirstName()).subscribe(firstName -> {
            System.out.println("firstName = " + firstName);
        });
    }

    @Test
    void personFluxBlock() {
        Person person = repository.findAll().blockFirst();
        System.out.println("person = " + person);
    }

    @Test
    void personFluxSubscribe() {
        repository.findAll().subscribe(person -> {
            System.out.println("person = " + person);
        });
    }

    @Test
    void personFluxSubscribeAsList() {
        repository.findAll().collectList().subscribe(list -> {
            list.forEach(person -> {
                System.out.println("person = " + person);
            });
        });
    }

    @Test
    void personMonoFromFlux() {
        repository.findAll().filter(person -> person.getId() ==  5).next().subscribe(person -> {
            System.out.println("person = " + person);
        });
    }
    @Test
    void personMonoFromFluxWithException() {
        repository.findAll().filter(person -> person.getId() ==  5).single().doOnError(throwable -> {
            System.out.println("Something went wrong");
        }).onErrorReturn(Person.builder().id(3).build()).subscribe(person -> {
            System.out.println("person = " + person);
        });
    }

}