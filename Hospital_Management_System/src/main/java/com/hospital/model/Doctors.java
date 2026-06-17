package com.hospital.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model class representing the doctors table.
 */
public class Doctors {

	// Primary key
	private Integer doctor_id;

	// Unique user reference
	private Integer user_id;

	// Department reference
	private Integer department_id;

	// Qualification details
	private String qualification;

	// Years of experience
	private Integer experience_years;

	// Consultation fee
	private BigDecimal consultation_fee;

	// Record creation timestamp
	private LocalDateTime created_at;

	// Default constructor
	public Doctors() {
	}

	// Parameterized constructor (all fields)
	public Doctors(Integer doctor_id, Integer user_id, Integer department_id, String qualification,
			Integer experience_years, BigDecimal consultation_fee, LocalDateTime created_at) {
		this.doctor_id = doctor_id;
		this.user_id = user_id;
		this.department_id = department_id;
		this.qualification = qualification;
		this.experience_years = experience_years;
		this.consultation_fee = consultation_fee;
		this.created_at = created_at;
	}

	public Integer getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Integer getExperience_years() {
		return experience_years;
	}

	public void setExperience_years(Integer experience_years) {
		this.experience_years = experience_years;
	}

	public BigDecimal getConsultation_fee() {
		return consultation_fee;
	}

	public void setConsultation_fee(BigDecimal consultation_fee) {
		this.consultation_fee = consultation_fee;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Doctors{" + "doctor_id=" + doctor_id + ", user_id=" + user_id + ", department_id=" + department_id
				+ ", qualification='" + qualification + '\'' + ", experience_years=" + experience_years
				+ ", consultation_fee=" + consultation_fee + ", created_at=" + created_at + '}';
	}
}
