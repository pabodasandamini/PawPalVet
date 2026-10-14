package model;

// INHERITANCE: Veterinarian extends Person
public class Veterinarian extends Person {
    private String specialization;
    private double consultationFee;
    private boolean available;

    public Veterinarian(int id, String name, String phone, String email,
                        String specialization, double consultationFee, boolean available) {
        super(id, name, phone, email);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.available = available;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // POLYMORPHISM: different implementation than User
    @Override
    public void displayInfo() {
        System.out.println("Vet ID: " + getId() + " | Dr. " + getName() +
                " | " + specialization +
                " | Fee: Rs." + consultationFee +
                " | Available: " + (available ? "Yes" : "No"));
    }
}