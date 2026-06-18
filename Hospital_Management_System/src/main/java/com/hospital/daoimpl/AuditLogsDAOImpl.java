package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.AuditLogsDAO;
import com.hospital.model.AuditLogs;
import com.hospital.util.DBConnection;

public class AuditLogsDAOImpl implements AuditLogsDAO {

    @Override
    public void save(AuditLogs log) throws SQLException {
        String sql = "INSERT INTO audit_logs "
                + "(user_id, action_type, table_name, record_id, metadata) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, log.getUser_id());
            ps.setString(2, log.getAction_type());
            ps.setString(3, log.getTable_name());

            if (log.getRecord_id() != null) {
                ps.setInt(4, log.getRecord_id());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setString(5, log.getMetadata());

            ps.executeUpdate();
        }
    }

    @Override
    public AuditLogs findById(Integer logId) throws SQLException {
        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs WHERE log_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, logId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAuditLog(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<AuditLogs> findByUserId(Integer userId) throws SQLException {
        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs WHERE user_id = ? "
                + "ORDER BY action_timestamp DESC";

        List<AuditLogs> logs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        }

        return logs;
    }

    @Override
    public List<AuditLogs> findByActionType(String actionType) throws SQLException {
        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs WHERE action_type = ? "
                + "ORDER BY action_timestamp DESC";

        List<AuditLogs> logs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, actionType);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        }

        return logs;
    }

    @Override
    public List<AuditLogs> findByTableName(String tableName) throws SQLException {
        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs WHERE table_name = ? "
                + "ORDER BY action_timestamp DESC";

        List<AuditLogs> logs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tableName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        }

        return logs;
    }

    @Override
    public List<AuditLogs> findByDateRange(java.time.LocalDateTime start,
                                           java.time.LocalDateTime end) throws SQLException {

        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs WHERE action_timestamp BETWEEN ? AND ? "
                + "ORDER BY action_timestamp DESC";

        List<AuditLogs> logs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        }

        return logs;
    }

    @Override
    public List<AuditLogs> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT log_id, user_id, action_type, table_name, record_id, "
                + "action_timestamp, metadata "
                + "FROM audit_logs "
                + "ORDER BY action_timestamp DESC "
                + "LIMIT ? OFFSET ?";

        List<AuditLogs> logs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        }

        return logs;
    }

    private AuditLogs mapResultSetToAuditLog(ResultSet rs) throws SQLException {
        AuditLogs log = new AuditLogs();

        log.setLog_id(rs.getInt("log_id"));
        log.setUser_id(rs.getInt("user_id"));
        log.setAction_type(rs.getString("action_type"));
        log.setTable_name(rs.getString("table_name"));

        int recordId = rs.getInt("record_id");
        if (!rs.wasNull()) {
            log.setRecord_id(recordId);
        }

        Timestamp timestamp = rs.getTimestamp("action_timestamp");
        if (timestamp != null) {
            log.setAction_timestamp(timestamp.toLocalDateTime());
        }

        log.setMetadata(rs.getString("metadata"));

        return log;
    }
}
