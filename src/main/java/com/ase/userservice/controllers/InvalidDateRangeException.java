package com.ase.userservice.controllers;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
        super("INVALID_DATE_RANGE");
    }
}
