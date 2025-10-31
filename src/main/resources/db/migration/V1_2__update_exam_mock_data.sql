-- Update exam dates for exams with UUIDs ending in 00 to 14 to be in the past
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '30 days' WHERE id = '550e8400-e29b-41d4-a716-446655440000';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '29 days' WHERE id = '550e8400-e29b-41d4-a716-446655440001';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '28 days' WHERE id = '550e8400-e29b-41d4-a716-446655440002';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '27 days' WHERE id = '550e8400-e29b-41d4-a716-446655440003';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '26 days' WHERE id = '550e8400-e29b-41d4-a716-446655440004';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '25 days' WHERE id = '550e8400-e29b-41d4-a716-446655440005';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '24 days' WHERE id = '550e8400-e29b-41d4-a716-446655440006';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '23 days' WHERE id = '550e8400-e29b-41d4-a716-446655440007';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '22 days' WHERE id = '550e8400-e29b-41d4-a716-446655440008';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '21 days' WHERE id = '550e8400-e29b-41d4-a716-446655440009';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '20 days' WHERE id = '550e8400-e29b-41d4-a716-446655440010';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '19 days' WHERE id = '550e8400-e29b-41d4-a716-446655440011';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '18 days' WHERE id = '550e8400-e29b-41d4-a716-446655440012';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '17 days' WHERE id = '550e8400-e29b-41d4-a716-446655440013';
UPDATE exams SET exam_date = CURRENT_TIMESTAMP - INTERVAL '16 days' WHERE id = '550e8400-e29b-41d4-a716-446655440014';

-- Update ASE module to have 3 exams with weights: 40%, 50%, 10%
UPDATE exams SET weight_per_cent = 40, exam_type = 'KLAUSUR', title = 'Agile Software Engineering - Schriftliche Prüfung' 
WHERE id = '550e8400-e29b-41d4-a716-446655440015';

-- Insert additional ASE exams (WAB and Presentation)
INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required, weight_per_cent
) VALUES
    ('550e8400-e29b-41d4-a716-446655440020', 'Agile Software Engineering - WAB', 'ASE', CURRENT_TIMESTAMP + INTERVAL '30 days', 'Online', 'PROJEKT', 'WS2025', 5, 100, 0, 1, TRUE, 50),
    ('550e8400-e29b-41d4-a716-446655440021', 'Agile Software Engineering - Präsentation', 'ASE', CURRENT_TIMESTAMP + INTERVAL '48 days', 'Room A101', 'PRAESENTATION', 'WS2025', 5, 100, 30, 1, TRUE, 10);

-- Update IKHT module to have 2 exams with weights: 50%, 50%
UPDATE exams SET weight_per_cent = 50, exam_type = 'PRAESENTATION', title = 'IKHT - Präsentation' 
WHERE id = '550e8400-e29b-41d4-a716-446655440018';

-- Insert additional IKHT exam (Report)
INSERT INTO exams (
    id, title, module_code, exam_date, room, exam_type, semester, ects, max_points, duration, attempt_number, file_upload_required, weight_per_cent
) VALUES
    ('550e8400-e29b-41d4-a716-446655440022', 'IKHT - Gruppenbericht', 'IKHT', CURRENT_TIMESTAMP + INTERVAL '20 days', 'Online', 'PROJEKT', 'WS2025', 5, 100, 0, 1, TRUE, 50);

-- Set file_upload_required to FALSE for TIRA module exam
UPDATE exams SET file_upload_required = FALSE WHERE module_code = 'TIRA';
