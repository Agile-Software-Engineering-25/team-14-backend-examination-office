package com.ase.userservice.controllers;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String field, String value) {
        super("INVALID_" + field.toUpperCase());
    }
}
