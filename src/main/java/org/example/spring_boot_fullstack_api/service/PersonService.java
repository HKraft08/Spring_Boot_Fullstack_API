package org.example.spring_boot_fullstack_api.service;

import jakarta.transaction.Transactional;
import org.example.spring_boot_fullstack_api.dto.PersonRequest;
import org.example.spring_boot_fullstack_api.dto.PersonResponse;
import org.example.spring_boot_fullstack_api.entity.Person;
import org.example.spring_boot_fullstack_api.exception.DuplicateResourceException;
import org.example.spring_boot_fullstack_api.exception.ResourceNotFoundException;
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

        if (personRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A person with this email already exists");
        }

        Person person = new Person();

        person.setName(request.name());
        person.setEmail(request.email());

        Person savePerson = personRepository.save(person);

        return mapToResponse(savePerson);
    }

    //Update
    @Transactional
    public PersonResponse updatePerson(Long id, PersonRequest request) {

        Person person = findPersonByIdOrThrow(id);

        boolean emailUsedByAnotherPerson = personRepository.existsByEmailAndIdNot(
                request.email(),
                id
        );

        if (emailUsedByAnotherPerson) {
            throw new DuplicateResourceException("Another person is already using this email");
        }

        person.setName(request.name());
        person.setEmail(request.email());

        Person updatedPerson = personRepository.save(person);

        return mapToResponse(updatedPerson);
    }

    private Person findPersonByIdOrThrow(Long id) {

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with ID: " + id));
    }

    private PersonResponse mapToResponse(Person person) {

        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getEmail()
        );
    }
}
