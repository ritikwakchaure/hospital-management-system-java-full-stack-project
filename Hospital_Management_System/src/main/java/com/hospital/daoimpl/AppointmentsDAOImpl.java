package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.AppointmentsDAO;
import com.hospital.model.Appointments;
import com.hospital.util.DBConnection;

public class AppointmentsDAOImpl implements AppointmentsDAO {

    @Override
    public void save(Appointments appointment) throws SQLException {
        String sql = "INSERT INTO appointments "
                + "(patient_id, doctor_id, appointment_date, appointment_time, reason, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, appointment.getPatient_id());
            ps.setInt(2, appointment.getDoctor_id());
            ps.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            ps.setTime(4, Time.valueOf(appointment.getAppointment_time()));
            ps.setString(5, appointment.getReason());
            ps.setString(6, appointment.getStatus());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Appointments appointment) throws SQLException {
        String sql = "UPDATE appointments SET "
                + "patient_id = ?, doctor_id = ?, appointment_date = ?, "
                + "appointment_time = ?, reason = ?, status = ? "
                + "WHERE appointment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, appointment.getPatient_id());
            ps.setInt(2, appointment.getDoctor_id());
            ps.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            ps.setTime(4, Time.valueOf(appointment.getAppointment_time()));
            ps.setString(5, appointment.getReason());
            ps.setString(6, appointment.getStatus());
            ps.setInt(7, appointment.getAppointment_id());

            ps.executeUpdate();
        }
    }

    @Override
    public void updateStatus(Integer appointmentId, String status) throws SQLException {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);

            ps.executeUpdate();
        }
    }

    @Override
    public Appointments findById(Integer appointmentId) throws SQLException {
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments WHERE appointment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAppointment(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Appointments> findByPatientId(Integer patientId) throws SQLException {
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments WHERE patient_id = ? "
                + "ORDER BY appointment_date ASC, appointment_time ASC";

        List<Appointments> appointments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }

        return appointments;
    }

    @Override
    public List<Appointments> findByDoctorAndDate(Integer doctorId, java.time.LocalDate appointmentDate)
            throws SQLException {

        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments WHERE doctor_id = ? AND appointment_date = ? "
                + "ORDER BY appointment_time ASC";

        List<Appointments> appointments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setDate(2, Date.valueOf(appointmentDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }

        return appointments;
    }

    @Override
    public List<Appointments> findByStatus(String status) throws SQLException {
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments WHERE status = ?";

        List<Appointments> appointments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }

        return appointments;
    }

    @Override
    public List<Appointments> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments LIMIT ? OFFSET ?";

        List<Appointments> appointments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }

        return appointments;
    }

    private Appointments mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointments appointment = new Appointments();

        appointment.setAppointment_id(rs.getInt("appointment_id"));
        appointment.setPatient_id(rs.getInt("patient_id"));
        appointment.setDoctor_id(rs.getInt("doctor_id"));
        appointment.setAppointment_date(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointment_time(rs.getTime("appointment_time").toLocalTime());
        appointment.setReason(rs.getString("reason"));
        appointment.setStatus(rs.getString("status"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            appointment.setCreated_at(timestamp.toLocalDateTime());
        }

        return appointment;
    }
    
    @Override
    public List<Appointments> findByDoctorId(Integer doctorId) throws SQLException {

        String sql = "SELECT appointment_id, patient_id, doctor_id, appointment_date, "
                + "appointment_time, reason, status, created_at "
                + "FROM appointments WHERE doctor_id = ? "
                + "ORDER BY appointment_date ASC, appointment_time ASC";

        List<Appointments> appointments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }

        return appointments;
    }

}
