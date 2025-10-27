package com.ase.userservice.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.ase.userservice.entities.Student;

@Service
public class CertificateService {

  private static final String UNIVERSITY_NAME =
      "Provadis School of International Management and Technology";
  private static final String LOCATION = "Frankfurt am Main";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  @Autowired
  private TemplateEngine templateEngine;

  public byte[] generateCertificate(Student student, String degreeType) throws IOException {
    System.out.println("Generiere " + degreeType + "-Zeugnis für Student: " + student.getFullName()
        + " (Studiengruppe: " + student.getStudyGroup() + ")");

    String templateName = degreeType.equalsIgnoreCase("Master")
        ? "master_certificate" : "bachelor_certificate";

    Context context = createCertificateContext(student);

    String html = templateEngine.process(templateName, context);

    return generatePdfFromHtml(html);
  }

  public byte[] generateCertificates(List<Student> students) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ZipOutputStream zos = new ZipOutputStream(baos)) {
      
      for (Student student : students) {
        String degreeType = student.getStudyGroup() != null 
          &&
            student.getStudyGroup().toUpperCase().startsWith("M") ? "Master" : "Bachelor";

        byte[] pdfBytes = generateCertificate(student, degreeType);
        String fileName = String.format("%s_%s_Zeugnis.pdf",
            student.getMatriculationId(),
            student.getFullName().replace(" ", "_"));
        
        // Füge das PDF zur ZIP-Datei hinzu
        ZipEntry entry = new ZipEntry(fileName);
        zos.putNextEntry(entry);
        zos.write(pdfBytes);
        zos.closeEntry();
      }
    }

    return baos.toByteArray();
  }

  private Context createCertificateContext(Student student) {
    Context context = new Context();

    context.setVariable("universityName", UNIVERSITY_NAME);
    context.setVariable("location", LOCATION);

    context.setVariable("studentName", student.getFullName());
    context.setVariable("studentId", student.getMatriculationId());

    String translatedProgram = translateStudyProgram(student.getStudyGroup());
    context.setVariable("studyProgram", translatedProgram);

    String formattedBirthDate = formatDateOrDefault(
        student.getDateOfBirth(),
        "--.--.----"
    );
    context.setVariable("dateOfBirth", formattedBirthDate);

    String graduationDate = LocalDate.now().format(DATE_FORMATTER);
    context.setVariable("graduationDate", graduationDate);

    return context;
  }

  private String translateStudyProgram(String studyGroup) {
    if (studyGroup == null || studyGroup.isEmpty()) {
      return "Unbekannter Studiengang";
    }

    String programCode = studyGroup
        .replaceAll("[0-9\\-]+$", "")
        .trim()
        .toUpperCase();

    // Muss später noch angepasst werden, wenn klar ist, welche Studiengänge es gibt
    return switch (programCode) {
      case "BIT" -> "Informatik";
      case "MIT" -> "Informatik";
      case "BWL" -> "Betriebswirtschaftslehre";
      case "WI" -> "Wirtschaftsinformatik";
      case "WING" -> "Wirtschaftsingenieurwesen";
      case "MB" -> "Maschinenbau";
      case "ET" -> "Elektrotechnik";
      default -> programCode;
    };
  }

  private String formatDateOrDefault(LocalDate date, String defaultValue) {
    if (date == null) {
      return defaultValue;
    }
    return date.format(DATE_FORMATTER);
  }

  private byte[] generatePdfFromHtml(String html) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try {
      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(html);
      renderer.layout();
      renderer.createPDF(outputStream);
    }
    catch (Exception e) {
      throw new IOException("Fehler bei der PDF-Generierung", e);
    }

    return outputStream.toByteArray();
  }
}
