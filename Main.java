import admin.Admin;
import patient.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        while (true){
            System.out.print(
                    "Health-Care Appointment System\n\nEnter\n1. Admin Login\n2. Patient Login\n3. Patient Signup\n4. Exit\n\nChoice: "
            );
            int choice = Integer.parseInt(reader.readLine());

            if (choice == 1){
                System.out.print("Enter admin username: ");
                String username = reader.readLine();
                System.out.print("Enter admin password: ");
                String password = reader.readLine();

                var admin = Admin.Login(username, password);
                admin.AdminMenu();
            }
            else if (choice == 2){
                System.out.print("Enter patient username: ");
                String username = reader.readLine();
                System.out.print("Enter patient password: ");
                String password = reader.readLine();

                var patient = Admin.getPatient(username);
                Patient.patientMenu(patient);
            }
            else if (choice == 3){
                System.out.print("Enter new username: ");
                var username = reader.readLine();
                System.out.print("Enter new password: ");
                var pass = reader.readLine();

                Patient.CreateAccount(username, pass);
            }
            else if (choice == 4){
                break;
            }
            else{
                System.err.print("Invalid choice. Please try again...");
            }
        }
    }
}
