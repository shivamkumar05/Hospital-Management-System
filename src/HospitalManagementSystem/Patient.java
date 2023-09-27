package HospitalManagementSystem;

import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES (?,?,?)";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0) {
                System.out.println("Patient added sucessfully");
            } else {
                System.out.println("Failed to add Patient");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPatient() {
        String query = "select * from patients";

        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            System.out.println("Patients: ");
            System.out.println("+------------+---------------+----------+----------+");
            System.out.println("| Patient Id | Name          | Age      |Gender    |");
            System.out.println("+------------+---------------+----------+----------+");

            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                int age = resultset.getInt("age");
                String gender = resultset.getString("gender");
                System.out.printf("|%-12s|%-15s|%-10s|%-10s|\n", id, name, id, gender);
                System.out.println("+------------+---------------+----------+----------+");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getPatientById(int id) {
        String query = "select * from patients where id = ?";
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
