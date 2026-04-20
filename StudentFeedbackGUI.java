/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstexample4;

/**
 *
 * @author ANUSHYA P
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentFeedbackGUI {

    static final String DB_URL = "jdbc:mysql://localhost:3306/student2"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "dbms";

    JFrame frame;

    public StudentFeedbackGUI() {
        frame = new JFrame("Student Feedback System");
        frame.setSize(900, 500);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Student", studentPanel());
        tabs.add("Course", coursePanel());
        tabs.add("Feedback", feedbackPanel());

        frame.add(tabs);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ================= STUDENT =================
    JPanel studentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Name", "Department", "Year"}, 0);
        JTable table = new JTable(model);

        JPanel btn = new JPanel();
        JButton load = new JButton("Load");
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");

        btn.add(load); btn.add(add); btn.add(update); btn.add(delete);

        // LOAD
        load.addActionListener(e -> {
            model.setRowCount(0);
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement s = c.createStatement()) {
                ResultSet rs = s.executeQuery("SELECT * FROM Student");
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt(1), rs.getString(2),
                            rs.getString(3), rs.getInt(4)
                    });
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        // ADD
        add.addActionListener(e -> {
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(JOptionPane.showInputDialog("ID")));
                ps.setString(2, JOptionPane.showInputDialog("Name"));
                ps.setString(3, JOptionPane.showInputDialog("Department"));
                ps.setInt(4, Integer.parseInt(JOptionPane.showInputDialog("Year")));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        // UPDATE (ALL COLUMNS)
        update.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE Student SET student_name=?, department=?, year=? WHERE student_id=?");

                ps.setString(1, JOptionPane.showInputDialog("New Name"));
                ps.setString(2, JOptionPane.showInputDialog("New Department"));
                ps.setInt(3, Integer.parseInt(JOptionPane.showInputDialog("New Year")));
                ps.setInt(4, Integer.parseInt(model.getValueAt(r, 0).toString()));

                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        // DELETE
        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("DELETE FROM Student WHERE student_id=?");
                ps.setInt(1, Integer.parseInt(model.getValueAt(r, 0).toString()));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        return panel;
    }

    // ================= COURSE =================
    JPanel coursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Course Name", "Faculty"}, 0);
        JTable table = new JTable(model);

        JPanel btn = new JPanel();
        JButton load = new JButton("Load");
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");

        btn.add(load); btn.add(add); btn.add(update); btn.add(delete);

        load.addActionListener(e -> {
            model.setRowCount(0);
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement s = c.createStatement()) {
                ResultSet rs = s.executeQuery("SELECT * FROM Course");
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt(1), rs.getString(2), rs.getString(3)
                    });
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        add.addActionListener(e -> {
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO Course VALUES (?, ?, ?)");
                ps.setInt(1, Integer.parseInt(JOptionPane.showInputDialog("Course ID")));
                ps.setString(2, JOptionPane.showInputDialog("Course Name"));
                ps.setString(3, JOptionPane.showInputDialog("Faculty Name"));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        update.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE Course SET course_name=?, faculty_name=? WHERE course_id=?");

                ps.setString(1, JOptionPane.showInputDialog("New Course Name"));
                ps.setString(2, JOptionPane.showInputDialog("New Faculty"));
                ps.setInt(3, Integer.parseInt(model.getValueAt(r, 0).toString()));

                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("DELETE FROM Course WHERE course_id=?");
                ps.setInt(1, Integer.parseInt(model.getValueAt(r, 0).toString()));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        return panel;
    }

    // ================= FEEDBACK =================
    JPanel feedbackPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Student ID", "Course ID", "Rating", "Comments"}, 0);
        JTable table = new JTable(model);

        JPanel btn = new JPanel();
        JButton load = new JButton("Load");
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");

        btn.add(load); btn.add(add); btn.add(update); btn.add(delete);

        load.addActionListener(e -> {
            model.setRowCount(0);
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement s = c.createStatement()) {
                ResultSet rs = s.executeQuery("SELECT * FROM Feedback");
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt(1), rs.getInt(2), rs.getInt(3),
                            rs.getInt(4), rs.getString(5)
                    });
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        add.addActionListener(e -> {
            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO Feedback VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(JOptionPane.showInputDialog("Feedback ID")));
                ps.setInt(2, Integer.parseInt(JOptionPane.showInputDialog("Student ID")));
                ps.setInt(3, Integer.parseInt(JOptionPane.showInputDialog("Course ID")));
                ps.setInt(4, Integer.parseInt(JOptionPane.showInputDialog("Rating")));
                ps.setString(5, JOptionPane.showInputDialog("Comments"));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        update.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE Feedback SET student_id=?, course_id=?, rating=?, comments=? WHERE feedback_id=?");

                ps.setInt(1, Integer.parseInt(JOptionPane.showInputDialog("New Student ID")));
                ps.setInt(2, Integer.parseInt(JOptionPane.showInputDialog("New Course ID")));
                ps.setInt(3, Integer.parseInt(JOptionPane.showInputDialog("New Rating")));
                ps.setString(4, JOptionPane.showInputDialog("New Comments"));
                ps.setInt(5, Integer.parseInt(model.getValueAt(r, 0).toString()));

                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        delete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS)) {
                PreparedStatement ps = c.prepareStatement("DELETE FROM Feedback WHERE feedback_id=?");
                ps.setInt(1, Integer.parseInt(model.getValueAt(r, 0).toString()));
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        new StudentFeedbackGUI();
    }
}
