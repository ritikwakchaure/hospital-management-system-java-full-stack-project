package com.hospital.dao;

import java.sql.SQLException;
import java.util.List;

import com.hospital.model.Patients;

public interface PatientsDAO {

    void save(Patients patient) throws SQLException;

    void update(Patients patient) throws SQLException;

    Patients findById(Integer patientId) throws SQLException;

    Patients findByUserId(Integer userId) throws SQLException;

    List<Patients> findAll(int offset, int limit) throws SQLException;

    void deleteById(Integer patientId) throws SQLException;
}
