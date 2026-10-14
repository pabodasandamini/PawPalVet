package main;

import dao.*;
import model.*;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();
    private static final PetDAO petDAO = new PetDAO();
    private static final VetDAO vetDAO = new VetDAO();
    private static final AppointmentDAO apptDAO = new AppointmentDAO();
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   PAWPAL - VET APPOINTMENT SYSTEM");
        System.out.println("=====================================");

        if (!login()) {
            System.out.println("Login failed. Exiting.");
            DatabaseConnection.closeConnection();
            return;
        }

        while (true) {
            printMenu();
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1: registerPet();     break;
                case 2: viewMyPets();      break;
                case 3: viewVets();        break;
                case 4: bookAppointment(); break;
                case 5: viewMyAppts();     break;
                case 6: cancelAppt();      break;
                case 7:
                    System.out.println("Goodbye!");
                    DatabaseConnection.closeConnection();
                    return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- LOGIN ----------
    private static boolean login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        currentUser = userDAO.authenticate(u, p);

        if (currentUser != null) {
            System.out.println("\nWelcome, " + currentUser.getName() + "!");
            currentUser.displayInfo();   // POLYMORPHISM in action
            return true;
        }
        return false;
    }

    // ---------- MENU ----------
    private static void printMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Register a Pet");
        System.out.println("2. View My Pets");
        System.out.println("3. View Available Veterinarians");
        System.out.println("4. Book an Appointment");
        System.out.println("5. View My Appointments");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. Exit");
        System.out.println("==========================");
    }

    // ---------- FEATURES ----------
    private static void registerPet() {
        System.out.println("\n--- REGISTER PET ---");
        System.out.print("Pet Name: ");
        String name = sc.nextLine();
        System.out.print("Species (Dog/Cat): ");
        String sp = sc.nextLine();
        System.out.print("Breed: ");
        String breed = sc.nextLine();
        int age = readInt("Age: ");

        Pet pet = new Pet(0, currentUser.getId(), name, sp, breed, age);
        System.out.println(petDAO.addPet(pet) ? "Pet registered!" : "Failed.");
    }

    private static void viewMyPets() {
        List<Pet> pets = petDAO.getPetsByOwner(currentUser.getId());
        System.out.println("\n--- MY PETS ---");
        if (pets.isEmpty()) System.out.println("No pets yet.");
        else for (Pet p : pets) p.displayInfo();
    }

    private static void viewVets() {
        List<Veterinarian> vets = vetDAO.getAvailableVets();
        System.out.println("\n--- AVAILABLE VETERINARIANS ---");
        if (vets.isEmpty()) System.out.println("No vets available.");
        else for (Veterinarian v : vets) v.displayInfo();   // POLYMORPHISM
    }

    private static void bookAppointment() {
        System.out.println("\n--- BOOK APPOINTMENT ---");

        List<Pet> pets = petDAO.getPetsByOwner(currentUser.getId());
        if (pets.isEmpty()) { System.out.println("Register a pet first."); return; }
        viewMyPets();

        int petId = readInt("Enter Pet ID: ");

        List<Veterinarian> vets = vetDAO.getAvailableVets();
        if (vets.isEmpty()) { System.out.println("No vets available."); return; }
        viewVets();

        int vetId = readInt("Enter Vet ID: ");
        if (vetDAO.getVetById(vetId) == null) {
            System.out.println("Invalid vet ID."); return;
        }

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Time (e.g., 10:30 AM): ");
        String time = sc.nextLine();
        System.out.print("Reason: ");
        String reason = sc.nextLine();

        Appointment a = new Appointment(0, petId, vetId, currentUser.getId(),
                                        date, time, reason, "BOOKED");

        System.out.println(apptDAO.bookAppointment(a) ? "Appointment booked!" : "Failed.");
    }

    private static void viewMyAppts() {
        List<Appointment> list = apptDAO.getAppointmentsByOwner(currentUser.getId());
        System.out.println("\n--- MY APPOINTMENTS ---");
        if (list.isEmpty()) System.out.println("No appointments.");
        else for (Appointment a : list) a.displayInfo();
    }

    private static void cancelAppt() {
        viewMyAppts();
        int id = readInt("Enter Appointment ID to cancel: ");
        System.out.println(apptDAO.cancelAppointment(id, currentUser.getId())
                ? "Cancelled." : "Failed (check ID).");
    }

    // ---------- HELPERS ----------
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) { System.out.print("Invalid. " + prompt); sc.next(); }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }
}