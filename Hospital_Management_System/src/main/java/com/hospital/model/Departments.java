package com.hospital.model;

import java.time.LocalDateTime;

/**
 * Model class representing the departments table.
 */
public class Departments {

	// Primary key
	private Integer department_id;

	// Unique department name
	private String department_name;

	// Optional description
	private String description;

	// Record creation timestamp
	private LocalDateTime created_at;

	// Default constructor
	public Departments() {
	}

	// Parameterized constructor (all fields)
	public Departments(Integer department_id, String department_name, String description, LocalDateTime created_at) {
		this.department_id = department_id;
		this.department_name = department_name;
		this.description = description;
		this.created_at = created_at;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Departments{" + "department_id=" + department_id + ", department_name='" + department_name + '\''
				+ ", description='" + description + '\'' + ", created_at=" + created_at + '}';
	}
}
