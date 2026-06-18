package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.DepartmentsDAO;
import com.hospital.model.Departments;
import com.hospital.util.DBConnection;

public class DepartmentsDAOImpl implements DepartmentsDAO {

    @Override
    public void save(Departments department) throws SQLException {
        String sql = "INSERT INTO departments (department_name, description) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, department.getDepartment_name());
            ps.setString(2, department.getDescription());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Departments department) throws SQLException {
        String sql = "UPDATE departments SET department_name = ?, description = ? WHERE department_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, department.getDepartment_name());
            ps.setString(2, department.getDescription());
            ps.setInt(3, department.getDepartment_id());

            ps.executeUpdate();
        }
    }

    @Override
    public Departments findById(Integer departmentId) throws SQLException {
        String sql = "SELECT department_id, department_name, description, created_at "
                   + "FROM departments WHERE department_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDepartment(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Departments findByName(String departmentName) throws SQLException {
        String sql = "SELECT department_id, department_name, description, created_at "
                   + "FROM departments WHERE department_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, departmentName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDepartment(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Departments> findAll() throws SQLException {
        String sql = "SELECT department_id, department_name, description, created_at "
                   + "FROM departments ORDER BY department_name ASC";

        List<Departments> departments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        }

        return departments;
    }

    @Override
    public void deleteById(Integer departmentId) throws SQLException {
        String sql = "DELETE FROM departments WHERE department_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, departmentId);
            ps.executeUpdate();
        }
    }

    private Departments mapResultSetToDepartment(ResultSet rs) throws SQLException {
        Departments department = new Departments();

        department.setDepartment_id(rs.getInt("department_id"));
        department.setDepartment_name(rs.getString("department_name"));
        department.setDescription(rs.getString("description"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            department.setCreated_at(timestamp.toLocalDateTime());
        }

        return department;
    }
}
