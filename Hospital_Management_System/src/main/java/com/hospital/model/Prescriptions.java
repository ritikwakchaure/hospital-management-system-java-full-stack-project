package com.hospital.model;

import java.time.LocalDateTime;

/**
 * Model class representing the prescriptions table.
 */
public class Prescriptions {

	// Primary key
	private Integer prescription_id;

	// Unique appointment reference
	private Integer appointment_id;

	// Diagnosis details
	private String diagnosis;

	// Prescribed medicines
	private String medicines;

	// Optional instructions
	private String instructions;

	// Prescription issue timestamp
	private LocalDateTime issued_at;

	// Default constructor
	public Prescriptions() {
	}

	// Parameterized constructor (all fields)
	public Prescriptions(Integer prescription_id, Integer appointment_id, String diagnosis, String medicines,
			String instructions, LocalDateTime issued_at) {
		this.prescription_id = prescription_id;
		this.appointment_id = appointment_id;
		this.diagnosis = diagnosis;
		this.medicines = medicines;
		this.instructions = instructions;
		this.issued_at = issued_at;
	}

	public Integer getPrescription_id() {
		return prescription_id;
	}

	public void setPrescription_id(Integer prescription_id) {
		this.prescription_id = prescription_id;
	}

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getMedicines() {
		return medicines;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public LocalDateTime getIssued_at() {
		return issued_at;
	}

	public void setIssued_at(LocalDateTime issued_at) {
		this.issued_at = issued_at;
	}

	@Override
	public String toString() {
		return "Prescriptions{" + "prescription_id=" + prescription_id + ", appointment_id=" + appointment_id
				+ ", diagnosis='" + diagnosis + '\'' + ", medicines='" + medicines + '\'' + ", instructions='"
				+ instructions + '\'' + ", issued_at=" + issued_at + '}';
	}
}
