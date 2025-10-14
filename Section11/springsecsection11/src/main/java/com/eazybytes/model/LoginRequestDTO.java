package com.eazybytes.model;

public record LoginRequestDTO(String username, String password) {
    // behind the scenes record class only have getters
}
