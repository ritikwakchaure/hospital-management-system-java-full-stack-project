package com.hospital.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hospital.dao.MedicalReportsDAO;
import com.hospital.model.MedicalReports;
import com.hospital.util.DBConnection;

public class MedicalReportsDAOImpl implements MedicalReportsDAO {

    @Override
    public void save(MedicalReports report) throws SQLException {
        String sql = "INSERT INTO medical_reports "
                + "(patient_id, report_type, report_description, report_date, file_path) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, report.getPatient_id());
            ps.setString(2, report.getReport_type());
            ps.setString(3, report.getReport_description());
            ps.setDate(4, Date.valueOf(report.getReport_date()));
            ps.setString(5, report.getFile_path());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(MedicalReports report) throws SQLException {
        String sql = "UPDATE medical_reports SET "
                + "patient_id = ?, report_type = ?, report_description = ?, "
                + "report_date = ?, file_path = ? "
                + "WHERE report_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, report.getPatient_id());
            ps.setString(2, report.getReport_type());
            ps.setString(3, report.getReport_description());
            ps.setDate(4, Date.valueOf(report.getReport_date()));
            ps.setString(5, report.getFile_path());
            ps.setInt(6, report.getReport_id());

            ps.executeUpdate();
        }
    }

    @Override
    public MedicalReports findById(Integer reportId) throws SQLException {
        String sql = "SELECT report_id, patient_id, report_type, report_description, "
                + "report_date, file_path, created_at "
                + "FROM medical_reports WHERE report_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, reportId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReport(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<MedicalReports> findByPatientId(Integer patientId) throws SQLException {
        String sql = "SELECT report_id, patient_id, report_type, report_description, "
                + "report_date, file_path, created_at "
                + "FROM medical_reports WHERE patient_id = ? "
                + "ORDER BY report_date DESC";

        List<MedicalReports> reports = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }
        }

        return reports;
    }

    @Override
    public List<MedicalReports> findByPatientIdAndType(Integer patientId, String reportType)
            throws SQLException {

        String sql = "SELECT report_id, patient_id, report_type, report_description, "
                + "report_date, file_path, created_at "
                + "FROM medical_reports WHERE patient_id = ? AND report_type = ? "
                + "ORDER BY report_date DESC";

        List<MedicalReports> reports = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setString(2, reportType);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }
        }

        return reports;
    }

    @Override
    public List<MedicalReports> findByDateRange(java.time.LocalDate startDate,
                                                java.time.LocalDate endDate) throws SQLException {

        String sql = "SELECT report_id, patient_id, report_type, report_description, "
                + "report_date, file_path, created_at "
                + "FROM medical_reports WHERE report_date BETWEEN ? AND ? "
                + "ORDER BY report_date DESC";

        List<MedicalReports> reports = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }
        }

        return reports;
    }

    @Override
    public List<MedicalReports> findAll(int offset, int limit) throws SQLException {
        String sql = "SELECT report_id, patient_id, report_type, report_description, "
                + "report_date, file_path, created_at "
                + "FROM medical_reports "
                + "ORDER BY report_date DESC "
                + "LIMIT ? OFFSET ?";

        List<MedicalReports> reports = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }
        }

        return reports;
    }

    @Override
    public void deleteById(Integer reportId) throws SQLException {
        String sql = "DELETE FROM medical_reports WHERE report_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, reportId);
            ps.executeUpdate();
        }
    }

    private MedicalReports mapResultSetToReport(ResultSet rs) throws SQLException {
        MedicalReports report = new MedicalReports();

        report.setReport_id(rs.getInt("report_id"));
        report.setPatient_id(rs.getInt("patient_id"));
        report.setReport_type(rs.getString("report_type"));
        report.setReport_description(rs.getString("report_description"));
        report.setReport_date(rs.getDate("report_date").toLocalDate());
        report.setFile_path(rs.getString("file_path"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            report.setCreated_at(timestamp.toLocalDateTime());
        }

        return report;
    }
}
