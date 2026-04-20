/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package firstexample4;

/**
 *
 * @author ANUSHYA P
 */

import java.sql.*;
import java.util.Scanner;

public class FirstExample4 {

    static final String DB_URL = "jdbc:mysql://localhost:3306/student2?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "dbms";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            while (true) {

                System.out.println("\n===== STUDENT FEEDBACK SYSTEM =====");
                System.out.println("1. View Students");
                System.out.println("2. Add Student");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");

                System.out.println("5. View Courses");
                System.out.println("6. Add Course");
                System.out.println("7. Update Course");
                System.out.println("8. Delete Course");

                System.out.println("9. View Feedback");
                System.out.println("10. Add Feedback");
                System.out.println("11. Update Feedback");
                System.out.println("12. Delete Feedback");

                System.out.println("13. Exit");

                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    // ---------- STUDENT ----------
                    case 1:
                        viewStudents(conn);
                        break;
                    case 2:
                        addStudent(conn, sc);
                        break;
                    case 3:
                        updateStudent(conn, sc);
                        break;
                    case 4:
                        deleteStudent(conn, sc);
                        break;

                    // ---------- COURSE ----------
                    case 5:
                        viewCourses(conn);
                        break;
                    case 6:
                        addCourse(conn, sc);
                        break;
                    case 7:
                        updateCourse(conn, sc);
                        break;
                    case 8:
                        deleteCourse(conn, sc);
                        break;

                    // ---------- FEEDBACK ----------
                    case 9:
                        viewFeedback(conn);
                        break;
                    case 10:
                        addFeedback(conn, sc);
                        break;
                    case 11:
                        updateFeedback(conn, sc);
                        break;
                    case 12:
                        deleteFeedback(conn, sc);
                        break;

                    case 13:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= STUDENT =================
    static void viewStudents(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Student");

        System.out.println("\n--- Student List ---");
        while (rs.next()) {
            System.out.println(rs.getInt("student_id") + " | "
                    + rs.getString("student_name") + " | "
                    + rs.getString("department") + " | "
                    + rs.getInt("year"));
        }
    }

    static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("ID: ");
        int id = sc.nextInt();

        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Department: ");
        String dept = sc.next();

        System.out.print("Year: ");
        int year = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, dept);
        ps.setInt(4, year);

        ps.executeUpdate();
        System.out.println("Student added!");
    }

    static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("New Name: ");
        String name = sc.next();

        System.out.print("New Department: ");
        String dept = sc.next();

        System.out.print("New Year: ");
        int year = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE Student SET student_name=?, department=?, year=? WHERE student_id=?");

        ps.setString(1, name);
        ps.setString(2, dept);
        ps.setInt(3, year);
        ps.setInt(4, id);

        System.out.println(ps.executeUpdate() + " row updated");
    }

    static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM Student WHERE student_id=?");
        ps.setInt(1, id);

        System.out.println(ps.executeUpdate() + " row deleted");
    }

    // ================= COURSE =================
    static void viewCourses(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Course");

        System.out.println("\n--- Course List ---");
        while (rs.next()) {
            System.out.println(rs.getInt("course_id") + " | "
                    + rs.getString("course_name") + " | "
                    + rs.getString("faculty_name"));
        }
    }

    static void addCourse(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Course ID: ");
        int id = sc.nextInt();

        System.out.print("Course Name: ");
        String name = sc.next();

        System.out.print("Faculty: ");
        String faculty = sc.next();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Course VALUES (?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, faculty);

        ps.executeUpdate();
        System.out.println("Course added!");
    }

    static void updateCourse(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Course ID: ");
        int id = sc.nextInt();

        System.out.print("New Name: ");
        String name = sc.next();

        System.out.print("New Faculty: ");
        String faculty = sc.next();

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE Course SET course_name=?, faculty_name=? WHERE course_id=?");

        ps.setString(1, name);
        ps.setString(2, faculty);
        ps.setInt(3, id);

        System.out.println(ps.executeUpdate() + " row updated");
    }

    static void deleteCourse(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Course ID: ");
        int id = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM Course WHERE course_id=?");
        ps.setInt(1, id);

        System.out.println(ps.executeUpdate() + " row deleted");
    }

    // ================= FEEDBACK =================
    static void viewFeedback(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Feedback");

        System.out.println("\n--- Feedback List ---");
        while (rs.next()) {
            System.out.println(rs.getInt("feedback_id") + " | "
                    + rs.getInt("student_id") + " | "
                    + rs.getInt("course_id") + " | "
                    + rs.getInt("rating") + " | "
                    + rs.getString("comments"));
        }
    }

    static void addFeedback(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Feedback ID: ");
        int id = sc.nextInt();

        System.out.print("Student ID: ");
        int sid = sc.nextInt();

        System.out.print("Course ID: ");
        int cid = sc.nextInt();

        System.out.print("Rating: ");
        int rating = sc.nextInt();

        System.out.print("Comments: ");
        String comments = sc.next();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Feedback VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, sid);
        ps.setInt(3, cid);
        ps.setInt(4, rating);
        ps.setString(5, comments);

        ps.executeUpdate();
        System.out.println("Feedback added!");
    }

    static void updateFeedback(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Feedback ID: ");
        int id = sc.nextInt();

        System.out.print("New Rating: ");
        int rating = sc.nextInt();

        System.out.print("New Comment: ");
        String comment = sc.next();

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE Feedback SET rating=?, comments=? WHERE feedback_id=?");

        ps.setInt(1, rating);
        ps.setString(2, comment);
        ps.setInt(3, id);

        System.out.println(ps.executeUpdate() + " row updated");
    }

    static void deleteFeedback(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Feedback ID: ");
        int id = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement("DELETE FROM Feedback WHERE feedback_id=?");
        ps.setInt(1, id);

        System.out.println(ps.executeUpdate() + " row deleted");
    }
}
