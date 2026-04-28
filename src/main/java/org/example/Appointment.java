package org.example;

public class Appointment {
    private final String id;
    private final Exception.Doctor doctor;
    private final Exception.Patient patient;
    private final Exception.TimeSlot slot;
    private Exception.AppointmentStatus status;
    public String getId() {
        return id;
    }
    public Exception.Doctor getDoctor() {
        return doctor;
    }
    public Exception.Patient getPatient() {
        return patient;
    }
    public Exception.TimeSlot getSlot() {
        return slot;
    }
    public Exception.AppointmentStatus getStatus() {
        return status;
    }

    public Appointment(String id, Exception.Doctor doctor, Exception.Patient patient, Exception.TimeSlot slot) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.slot = slot;
        this.status = Exception.AppointmentStatus.SCHEDULED;
    }

    public void changeStatus(Exception.AppointmentStatus newStatus) {
        if (this.status == Exception.AppointmentStatus.COMPLETED && newStatus == Exception.AppointmentStatus.CANCELLED) {
            throw new Exception.LifecycleException("Cannot cancel an already completed appointment.");
        }
        if (this.status == Exception.AppointmentStatus.CANCELLED && newStatus == Exception.AppointmentStatus.COMPLETED) {
            throw new Exception.LifecycleException("Cannot complete a cancelled appointment.");
        }
        this.status = newStatus;
    }
}
