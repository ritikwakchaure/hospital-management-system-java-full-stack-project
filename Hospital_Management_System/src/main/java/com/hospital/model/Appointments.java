package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Model class representing the appointments table.
 */
public class Appointments {

    // Primary key
    private Integer appointment_id;

    // Foreign key references
    private Integer patient_id;
    private Integer doctor_id;

    // Appointment schedule details
    private LocalDate appointment_date;
    private LocalTime appointment_time;

    // Optional reason for visit
    private String reason;

    // Appointment status (SCHEDULED, COMPLETED, CANCELLED)
    private String status;

    // Record creation timestamp
    private LocalDateTime created_at;

    // Default constructor
    public Appointments() {
    }

    // Parameterized constructor (all fields)
    public Appointments(Integer appointment_id, Integer patient_id, Integer doctor_id,
                        LocalDate appointment_date, LocalTime appointment_time,
                        String reason, String status, LocalDateTime created_at) {
        this.appointment_id = appointment_id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.reason = reason;
        this.status = status;
        this.created_at = created_at;
    }

    public Integer getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Integer appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public LocalDate getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(LocalDate appointment_date) {
        this.appointment_date = appointment_date;
    }

    public LocalTime getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(LocalTime appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "appointment_id=" + appointment_id +
                ", patient_id=" + patient_id +
                ", doctor_id=" + doctor_id +
                ", appointment_date=" + appointment_date +
                ", appointment_time=" + appointment_time +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
