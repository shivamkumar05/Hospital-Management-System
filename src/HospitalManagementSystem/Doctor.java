package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        String query = "select * from doctors";

        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            System.out.println("Doctor: ");
            System.out.println("+------------+------------------+---------------------+");
            System.out.println("| Doctor Id  | Name             | Specilization       |");
            System.out.println("+------------+------------------+---------------------+");

            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String specilization = resultset.getString("Specialization");
                System.out.printf("| %-10s | %-16s | %-19s |\n",id,name,specilization);
                System.out.println("+------------+------------------+---------------------+");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getDoctorById(int id) {
        String query = "select * from doctors where id = ?";
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
