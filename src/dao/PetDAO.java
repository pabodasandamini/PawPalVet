package dao;

import model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    // CREATE: Add a new pet
    public boolean addPet(Pet pet) {
        String sql = "INSERT INTO pets (owner_id, pet_name, species, breed, age) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, pet.getOwnerId());
            ps.setString(2, pet.getPetName());
            ps.setString(3, pet.getSpecies());
            ps.setString(4, pet.getBreed());
            ps.setInt(5, pet.getAge());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: Get all pets belonging to one owner
    public List<Pet> getPetsByOwner(int ownerId) {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE owner_id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pet(
                    rs.getInt("pet_id"),
                    rs.getInt("owner_id"),
                    rs.getString("pet_name"),
                    rs.getString("species"),
                    rs.getString("breed"),
                    rs.getInt("age")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // DELETE: Remove a pet
    public boolean deletePet(int petId) {
        String sql = "DELETE FROM pets WHERE pet_id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, petId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}