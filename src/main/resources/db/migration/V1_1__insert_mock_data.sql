-- Insert Students
INSERT INTO students (id, matriculation_id, first_name, last_name, email, study_group, semester, date_of_birth)
VALUES
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', 'D725', 'John', 'Doe', 'john.doe@uni.com', 'CS-01', 3, '2000-01-15'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', 'D755', 'Jane', 'Smith', 'jane.smith@uni.com', 'CS-01', 3, '2000-02-20'),
    ('7283a092-2b64-4bfa-bf92-4242448b740a', 'D735', 'Alice', 'Johnson', 'alice.j@uni.com', 'CS-02', 5, '1999-11-30'),
    ('a9f5d8b5-2632-42b5-8520-1db4010fc80d', 'D729', 'Bob', 'Wilson', 'bob.w@uni.com', 'CS-02', 5, '1998-05-10'),
    ('be7f4234-cd28-4b29-9b09-5d1a38d3c67a', 'D726', 'Charlie', 'Brown', 'charlie.b@uni.com', 'CS-03', 1, '2001-08-25');

INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required
) VALUES

      ('550e8400-e29b-41d4-a716-446655440000', 'Mathematik 1', 'MATH1', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440001', 'Sprachkompetenz Englisch', 'ENG1', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440002', 'Grundlagen der Informatik', 'INFO1', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440003', 'Lerntechniken und wissenschaftliches Arbeiten', 'LWA', ${interval30}, 'Room A101', 'PRAESENTATION', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440004', 'Programmierung', 'PROG1', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440005', 'Algorithmen und Datenstrukturen', 'ADS', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440006', 'Fortgeschrittene Programmierung', 'PROG2', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440007', 'Kommunikationskompetenz', 'KOMM', ${interval30}, 'Room A101', 'PRAESENTATION', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440008', 'Mathematik 2', 'MATH2', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440009', 'Theoretische Informatik', 'TI', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440010', 'Betriebssysteme', 'OS', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440011', 'Datenmodellierung und Datenbanken', 'DB', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440012', 'Informationssicherheit', 'SEC', ${interval30}, 'Room A101', 'MUENDLICH', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440013', 'Netze und verteilte Systeme', 'NET', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440014', 'Projektmanagement', 'PM', ${interval30}, 'Room A101', 'PROJEKT', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440015', 'Agile Software Engineering und Softwaretechnik', 'ASE', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440016', 'Data Analytics & Big Data', 'DABD', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440017', 'Human-Computer-Interaction', 'HCI', ${interval30}, 'Room A101', 'PRAESENTATION', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440018', 'Interkulturelle Kommunikation und heterogene Teams', 'IKHT', ${interval30}, 'Room A101', 'MUENDLICH', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440019', 'Technische Informatik und Rechnerarchitekturen und XAAS', 'TIRA', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN');

INSERT INTO student_exams (student_id, exam_id, state)
VALUES
    -- EXAM_UUID1 ("550e8400-e29b-41d4-a716-446655440000")  Mathematics Final Exam
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),
    ('7283a092-2b64-4bfa-bf92-4242448b740a', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),
    ('a9f5d8b5-2632-42b5-8520-1db4010fc80d', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),
    ('be7f4234-cd28-4b29-9b09-5d1a38d3c67a', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),

    -- EXAM_UUID2 ("550e8400-e29b-41d4-a716-446655440001")  Physics Midterm
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440001', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440001', 'EXAM_GRADED'),

    -- EXAM_UUID3 ("550e8400-e29b-41d4-a716-446655440002")  Computer Science Project
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440002', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440002', 'EXAM_GRADED'),

    -- EXAM_UUID4 ("550e8400-e29b-41d4-a716-446655440003")  Chemistry Lab Exam
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440003', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440003', 'EXAM_GRADED'),

    -- EXAM_UUID5 ("550e8400-e29b-41d4-a716-446655440004")  Software Development Project
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440004', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440004', 'EXAM_GRADED'),

    -- EXAM_UUID6 ("550e8400-e29b-41d4-a716-446655440005")  History
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440005', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440005', 'EXAM_GRADED'),

    -- EXAM_UUID7 ("5509012a-e29b-41d4-a716-446655440005")  Project 1
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '5509012a-e29b-41d4-a716-446655440005', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '5509012a-e29b-41d4-a716-446655440005', 'EXAM_GRADED');

-- EXAM_UUID8 ("550e8322-e29b-41d4-a716-446655440005")  Project 2
-- (no assigned students in DummyData)
