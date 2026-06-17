package com.hospital.dao;

import java.sql.SQLException;
import java.util.List;

import com.hospital.model.Users;

public interface UsersDAO {

    void save(Users user) throws SQLException;

    void update(Users user) throws SQLException;

    void updatePassword(Integer userId, String encryptedPassword) throws SQLException;

    Users findById(Integer userId) throws SQLException;

    Users findByEmail(String email) throws SQLException;

    Users findByPhone(String phone) throws SQLException;

    List<Users> findByRole(String role) throws SQLException;

    List<Users> findAll(int offset, int limit) throws SQLException;

    void deleteById(Integer userId) throws SQLException;
}
