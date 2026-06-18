package com.hospital.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.DoctorsDAO;
import com.hospital.model.Doctors;
import com.hospital.util.DBConnection;

public class DoctorsDAOImpl implements DoctorsDAO {

    @Override
    public void save(Doctors doctor) throws SQLException {
        String sql = "INSERT INTO doctors "
                + "(user_id, department_id, qualification, experience_years, consultation_fee) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctor.getUser_id());
            ps.setInt(2, doctor.getDepartment_id());
            ps.setString(3, doctor.getQualification());
            ps.setInt(4, doctor.getExperience_years());
            ps.setBigDecimal(5, doctor.getConsultation_fee());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Doctors doctor) throws SQLException {
        String sql = "UPDATE doctors SET "
                + "user_id = ?, department_id = ?, qualification = ?, "
                + "experience_years = ?, consultation_fee = ? "
                + "WHERE doctor_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctor.getUser_id());
            ps.setInt(2, doctor.getDepartment_id());
            ps.setString(3, doctor.getQualification());
            ps.setInt(4, doctor.getExperience_years());
            ps.setBigDecimal(5, doctor.getConsultation_fee());
            ps.setInt(6, doctor.getDoctor_id());

            ps.executeUpdate();
        }
    }

    @Override
    public Doctors findById(Integer doctorId) throws SQLException {
        String sql = "SELECT doctor_id, user_id, department_id, qualification, "
                + "experience_years, consultation_fee, created_at "
                + "FROM doctors WHERE doctor_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoctor(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Doctors findByUserId(Integer userId) throws SQLException {
        String sql = "SELECT doctor_id, user_id, department_id, qualification, "
                + "experience_years, consultation_fee, created_at "
                + "FROM doctors WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoctor(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Doctors> findByDepartmentId(Integer departmentId) throws SQLException {
        String sql = "SELECT doctor_id, user_id, department_id, qualification, "
                + "experience_years, consultation_fee, created_at "
                + "FROM doctors WHERE department_id = ? "
                + "ORDER BY experience_years DESC";

        List<Doctors> doctors = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapResultSetToDoctor(rs));
                }
            }
        }

        return doctors;
    }

    @Override
    public List<Doctors> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT doctor_id, user_id, department_id, qualification, "
                + "experience_years, consultation_fee, created_at "
                + "FROM doctors ORDER BY doctor_id ASC LIMIT ? OFFSET ?";

        List<Doctors> doctors = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapResultSetToDoctor(rs));
                }
            }
        }

        return doctors;
    }

    @Override
    public void deleteById(Integer doctorId) throws SQLException {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.executeUpdate();
        }
    }

    private Doctors mapResultSetToDoctor(ResultSet rs) throws SQLException {
        Doctors doctor = new Doctors();

        doctor.setDoctor_id(rs.getInt("doctor_id"));
        doctor.setUser_id(rs.getInt("user_id"));
        doctor.setDepartment_id(rs.getInt("department_id"));
        doctor.setQualification(rs.getString("qualification"));
        doctor.setExperience_years(rs.getInt("experience_years"));

        BigDecimal fee = rs.getBigDecimal("consultation_fee");
        doctor.setConsultation_fee(fee);

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            doctor.setCreated_at(timestamp.toLocalDateTime());
        }

        return doctor;
    }
}
