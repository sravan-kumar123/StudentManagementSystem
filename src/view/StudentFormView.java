package view;

import controller.StudentController;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentFormView extends JDialog {

    private final JTextField txtName = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtPhone = new JTextField(20);
    private final JTextField txtCourse = new JTextField(20);
    private final JTextField txtDepartment = new JTextField(20);
    private final JTextField txtMarks = new JTextField(20);

    private final StudentController studentController = new StudentController();
    private final Student editingStudent;
    private final StudentListView parentView;

    public StudentFormView(StudentListView parent, Student student) {
        super(parent, student == null ? "Add Student" : "Edit Student", true);
        this.parentView = parent;
        this.editingStudent = student;

        setSize(400, 380);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Name", "Email", "Phone", "Course", "Department", "Marks"};
        JTextField[] fields = {txtName, txtEmail, txtPhone, txtCourse, txtDepartment, txtMarks};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labels[i] + ":"), gbc);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        JButton btnSave = new JButton(student == null ? "Add" : "Update");
        JButton btnCancel = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        if (student != null) {
            txtName.setText(student.getName());
            txtEmail.setText(student.getEmail());
            txtPhone.setText(student.getPhone());
            txtCourse.setText(student.getCourse());
            txtDepartment.setText(student.getDepartment());
            txtMarks.setText(String.valueOf(student.getMarks()));
        }

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> save());
    }

    private void save() {
        double marks;
        try {
            marks = Double.parseDouble(txtMarks.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be a number.", "Invalid input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student student = (editingStudent == null) ? new Student() : editingStudent;
        student.setName(txtName.getText().trim());
        student.setEmail(txtEmail.getText().trim());
        student.setPhone(txtPhone.getText().trim());
        student.setCourse(txtCourse.getText().trim());
        student.setDepartment(txtDepartment.getText().trim());
        student.setMarks(marks);

        String error = (editingStudent == null)
                ? studentController.addStudent(student)
                : studentController.updateStudent(student);

        if (error == null) {
            parentView.loadData(null);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, error, "Could not save", JOptionPane.ERROR_MESSAGE);
        }
    }
}
