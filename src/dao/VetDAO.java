package dao;

import model.Veterinarian;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VetDAO {

    // READ: Get all available vets
    public List<Veterinarian> getAvailableVets() {
        List<Veterinarian> list = new ArrayList<>();
        String sql = "SELECT * FROM veterinarians WHERE available = TRUE";
        try (Statement st = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Veterinarian(
                    rs.getInt("vet_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("specialization"),
                    rs.getDouble("consultation_fee"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // READ: Get one vet by ID
    public Veterinarian getVetById(int vetId) {
        String sql = "SELECT * FROM veterinarians WHERE vet_id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, vetId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Veterinarian(
                    rs.getInt("vet_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("specialization"),
                    rs.getDouble("consultation_fee"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}