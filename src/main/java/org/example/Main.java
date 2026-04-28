package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Стартуємо систему клініки ---");

        Repository<Appointment, String> repository = new InMemoryAppointmentRepository();
        AppointmentService service = new AppointmentService(repository);

        Exception.Doctor docHouse = new Exception.Doctor("d1", "Dr. Gregory House", Exception.Specialty.THERAPIST);
        Exception.Patient patientJohn = new Exception.Patient("p1", "John Doe");

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Exception.TimeSlot slot1 = new Exception.TimeSlot(
                tomorrow.withHour(10).withMinute(0),
                tomorrow.withHour(11).withMinute(0)
        );

        Exception.TimeSlot slot2 = new Exception.TimeSlot(
                tomorrow.withHour(14).withMinute(0),
                tomorrow.withHour(15).withMinute(0)
        );

        try {
            System.out.println("\nСпроба записати пацієнта...");
            Appointment app1 = service.bookAppointment("app-001", docHouse, patientJohn, slot1);
            System.out.println("Успішно! Статус: " + app1.getStatus());

            app1.changeStatus(Exception.AppointmentStatus.COMPLETED);
            System.out.println("Статус після прийому: " + app1.getStatus());

        } catch (ClinicDomainException e) {
            System.err.println("Помилка бізнес-логіки: " + e.getMessage());
        }

        try {
            System.out.println("\nСпроба записати іншого пацієнта на той самий час до того ж лікаря...");
            Exception.Patient patientJane = new Exception.Patient("p2", "Jane Smith");
            service.bookAppointment("app-002", docHouse, patientJane, slot1);
        } catch (ValidationException e) {
            System.err.println("Очікувана помилка валідації: " + e.getMessage());
        }

        try {
            service.bookAppointment("app-003", docHouse, new Exception.Patient("p3", "Alice"), slot2);

            System.out.println("\nОтримання всіх активних прийомів (відсортованих за часом):");
            List<Appointment> activeApps = service.getActiveAppointmentsSortedByDate();

            for (Appointment app : activeApps) {
                System.out.println("- Запис: " + app.getId() +
                        ", Пацієнт: " + app.getPatient().name() +
                        ", Час: " + app.getSlot().startTime());
            }
        } catch (ClinicDomainException e) {
            System.err.println("Помилка: " + e.getMessage());
        }

        System.out.println("\n--- Демонстрацію завершено ---");
    }
}
