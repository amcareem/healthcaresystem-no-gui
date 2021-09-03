package admin;

import patient.Patient;
import patient.PatientStatus;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Admin {
    private static Admin admin = new Admin();
    private Admin(){};
    private static HashMap<String, Patient> patients = new HashMap<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static Admin Login(String username, String password){
        if (username.compareTo("amcareem") == 0 && password.compareTo("amc2001") == 0)
            return admin;

        return null;
    }

    public void AddPatientDetails() throws IOException {
        /**
         *  Ask the user for patient name, patient age, problem description,
         *  doctor. Create a new object with the data and store it in the
         *  patients "HashMap"
         */
        System.out.print("Enter patient name: ");
        String patientName = reader.readLine();

        System.out.print("Enter patient age: ");
        int patientAge = Integer.parseInt(reader.readLine());

        System.out.print("Enter problem description: ");
        String problemDescription = reader.readLine();

        System.out.print("Enter doctor name: ");
        String doctorName = reader.readLine();

        patients.put(patientName, new Patient(
           patientName, patientAge,
           problemDescription, doctorName
        ));

        System.out.print("Booking Status: Pending\nPlease add payment to reserve booking.");
    }

    public void AddPayment(String name){
        /**
         *  Ask the user for payment details and add those details
         *  to the Patient object.
         */
        var patient = getPatient(name);

        if (patient != null){
            System.out.print("Enter payment method (1. Cash, 2. Card): ");
            int status = 0;
            try {
                status = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                PatientStatus patientStatus = PatientStatus.Booked;
                String cardNo = null;

                if (status == 2){
                    System.out.print("Enter Card# ");
                    cardNo = reader.readLine();
                    patient.AddPaymentData(String.format("card:%s", cardNo));
                }
                else
                    patient.AddPaymentData("cash");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            System.err.print(String.format("No patient by the name '%s' exists!", name));
        }
    }

    public void removePatient(String name){
        if (patients.containsKey(name)) {
            patients.remove(name);
            System.out.print("Patient removed...");
        }
    }

    public void editPatient(String name){
        /**
         *  Ask the user for new data and use that
         *  data to edit the patient details.
         */
        if (patients.containsKey(name)){
            try{
                var patient = patients.get(name);

                System.out.print("Enter patient age: ");
                int patientAge = Integer.parseInt(reader.readLine());

                System.out.print("Enter problem description: ");
                String problemDescription = reader.readLine();

                System.out.print("Enter doctor name: ");
                String doctorName = reader.readLine();

                patient.setAge(patientAge);
                patient.setDoctor(doctorName);
                patient.setProblem(problemDescription);

                System.out.println("Data edited...");
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else
            System.err.print("No patient with this name exists!");
    }

    public static Patient getPatient(String name){
        /**
         *  Get a patient object by patient name.
         */
        return patients.get(name);
    }

    public void printAllPatients(){
        /**
         *  Go through every patient object in the HashMap
         *  and print the data.
         */
        for (var p: patients.entrySet()){
            System.out.println("*".repeat(50));
            var patient = p.getValue();
            System.out.println("Patient Name: " + patient.getName());
            System.out.println("Patient Age: " + patient.getAge());
            System.out.println("Patient Problem: " + patient.getProblem());
            System.out.println("Patient Doctor: " + patient.getDoctor());
            System.out.println("Patient Status: " + patient.getStatus());
            System.out.println("*".repeat(50));
        }
    }

    public void AdminMenu() throws IOException {
        while (true){
            System.out.print(
                    "\nAdmin Panel:\n1. Add patient details.\n2. Add payment details" +
                    "\n3. Remove patient\n4. Edit patient details\n5. List all patients\n6. Go back\n\n Choice: "
            );
            int choice = Integer.parseInt(reader.readLine());

            if (choice == 1) this.AddPatientDetails();
            else if (choice == 2) {
                System.out.print("Enter patient name: ");
                String patientName = reader.readLine();
                this.AddPayment(patientName);
            }
            else if (choice == 3) {
                System.out.print("Enter patient name: ");
                String patientName = reader.readLine();
                this.removePatient(patientName);
            }
            else if (choice == 4){
                System.out.print("Enter patient name: ");
                String patientName = reader.readLine();
                this.editPatient(patientName);
            }
            else if (choice == 5) printAllPatients();
            else if (choice == 6) break;
            else System.err.println("Invalid choice. Try again...");
        }
    }
}
