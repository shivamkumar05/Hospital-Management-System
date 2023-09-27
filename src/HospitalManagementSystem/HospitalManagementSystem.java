package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private  static  final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Shivam05@";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);

            while (true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. view Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice)
                {
                    case 1:
                        //add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //view docter
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
    {
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id");
        int doctorId = scanner.nextInt();

        System.out.println("Enter appointment date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
        {
            if(chekDoctorAvalability(doctorId, appointmentDate,connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?,?,?)";

                try {
                    java.sql.PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3,appointmentDate);

                    int affectedrows = preparedStatement.executeUpdate();

                    if(affectedrows>0)
                    {
                        System.out.println("Appointment Bocked");
                    }else {
                        System.out.println("Failed to Book Appointment");
                    }
                }catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }else {
                System.out.println("Doctor not available on this date!!");
            }
        }else {
            System.out.println("Either doctor or patient does't exist!!!");
        }
    }

    public static boolean chekDoctorAvalability(int doctorId, String appointDate, Connection connection)
    {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count == 0)
                {
                    return true;
                }
                else {
                    return false;
                }
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
