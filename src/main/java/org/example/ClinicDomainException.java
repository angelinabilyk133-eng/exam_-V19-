package org.example;

public class ClinicDomainException extends RuntimeException {
    public ClinicDomainException(String message) {
        super(message);
    }

    public ClinicDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
