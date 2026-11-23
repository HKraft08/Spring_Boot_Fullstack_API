package org.example.spring_boot_fullstack_api.repository;

import org.example.spring_boot_fullstack_api.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
