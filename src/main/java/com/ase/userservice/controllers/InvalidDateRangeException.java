package com.ase.userservice.controller;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
        super("INVALID_DATE_RANGE");
    }
}
