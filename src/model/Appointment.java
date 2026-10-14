package model;

public class Appointment {
    private int apptId;
    private int petId;
    private int vetId;
    private int ownerId;
    private String apptDate;
    private String apptTime;
    private String reason;
    private String status;

    // Extra fields for display (populated by JOIN queries)
    private String petName;
    private String vetName;

    public Appointment(int apptId, int petId, int vetId, int ownerId,
                       String apptDate, String apptTime, String reason, String status) {
        this.apptId = apptId;
        this.petId = petId;
        this.vetId = vetId;
        this.ownerId = ownerId;
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.reason = reason;
        this.status = status;
    }

    public int getApptId() { return apptId; }
    public void setApptId(int apptId) { this.apptId = apptId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getVetId() { return vetId; }
    public void setVetId(int vetId) { this.vetId = vetId; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getApptDate() { return apptDate; }
    public void setApptDate(String apptDate) { this.apptDate = apptDate; }

    public String getApptTime() { return apptTime; }
    public void setApptTime(String apptTime) { this.apptTime = apptTime; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getVetName() { return vetName; }
    public void setVetName(String vetName) { this.vetName = vetName; }

    public void displayInfo() {
        System.out.println("Appt #" + apptId +
                " | Pet: " + petName +
                " | Vet: Dr. " + vetName +
                " | " + apptDate + " at " + apptTime +
                " | " + reason +
                " | Status: " + status);
    }
}