package controller;

import dao.StudentDAO;
import model.Student;

import java.util.List;

/**
 * Middle layer between the Swing views and StudentDAO.
 * Also does basic field validation before hitting the DB.
 */
public class StudentController {

    private final StudentDAO studentDAO = new StudentDAO();

    public String addStudent(Student student) {
        String error = validate(student);
        if (error != null) {
            return error;
        }
        return studentDAO.addStudent(student) ? null : "Could not save the student. Check the logs / DB connection.";
    }

    public String updateStudent(Student student) {
        String error = validate(student);
        if (error != null) {
            return error;
        }
        return studentDAO.updateStudent(student) ? null : "Could not update the student.";
    }

    public boolean deleteStudent(int studentId) {
        return studentDAO.deleteStudent(studentId);
    }

    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentDAO.searchStudents(keyword.trim());
    }

    private String validate(Student s) {
        if (s.getName() == null || s.getName().trim().isEmpty()) {
            return "Name cannot be empty.";
        }
        if (s.getEmail() == null || !s.getEmail().contains("@")) {
            return "Enter a valid email address.";
        }
        if (s.getPhone() == null || !s.getPhone().matches("\\d{10}")) {
            return "Phone number must be 10 digits.";
        }
        if (s.getMarks() < 0 || s.getMarks() > 100) {
            return "Marks must be between 0 and 100.";
        }
        return null;
    }
}
