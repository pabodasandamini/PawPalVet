package dao;

import model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // CREATE: Book a new appointment
    public boolean bookAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (pet_id, vet_id, owner_id, appt_date, appt_time, reason, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'BOOKED')";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, a.getPetId());
            ps.setInt(2, a.getVetId());
            ps.setInt(3, a.getOwnerId());
            ps.setDate(4, Date.valueOf(a.getApptDate()));
            ps.setString(5, a.getApptTime());
            ps.setString(6, a.getReason());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: Get appointments for one owner (with JOIN to get pet + vet names)
    public List<Appointment> getAppointmentsByOwner(int ownerId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p.pet_name, v.name AS vet_name " +
                     "FROM appointments a " +
                     "JOIN pets p ON a.pet_id = p.pet_id " +
                     "JOIN veterinarians v ON a.vet_id = v.vet_id " +
                     "WHERE a.owner_id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // UPDATE: Cancel an appointment
    public boolean cancelAppointment(int apptId, int ownerId) {
        String sql = "UPDATE appointments SET status = 'CANCELLED' WHERE appt_id = ? AND owner_id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, apptId);
            ps.setInt(2, ownerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // Helper: convert a database row into an Appointment object
    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment a = new Appointment(
            rs.getInt("appt_id"),
            rs.getInt("pet_id"),
            rs.getInt("vet_id"),
            rs.getInt("owner_id"),
            rs.getString("appt_date"),
            rs.getString("appt_time"),
            rs.getString("reason"),
            rs.getString("status")
        );
        a.setPetName(rs.getString("pet_name"));
        a.setVetName(rs.getString("vet_name"));
        return a;
    }
}