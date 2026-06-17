package com.hospital.dao;

import java.sql.SQLException;
import java.util.List;

import com.hospital.model.Doctors;

public interface DoctorsDAO {

    void save(Doctors doctor) throws SQLException;

    void update(Doctors doctor) throws SQLException;

    Doctors findById(Integer doctorId) throws SQLException;

    Doctors findByUserId(Integer userId) throws SQLException;

    List<Doctors> findByDepartmentId(Integer departmentId) throws SQLException;

    List<Doctors> findAll(int offset, int limit) throws SQLException;

    void deleteById(Integer doctorId) throws SQLException;
}
