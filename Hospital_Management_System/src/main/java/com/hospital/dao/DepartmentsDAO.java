package com.hospital.dao;

import java.sql.SQLException;
import java.util.List;

import com.hospital.model.Departments;

public interface DepartmentsDAO {

    void save(Departments department) throws SQLException;

    void update(Departments department) throws SQLException;

    Departments findById(Integer departmentId) throws SQLException;

    Departments findByName(String departmentName) throws SQLException;

    List<Departments> findAll() throws SQLException;

    void deleteById(Integer departmentId) throws SQLException;
}
