package com.hospital.model;

import java.time.LocalDateTime;

/**
 * Model class representing the users table.
 */
public class Users {

	// Primary key
	private Integer user_id;

	// User full name
	private String name;

	// Unique email
	private String email;

	// Encrypted password
	private String password;

	// Unique phone number
	private String phone;

	// Role (ADMIN, DOCTOR, PATIENT)
	private String role;

	// Optional address
	private String address;

	// Record creation timestamp
	private LocalDateTime created_at;

	// Default constructor
	public Users() {
	}

	// Parameterized constructor (all fields)
	public Users(Integer user_id, String name, String email, String password, String phone, String role, String address,
			LocalDateTime created_at) {
		this.user_id = user_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.address = address;
		this.created_at = created_at;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Users{" + "user_id=" + user_id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='"
				+ password + '\'' + ", phone='" + phone + '\'' + ", role='" + role + '\'' + ", address='" + address
				+ '\'' + ", created_at=" + created_at + '}';
	}
}
