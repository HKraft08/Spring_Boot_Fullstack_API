package org.example.spring_boot_fullstack_api.controller;

import jakarta.validation.Valid;
import org.example.spring_boot_fullstack_api.dto.PersonRequest;
import org.example.spring_boot_fullstack_api.dto.PersonResponse;
import org.example.spring_boot_fullstack_api.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private final PersonService personService;

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    //Create
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request) {

        PersonResponse response = personService.createPerson(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Read one
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id) {

        return ResponseEntity.ok(personService.getPersonById(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long id, @Valid @RequestBody PersonRequest request) {

        return ResponseEntity.ok(personService.updatePerson(id, request));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {

        personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }
}
