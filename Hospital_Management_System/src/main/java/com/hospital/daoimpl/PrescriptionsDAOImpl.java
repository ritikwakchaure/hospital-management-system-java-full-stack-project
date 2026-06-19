package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.PrescriptionsDAO;
import com.hospital.model.Prescriptions;
import com.hospital.util.DBConnection;

public class PrescriptionsDAOImpl implements PrescriptionsDAO {

    @Override
    public void save(Prescriptions prescription) throws SQLException {
        String sql = "INSERT INTO prescriptions "
                + "(appointment_id, diagnosis, medicines, instructions) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, prescription.getAppointment_id());
            ps.setString(2, prescription.getDiagnosis());
            ps.setString(3, prescription.getMedicines());
            ps.setString(4, prescription.getInstructions());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Prescriptions prescription) throws SQLException {
        String sql = "UPDATE prescriptions SET "
                + "appointment_id = ?, diagnosis = ?, medicines = ?, instructions = ? "
                + "WHERE prescription_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, prescription.getAppointment_id());
            ps.setString(2, prescription.getDiagnosis());
            ps.setString(3, prescription.getMedicines());
            ps.setString(4, prescription.getInstructions());
            ps.setInt(5, prescription.getPrescription_id());

            ps.executeUpdate();
        }
    }

    @Override
    public Prescriptions findById(Integer prescriptionId) throws SQLException {
        String sql = "SELECT prescription_id, appointment_id, diagnosis, medicines, "
                   + "instructions, issued_at "
                   + "FROM prescriptions WHERE prescription_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, prescriptionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrescription(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Prescriptions findByAppointmentId(Integer appointmentId) throws SQLException {
        String sql = "SELECT prescription_id, appointment_id, diagnosis, medicines, "
                   + "instructions, issued_at "
                   + "FROM prescriptions WHERE appointment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrescription(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Prescriptions> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT prescription_id, appointment_id, diagnosis, medicines, "
                   + "instructions, issued_at "
                   + "FROM prescriptions "
                   + "ORDER BY issued_at DESC "
                   + "LIMIT ? OFFSET ?";

        List<Prescriptions> prescriptions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prescriptions.add(mapResultSetToPrescription(rs));
                }
            }
        }

        return prescriptions;
    }

    @Override
    public void deleteById(Integer prescriptionId) throws SQLException {
        String sql = "DELETE FROM prescriptions WHERE prescription_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, prescriptionId);
            ps.executeUpdate();
        }
    }

    private Prescriptions mapResultSetToPrescription(ResultSet rs) throws SQLException {
        Prescriptions prescription = new Prescriptions();

        prescription.setPrescription_id(rs.getInt("prescription_id"));
        prescription.setAppointment_id(rs.getInt("appointment_id"));
        prescription.setDiagnosis(rs.getString("diagnosis"));
        prescription.setMedicines(rs.getString("medicines"));
        prescription.setInstructions(rs.getString("instructions"));

        Timestamp timestamp = rs.getTimestamp("issued_at");
        if (timestamp != null) {
            prescription.setIssued_at(timestamp.toLocalDateTime());
        }

        return prescription;
    }
}
