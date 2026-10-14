package model;

public class Pet {
    private int petId;
    private int ownerId;
    private String petName;
    private String species;
    private String breed;
    private int age;

    public Pet(int petId, int ownerId, String petName, String species, String breed, int age) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.age = age;
    }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public void displayInfo() {
        System.out.println("Pet ID: " + petId + " | " + petName + " | " +
                species + " (" + breed + ") | Age: " + age);
    }
}