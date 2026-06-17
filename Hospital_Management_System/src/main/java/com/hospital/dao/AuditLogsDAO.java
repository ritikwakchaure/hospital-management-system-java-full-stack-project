package com.hospital.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.hospital.model.AuditLogs;

public interface AuditLogsDAO {

    void save(AuditLogs log) throws SQLException;

    AuditLogs findById(Integer logId) throws SQLException;

    List<AuditLogs> findByUserId(Integer userId) throws SQLException;

    List<AuditLogs> findByActionType(String actionType) throws SQLException;

    List<AuditLogs> findByTableName(String tableName) throws SQLException;

    List<AuditLogs> findByDateRange(LocalDateTime start, LocalDateTime end) throws SQLException;

    List<AuditLogs> findAll(int offset, int limit) throws SQLException;
}
