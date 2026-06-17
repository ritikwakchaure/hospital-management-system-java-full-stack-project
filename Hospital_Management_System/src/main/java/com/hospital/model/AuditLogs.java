package com.hospital.model;

import java.time.LocalDateTime;

/**
 * Model class representing the audit_logs table.
 */
public class AuditLogs {

    // Primary key
    private Integer log_id;

    // User performing the action
    private Integer user_id;

    // Action performed (CREATE, UPDATE, DELETE, LOGIN, LOGOUT)
    private String action_type;

    // Target table name
    private String table_name;

    // Affected record ID
    private Integer record_id;

    // Timestamp of action
    private LocalDateTime action_timestamp;

    // Additional metadata in JSON format
    private String metadata;

    // Default constructor
    public AuditLogs() {
    }

    // Parameterized constructor (all fields)
    public AuditLogs(Integer log_id, Integer user_id, String action_type,
                      String table_name, Integer record_id,
                      LocalDateTime action_timestamp, String metadata) {
        this.log_id = log_id;
        this.user_id = user_id;
        this.action_type = action_type;
        this.table_name = table_name;
        this.record_id = record_id;
        this.action_timestamp = action_timestamp;
        this.metadata = metadata;
    }

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public LocalDateTime getAction_timestamp() {
        return action_timestamp;
    }

    public void setAction_timestamp(LocalDateTime action_timestamp) {
        this.action_timestamp = action_timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Audit_logs{" +
                "log_id=" + log_id +
                ", user_id=" + user_id +
                ", action_type='" + action_type + '\'' +
                ", table_name='" + table_name + '\'' +
                ", record_id=" + record_id +
                ", action_timestamp=" + action_timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
