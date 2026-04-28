package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFileProcessor<T> {
    private static final Logger logger = Logger.getLogger(AbstractFileProcessor.class.getName());

    public List<T> loadFromFile(String filePath) {
        List<T> results = new LinkedList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    results.add(parseLine(line));
                }
            }
            logger.info("Successfully loaded data from " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read file: " + filePath, e);
            throw new ClinicDomainException("File processing error", e);
        }
        return results;
    }

    protected abstract T parseLine(String line);
}
