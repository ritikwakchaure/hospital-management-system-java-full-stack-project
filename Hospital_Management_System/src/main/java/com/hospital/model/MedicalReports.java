package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing the medical_reports table.
 */
public class MedicalReports {

	// Primary key
	private Integer report_id;

	// Patient reference
	private Integer patient_id;

	// Type of medical report
	private String report_type;

	// Optional description
	private String report_description;

	// Date of report
	private LocalDate report_date;

	// Optional file storage path
	private String file_path;

	// Record creation timestamp
	private LocalDateTime created_at;

	// Default constructor
	public MedicalReports() {
	}

	// Parameterized constructor (all fields)
	public MedicalReports(Integer report_id, Integer patient_id, String report_type, String report_description,
			LocalDate report_date, String file_path, LocalDateTime created_at) {
		this.report_id = report_id;
		this.patient_id = patient_id;
		this.report_type = report_type;
		this.report_description = report_description;
		this.report_date = report_date;
		this.file_path = file_path;
		this.created_at = created_at;
	}

	public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getReport_description() {
		return report_description;
	}

	public void setReport_description(String report_description) {
		this.report_description = report_description;
	}

	public LocalDate getReport_date() {
		return report_date;
	}

	public void setReport_date(LocalDate report_date) {
		this.report_date = report_date;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "MedicalReports{" + "report_id=" + report_id + ", patient_id=" + patient_id + ", report_type='"
				+ report_type + '\'' + ", report_description='" + report_description + '\'' + ", report_date="
				+ report_date + ", file_path='" + file_path + '\'' + ", created_at=" + created_at + '}';
	}
}
