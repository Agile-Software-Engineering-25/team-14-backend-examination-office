-- Insert Students
INSERT INTO students (id, matriculation_id, first_name, last_name, email, study_group, semester, date_of_birth)
VALUES
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', 'st1', 'John', 'Doe', 'john.doe@uni.com', 'CS-01', 3, '2000-01-15'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', 'st2', 'Jane', 'Smith', 'jane.smith@uni.com', 'CS-01', 3, '2000-02-20'),
    ('7283a092-2b64-4bfa-bf92-4242448b740a', 'st3', 'Alice', 'Johnson', 'alice.j@uni.com', 'CS-02', 5, '1999-11-30'),
    ('a9f5d8b5-2632-42b5-8520-1db4010fc80d', 'st4', 'Bob', 'Wilson', 'bob.w@uni.com', 'CS-02', 5, '1998-05-10'),
    ('be7f4234-cd28-4b29-9b09-5d1a38d3c67a', 'st5', 'Charlie', 'Brown', 'charlie.b@uni.com', 'CS-03', 1, '2001-08-25');

-- Insert Exams
INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required, exam_state
) VALUES
      ('550e8400-e29b-41d4-a716-446655440000', 'Software Engineering', 'SE101', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440001', 'Database Systems', 'DB101', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440002', 'Algorithm Analysis', 'AL201', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440003', 'Network Security', 'NS301', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440004', 'Machine Learning', 'ML401', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440005', 'Web Development', 'WD201', ${interval30}, 'Room A101', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440006', 'Operating Systems', 'OS202', ${interval30}, 'Room B201', 'KLAUSUR', 'WS2025', 6, 100, 90, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440007', 'Software Architecture', 'SA310', ${interval30}, 'Room B202', 'KLAUSUR', 'WS2025', 6, 100, 90, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440008', 'Human-Computer Interaction', 'HCI150', ${interval30}, 'Room B203', 'PRAESENTATION', 'WS2025', 4, 100, 60, 1, TRUE, 'EXAM_OPEN'),
      ('550e8400-e29b-41d4-a716-446655440009', 'Cryptography', 'CRY330', ${interval30}, 'Room B204', 'KLAUSUR', 'WS2025', 5, 100, 120, 1, TRUE, 'EXAM_OPEN');

-- Insert Student-Exam relationships
INSERT INTO student_exams (student_id, exam_id, state)
VALUES
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_OPEN'),
    ('d1c27c4f-e7d7-45b8-bc4e-6f634e7c5e8f', '550e8400-e29b-41d4-a716-446655440001', 'EXAM_PENDING'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440000', 'EXAM_GRADED'),
    ('f2a26e3f-3b50-44ac-a7f9-02fe3b41cf6a', '550e8400-e29b-41d4-a716-446655440002', 'EXAM_ACCEPTED'),
    ('7283a092-2b64-4bfa-bf92-4242448b740a', '550e8400-e29b-41d4-a716-446655440001', 'EXAM_REJECTED'),
    ('7283a092-2b64-4bfa-bf92-4242448b740a', '550e8400-e29b-41d4-a716-446655440003', 'EXAM_OPEN'),
    ('a9f5d8b5-2632-42b5-8520-1db4010fc80d', '550e8400-e29b-41d4-a716-446655440004', 'EXAM_PENDING'),
    ('be7f4234-cd28-4b29-9b09-5d1a38d3c67a', '550e8400-e29b-41d4-a716-446655440005', 'EXAM_OPEN');
