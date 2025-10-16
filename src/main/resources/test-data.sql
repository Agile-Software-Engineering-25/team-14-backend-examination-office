-- Beispielstudenten für die Zeugnistests

-- Bachelor Student
INSERT INTO students (student_id, first_name, last_name, email, study_group, semester)
VALUES ('B12345', 'Max', 'Mustermann', 'max.mustermann@example.com', 'BIT-21', 6)
ON CONFLICT (student_id) DO NOTHING;

-- Master Student
INSERT INTO students (student_id, first_name, last_name, email, study_group, semester)
VALUES ('M54321', 'Maria', 'Musterfrau', 'maria.musterfrau@example.com', 'MIT-23', 4)
ON CONFLICT (student_id) DO NOTHING;

-- Beispielprüfungen (optional)
INSERT INTO exams (title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required)
VALUES 
('Programmierung 1', 'CS101', '2025-06-15 10:00:00', 'Raum 101', 'Klausur', 'SS2025', 6, 100, 120, 1, false),
('Datenbanken', 'CS203', '2025-07-20 14:00:00', 'Raum 202', 'Klausur', 'SS2025', 6, 100, 120, 1, false),
('Softwareentwicklung', 'CS301', '2025-08-10 09:00:00', 'Raum 305', 'Projekt', 'SS2025', 8, 100, 180, 1, true)
ON CONFLICT (module_code, exam_date, attempt_number) DO NOTHING;

-- Verknüpfung von Studenten mit Prüfungen (optional)
INSERT INTO exam_students (exam_id, student_id)
SELECT e.id, s.id
FROM exams e, students s
WHERE e.module_code = 'CS101' AND s.student_id = 'B12345'
ON CONFLICT DO NOTHING;

INSERT INTO exam_students (exam_id, student_id)
SELECT e.id, s.id
FROM exams e, students s
WHERE e.module_code = 'CS203' AND s.student_id = 'B12345'
ON CONFLICT DO NOTHING;

INSERT INTO exam_students (exam_id, student_id)
SELECT e.id, s.id
FROM exams e, students s
WHERE e.module_code = 'CS301' AND s.student_id = 'M54321'
ON CONFLICT DO NOTHING;