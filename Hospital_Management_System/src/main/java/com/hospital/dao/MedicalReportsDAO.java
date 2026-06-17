package com.hospital.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hospital.model.MedicalReports;

public interface MedicalReportsDAO {

    void save(MedicalReports report) throws SQLException;

    void update(MedicalReports report) throws SQLException;

    MedicalReports findById(Integer reportId) throws SQLException;

    List<MedicalReports> findByPatientId(Integer patientId) throws SQLException;

    List<MedicalReports> findByPatientIdAndType(Integer patientId, String reportType) throws SQLException;

    List<MedicalReports> findByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException;

    List<MedicalReports> findAll(int offset, int limit) throws SQLException;

    void deleteById(Integer reportId) throws SQLException;
}
