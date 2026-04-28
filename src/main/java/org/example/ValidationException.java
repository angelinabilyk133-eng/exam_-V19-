package org.example;

public class ValidationException extends ClinicDomainException {
    public ValidationException(String message) {
        super(message);
    }
}
