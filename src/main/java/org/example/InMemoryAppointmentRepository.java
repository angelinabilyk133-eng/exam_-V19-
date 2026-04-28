package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InMemoryAppointmentRepository implements Repository<Appointment, String> {
    private final List<Appointment> db = new LinkedList<>();

    @Override
    public void save(Appointment entity) {
        db.removeIf(a -> a.getId().equals(entity.getId()));
        db.add(entity);
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return db.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Appointment> findAll() {
        return new LinkedList<>(db);
    }
}