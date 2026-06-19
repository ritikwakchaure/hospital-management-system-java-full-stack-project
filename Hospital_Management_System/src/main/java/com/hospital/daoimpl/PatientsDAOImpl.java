package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.PatientsDAO;
import com.hospital.model.Patients;
import com.hospital.util.DBConnection;

public class PatientsDAOImpl implements PatientsDAO {

    @Override
    public void save(Patients patient) throws SQLException {
        String sql = "INSERT INTO patients "
                + "(user_id, date_of_birth, gender, blood_group) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patient.getUser_id());
            ps.setDate(2, Date.valueOf(patient.getDate_of_birth()));
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getBlood_group());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Patients patient) throws SQLException {
        String sql = "UPDATE patients SET "
                + "user_id = ?, date_of_birth = ?, gender = ?, blood_group = ? "
                + "WHERE patient_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patient.getUser_id());
            ps.setDate(2, Date.valueOf(patient.getDate_of_birth()));
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getBlood_group());
            ps.setInt(5, patient.getPatient_id());

            ps.executeUpdate();
        }
    }

    @Override
    public Patients findById(Integer patientId) throws SQLException {
        String sql = "SELECT patient_id, user_id, date_of_birth, gender, blood_group, created_at "
                   + "FROM patients WHERE patient_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Patients findByUserId(Integer userId) throws SQLException {
        String sql = "SELECT patient_id, user_id, date_of_birth, gender, blood_group, created_at "
                   + "FROM patients WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Patients> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT patient_id, user_id, date_of_birth, gender, blood_group, created_at "
                   + "FROM patients ORDER BY patient_id ASC LIMIT ? OFFSET ?";

        List<Patients> patients = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapResultSetToPatient(rs));
                }
            }
        }

        return patients;
    }

    @Override
    public void deleteById(Integer patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE patient_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }

    private Patients mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patients patient = new Patients();

        patient.setPatient_id(rs.getInt("patient_id"));
        patient.setUser_id(rs.getInt("user_id"));
        patient.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate());
        patient.setGender(rs.getString("gender"));
        patient.setBlood_group(rs.getString("blood_group"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            patient.setCreated_at(timestamp.toLocalDateTime());
        }

        return patient;
    }
}
