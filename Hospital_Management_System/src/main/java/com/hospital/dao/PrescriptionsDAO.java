package com.hospital.dao;

import java.sql.SQLException;
import java.util.List;

import com.hospital.model.Prescriptions;

public interface PrescriptionsDAO {

    void save(Prescriptions prescription) throws SQLException;

    void update(Prescriptions prescription) throws SQLException;

    Prescriptions findById(Integer prescriptionId) throws SQLException;

    Prescriptions findByAppointmentId(Integer appointmentId) throws SQLException;

    List<Prescriptions> findAll(int offset, int limit) throws SQLException;

    void deleteById(Integer prescriptionId) throws SQLException;
}
