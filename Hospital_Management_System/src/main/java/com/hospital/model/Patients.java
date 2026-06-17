package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing the patients table.
 */
public class Patients {

	// Primary key
	private Integer patient_id;

	// Unique user reference
	private Integer user_id;

	// Date of birth
	private LocalDate date_of_birth;

	// Gender (MALE, FEMALE, OTHER)
	private String gender;

	// Blood group (A+, A-, B+, B-, AB+, AB-, O+, O-)
	private String blood_group;

	// Record creation timestamp
	private LocalDateTime created_at;

	// Default constructor
	public Patients() {
	}

	// Parameterized constructor (all fields)
	public Patients(Integer patient_id, Integer user_id, LocalDate date_of_birth, String gender, String blood_group,
			LocalDateTime created_at) {
		this.patient_id = patient_id;
		this.user_id = user_id;
		this.date_of_birth = date_of_birth;
		this.gender = gender;
		this.blood_group = blood_group;
		this.created_at = created_at;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Patients{" + "patient_id=" + patient_id + ", user_id=" + user_id + ", date_of_birth=" + date_of_birth
				+ ", gender='" + gender + '\'' + ", blood_group='" + blood_group + '\'' + ", created_at=" + created_at
				+ '}';
	}
}
