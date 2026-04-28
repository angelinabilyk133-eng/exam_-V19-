package org.example;


import java.time.LocalDateTime;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.logging.Logger;



public class AppointmentService {
        private static final Logger logger = Logger.getLogger(AppointmentService.class.getName());
        private final Repository<Appointment, String> appointmentRepository;

        public AppointmentService(Repository<Appointment, String> appointmentRepository) {
            this.appointmentRepository = appointmentRepository;
        }

        public Appointment bookAppointment(String id, Exception.Doctor doctor, Exception.Patient patient, Exception.TimeSlot slot) {
            validateTimeSlot(slot);
            validateDoctorAvailability(doctor, slot);
            validatePatientAvailability(patient, slot);

            Appointment appointment = new Appointment(id, doctor, patient, slot);
            appointmentRepository.save(appointment);
            logger.info("Appointment booked successfully: " + id);
            return appointment;
        }

        private void validateTimeSlot(Exception.TimeSlot slot) {
            if (slot.startTime().isBefore(LocalDateTime.now())) {
                logger.warning("Attempt to book in the past");
                throw new ValidationException("Cannot book an appointment in the past.");
            }
        }

        private void validateDoctorAvailability(Exception.Doctor doctor, Exception.TimeSlot slot) {
            boolean isBusy = appointmentRepository.findAll().stream()
                    .filter(a -> a.getDoctor().id().equals(doctor.id()))
                    .filter(a -> a.getStatus() == Exception.AppointmentStatus.SCHEDULED)
                    .anyMatch(a -> slotsOverlap(a.getSlot(), slot));
            if (isBusy) {
                throw new ValidationException("Doctor is already booked for this time.");
            }
        }

        private void validatePatientAvailability(Exception.Patient patient, Exception.TimeSlot slot) {
            boolean isBusy = appointmentRepository.findAll().stream()
                    .filter(a -> a.getPatient().id().equals(patient.id()))
                    .filter(a -> a.getStatus() == Exception.AppointmentStatus.SCHEDULED)
                    .anyMatch(a -> slotsOverlap(a.getSlot(), slot));
            if (isBusy) {
                throw new ValidationException("Patient already has an appointment at this time.");
            }
        }

        private boolean slotsOverlap(Exception.TimeSlot s1, Exception.TimeSlot s2) {
            return s1.startTime().isBefore(s2.endTime()) && s2.startTime().isBefore(s1.endTime());
        }

        public List<Appointment> getActiveAppointmentsSortedByDate() {
            return appointmentRepository.findAll().stream()
                    .filter(a -> a.getStatus() == Exception.AppointmentStatus.SCHEDULED)
                    .sorted(Comparator.comparing(a -> a.getSlot().startTime()))
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

