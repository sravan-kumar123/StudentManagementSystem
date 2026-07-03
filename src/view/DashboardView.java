package view;

import controller.LoginController;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Landing screen after login. Admin gets full CRUD access,
 * Faculty only gets to view/search students (no add/edit/delete).
 */
public class DashboardView extends JFrame {

    public DashboardView() {
        User currentUser = LoginController.getLoggedInUser();

        setTitle("Dashboard - " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel welcome = new JLabel("Welcome, " + currentUser.getFullName());
        welcome.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel("Logged in as: " + currentUser.getRole());
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleLabel.setForeground(Color.GRAY);

        JButton btnViewStudents = new JButton("View / Search Students");
        JButton btnManageStudents = new JButton("Manage Students (Add / Edit / Delete)");
        JButton btnLogout = new JButton("Logout");

        for (JButton b : new JButton[]{btnViewStudents, btnManageStudents, btnLogout}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(260, 35));
        }

        // Faculty can look up students but cannot modify records
        btnManageStudents.setEnabled(currentUser.isAdmin());
        if (!currentUser.isAdmin()) {
            btnManageStudents.setToolTipText("Only Admin accounts can add, edit or delete students");
        }

        panel.add(welcome);
        panel.add(Box.createVerticalStrut(5));
        panel.add(roleLabel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(btnViewStudents);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnManageStudents);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnLogout);

        add(panel);

        btnViewStudents.addActionListener(e -> new StudentListView(false).setVisible(true));
        btnManageStudents.addActionListener(e -> new StudentListView(true).setVisible(true));
        btnLogout.addActionListener(e -> {
            LoginController.logout();
            dispose();
            new LoginView().setVisible(true);
        });
    }
}
