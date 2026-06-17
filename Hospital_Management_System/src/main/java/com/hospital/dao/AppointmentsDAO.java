package com.hospital.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hospital.model.Appointments;

public interface AppointmentsDAO {

    void save(Appointments appointment) throws SQLException;

    void update(Appointments appointment) throws SQLException;

    void updateStatus(Integer appointmentId, String status) throws SQLException;

    Appointments findById(Integer appointmentId) throws SQLException;

    List<Appointments> findByPatientId(Integer patientId) throws SQLException;

    List<Appointments> findByDoctorAndDate(Integer doctorId, LocalDate appointmentDate) throws SQLException;

    List<Appointments> findByStatus(String status) throws SQLException;

    List<Appointments> findAll(int offset, int limit) throws SQLException;
    
    List<Appointments> findByDoctorId(Integer doctorId) throws SQLException;

}
