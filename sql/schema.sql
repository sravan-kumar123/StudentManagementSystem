-- Run this in SQL*Plus or SQL Developer connected to your Oracle instance
-- before starting the application.

-- =========================
-- STUDENTS TABLE
-- =========================
CREATE TABLE students (
    student_id   NUMBER PRIMARY KEY,
    name         VARCHAR2(100) NOT NULL,
    email        VARCHAR2(100) UNIQUE NOT NULL,
    phone        VARCHAR2(15),
    course       VARCHAR2(50),
    department   VARCHAR2(50),
    marks        NUMBER(5,2)
);

CREATE SEQUENCE students_seq START WITH 1 INCREMENT BY 1;

-- =========================
-- USERS TABLE (login / role based auth)
-- =========================
CREATE TABLE users (
    user_id      NUMBER PRIMARY KEY,
    username     VARCHAR2(50) UNIQUE NOT NULL,
    password     VARCHAR2(50) NOT NULL,
    role         VARCHAR2(20) NOT NULL CHECK (role IN ('ADMIN', 'FACULTY')),
    full_name    VARCHAR2(100)
);

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;

-- =========================
-- SAMPLE DATA
-- =========================
INSERT INTO users (user_id, username, password, role, full_name)
VALUES (users_seq.NEXTVAL, 'admin', 'admin123', 'ADMIN', 'System Administrator');

INSERT INTO users (user_id, username, password, role, full_name)
VALUES (users_seq.NEXTVAL, 'faculty1', 'faculty123', 'FACULTY', 'Faculty Member');

INSERT INTO students (student_id, name, email, phone, course, department, marks)
VALUES (students_seq.NEXTVAL, 'Aditya Sharma', 'aditya.sharma@example.com', '9876543210', 'B.Tech', 'CSE', 88.5);

INSERT INTO students (student_id, name, email, phone, course, department, marks)
VALUES (students_seq.NEXTVAL, 'Priya Reddy', 'priya.reddy@example.com', '9876543211', 'B.Tech', 'ECE', 92.0);

COMMIT;
