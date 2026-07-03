package view;

import controller.StudentController;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentListView extends JFrame {

    private final StudentController studentController = new StudentController();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtSearch;
    private final boolean editable;

    public StudentListView(boolean editable) {
        this.editable = editable;

        setTitle(editable ? "Manage Students" : "View Students");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Search");
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Phone", "Course", "Department", "Marks"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        btnAdd.setEnabled(editable);
        btnEdit.setEnabled(editable);
        btnDelete.setEnabled(editable);

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnRefresh);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText()));
        txtSearch.addActionListener(e -> loadData(txtSearch.getText()));
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData(null);
        });
        btnAdd.addActionListener(e -> new StudentFormView(this, null).setVisible(true));
        btnEdit.addActionListener(e -> editSelected());
        btnDelete.addActionListener(e -> deleteSelected());

        loadData(null);
    }

    public void loadData(String keyword) {
        tableModel.setRowCount(0);
        List<Student> students = studentController.searchStudents(keyword);
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getStudentId(), s.getName(), s.getEmail(), s.getPhone(),
                    s.getCourse(), s.getDepartment(), s.getMarks()
            });
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to edit first.");
            return;
        }
        int studentId = (int) tableModel.getValueAt(row, 0);
        Student student = studentController.getStudentById(studentId);
        new StudentFormView(this, student).setVisible(true);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete first.");
            return;
        }
        int studentId = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete this student record? This cannot be undone.",
                "Confirm delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = studentController.deleteStudent(studentId);
            if (deleted) {
                loadData(txtSearch.getText());
            } else {
                JOptionPane.showMessageDialog(this, "Could not delete the record.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
