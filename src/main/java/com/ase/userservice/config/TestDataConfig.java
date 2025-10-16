package com.ase.userservice.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ase.userservice.entities.Student;
import com.ase.userservice.repositories.StudentRepository;

@Configuration
public class TestDataConfig {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Initialisiert Testdaten für die Entwicklungsumgebung.
     * Wird nur ausgeführt, wenn das aktive Profil "dev" oder "test" ist.
     */
    @Bean
    @Profile({"dev", "test", "default"})
    public CommandLineRunner initTestData() {
        return args -> {
            // Prüfen, ob bereits Studenten vorhanden sind
            if (studentRepository.count() == 0) {
                System.out.println("Initialisiere Testdaten...");

                // Bachelor Student
                Student bachelorStudent = new Student(
                        "B12345", 
                        "Max", 
                        "Mustermann", 
                        "max.mustermann@example.com", 
                        "BIT-21", 
                        6,
                        LocalDate.of(2000, 5, 15));
                studentRepository.save(bachelorStudent);

                // Master Student
                Student masterStudent = new Student(
                        "M54321", 
                        "Maria", 
                        "Musterfrau", 
                        "maria.musterfrau@example.com", 
                        "MIT-23", 
                        4,
                        LocalDate.of(1998, 8, 22));
                studentRepository.save(masterStudent);

                System.out.println("Testdaten initialisiert: 2 Studenten erstellt");
            } else {
                System.out.println("Testdaten-Initialisierung übersprungen - Datenbank enthält bereits Einträge");
            }
        };
    }
}