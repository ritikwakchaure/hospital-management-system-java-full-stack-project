package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.UsersDAO;
import com.hospital.model.Users;
import com.hospital.util.DBConnection;

public class UsersDAOImpl implements UsersDAO {

    @Override
    public void save(Users user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, phone, role, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getAddress());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Users user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, phone = ?, role = ?, address = ? WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getUser_id());

            ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(Integer userId, String encryptedPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, encryptedPassword);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }

    @Override
    public Users findById(Integer userId) throws SQLException {
        String sql = "SELECT user_id, name, email, password, phone, role, address, created_at FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Users findByEmail(String email) throws SQLException {
        String sql = "SELECT user_id, name, email, password, phone, role, address, created_at FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Users findByPhone(String phone) throws SQLException {
        String sql = "SELECT user_id, name, email, password, phone, role, address, created_at FROM users WHERE phone = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, phone);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Users> findByRole(String role) throws SQLException {
        String sql = "SELECT user_id, name, email, password, phone, role, address, created_at FROM users WHERE role = ?";

        List<Users> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, role);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }

        return users;
    }

    @Override
    public List<Users> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT user_id, name, email, password, phone, role, address, created_at FROM users LIMIT ? OFFSET ?";

        List<Users> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }

        return users;
    }

    @Override
    public void deleteById(Integer userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    private Users mapResultSetToUser(ResultSet rs) throws SQLException {
        Users user = new Users();

        user.setUser_id(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setAddress(rs.getString("address"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            user.setCreated_at(timestamp.toLocalDateTime());
        }

        return user;
    }
}
