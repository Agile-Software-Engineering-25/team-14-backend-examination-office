-- Update exam dates for exams with UUIDs ending in 00 to 14 to be in the past (14.11.2024 23:59)
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440000';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440001';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440002';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440003';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440004';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440005';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440006';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440007';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440008';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440009';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440010';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440011';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440012';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440013';
UPDATE exams SET exam_date = TIMESTAMP '2024-11-14 23:59:00' WHERE id = '550e8400-e29b-41d4-a716-446655440014';

-- Update ASE module to have 3 exams with weights: 40%, 50%, 10%
UPDATE exams SET weight_per_cent = 40, exam_type = 'KLAUSUR', title = 'Agile Software Engineering - Schriftliche Prüfung'
WHERE id = '550e8400-e29b-41d4-a716-446655440015';

-- Insert additional ASE exams (WAB and Presentation)
INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required, weight_per_cent
) VALUES
      ('550e8400-e29b-41d4-a716-446655440020', 'Agile Software Engineering - WAB', 'ASE', TIMESTAMP '2025-10-07 23:59:00', 'Online', 'PROJEKT', 'WS2025', 5, 100, 0, 1, TRUE, 50),
      ('550e8400-e29b-41d4-a716-446655440021', 'Agile Software Engineering - Präsentation', 'ASE', TIMESTAMP '2025-11-17 15:00:00', 'Room A101', 'PRAESENTATION', 'WS2025', 5, 100, 30, 1, TRUE, 10);

-- Update IKHT module to have 2 exams with weights: 50%, 50%
UPDATE exams SET weight_per_cent = 50, exam_type = 'PRAESENTATION', title = 'IKHT - Präsentation'
WHERE id = '550e8400-e29b-41d4-a716-446655440018';

-- Insert additional IKHT exam (Report)
INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required, weight_per_cent
) VALUES
    ('550e8400-e29b-41d4-a716-446655440022', 'IKHT - Gruppenbericht', 'IKHT', TIMESTAMP '2025-09-07 23:59:00', 'Online', 'PROJEKT', 'WS2025', 5, 100, 0, 1, TRUE, 50);

-- Set file_upload_required to FALSE for TIRA module exam
UPDATE exams SET file_upload_required = FALSE WHERE module_code = 'TIRA';
