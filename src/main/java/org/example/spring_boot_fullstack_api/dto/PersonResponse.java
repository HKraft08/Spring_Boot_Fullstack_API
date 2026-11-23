package org.example.spring_boot_fullstack_api.dto;

public record PersonResponse(
        Long id,
        String name,
        String email
) {
}
