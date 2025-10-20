-- 1. Create exams table
CREATE TABLE exams (
                       id UUID PRIMARY KEY,
                       title VARCHAR(160) NOT NULL,
                       module_code VARCHAR(40) NOT NULL,
                       exam_date TIMESTAMP NOT NULL,
                       room VARCHAR(80) NOT NULL,
                       exam_type VARCHAR(20) NOT NULL,
                       semester VARCHAR(20) NOT NULL,
                       ects INT NOT NULL,
                       max_points INT NOT NULL,
                       duration INT NOT NULL,
                       attempt_number INT NOT NULL,
                       file_upload_required BOOLEAN NOT NULL,
                       exam_state VARCHAR(20) NOT NULL DEFAULT 'EXAM_OPEN',
                       CONSTRAINT uk_exam_modcode_date_attempt UNIQUE(module_code, exam_date, attempt_number)
);

-- 2. Create exam_tools collection table
CREATE TABLE exam_tools (
                            exam_id UUID NOT NULL,
                            tool VARCHAR(60) NOT NULL,
                            CONSTRAINT fk_exam_tools_exam FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE
);

-- 1. Create students table
CREATE TABLE students (
                          id UUID PRIMARY KEY,
                          matriculation_id VARCHAR(20) NOT NULL UNIQUE,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          study_group VARCHAR(100),
                          semester INT
);

-- 2. Create student_exams join table
CREATE TABLE student_exams (
                               student_id UUID NOT NULL,
                               exam_id UUID NOT NULL,
                               state VARCHAR(20) NOT NULL DEFAULT 'EXAM_OPEN',
                               PRIMARY KEY (student_id, exam_id),
                               CONSTRAINT fk_student_exams_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                               CONSTRAINT fk_student_exams_exam FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE
);
