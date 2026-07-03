package model;

/**
 * Simple POJO that represents a row in the STUDENTS table.
 */
public class Student {

    private int studentId;
    private String name;
    private String email;
    private String phone;
    private String course;
    private String department;
    private double marks;

    public Student() {
    }

    public Student(int studentId, String name, String email, String phone,
                    String course, String department, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.department = department;
        this.marks = marks;
    }

    // Used when inserting a new student (id not generated yet)
    public Student(String name, String email, String phone, String course,
                    String department, double marks) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name + " (" + course + ")";
    }
}
