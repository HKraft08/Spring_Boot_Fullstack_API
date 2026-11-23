package org.example.spring_boot_fullstack_api.service;

import jakarta.transaction.Transactional;
import org.example.spring_boot_fullstack_api.dto.PersonRequest;
import org.example.spring_boot_fullstack_api.dto.PersonResponse;
import org.example.spring_boot_fullstack_api.entity.Person;
import org.example.spring_boot_fullstack_api.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    //Create
    @Transactional
    public PersonResponse createPerson(PersonRequest request) {

        Person person = new Person();

        person.setName(request.name());
        person.setEmail(request.email());

        Person savePerson = personRepository.save(person);

        return mapToResponse(savePerson);
    }

    private PersonResponse mapToResponse(Person person) {

        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getEmail()
        );
    }
}
