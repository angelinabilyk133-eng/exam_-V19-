package org.example;

public class Exception extends RuntimeException {

    public enum Specialty {DENTIST, CARDIOLOGIST, THERAPIST}

    public enum AppointmentStatus {SCHEDULED, COMPLETED, CANCELLED}

    public record Doctor(String id, String name, Specialty specialty) {
    }

    public record Patient(String id, String name) {
    }

    public record TimeSlot(java.time.LocalDateTime startTime, java.time.LocalDateTime endTime) {
    }

    public static class LifecycleException extends ClinicDomainException {
        public LifecycleException(String message) {
            super(message);
        }
    }
}
