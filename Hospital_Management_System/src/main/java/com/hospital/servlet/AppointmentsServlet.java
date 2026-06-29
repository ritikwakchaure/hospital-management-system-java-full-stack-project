package com.hospital.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.hospital.dao.AppointmentsDAO;
import com.hospital.dao.PatientsDAO;
import com.hospital.dao.DoctorsDAO;

import com.hospital.daoimpl.AppointmentsDAOImpl;
import com.hospital.daoimpl.PatientsDAOImpl;
import com.hospital.daoimpl.DoctorsDAOImpl;

import com.hospital.model.Appointments;
import com.hospital.model.Patients;
import com.hospital.model.Doctors;
import com.hospital.model.Users;

@WebServlet("/appointments")
public class AppointmentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentsDAO appointmentsDAO;
    private PatientsDAO patientsDAO;
    private DoctorsDAO doctorsDAO;

    @Override
    public void init() {
        appointmentsDAO = new AppointmentsDAOImpl();
        patientsDAO = new PatientsDAOImpl();
        doctorsDAO = new DoctorsDAOImpl();
    }

    /* =========================================================
                           DO GET
       ========================================================= */

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = session.getAttribute("role").toString();
        String action = request.getParameter("action");

        try {

            switch (role.toUpperCase()) {

                case "ADMIN":
                    handleAdminGet(request, response, action);
                    break;

                case "PATIENT":
                    handlePatientGet(request, response, action, session);
                    break;

                case "DOCTOR":
                    handleDoctorGet(request, response, session);
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/login");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /* =========================================================
                           DO POST
       ========================================================= */

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = session.getAttribute("role").toString();
        String action = request.getParameter("action");

        try {

            if ("PATIENT".equalsIgnoreCase(role) && "insert".equals(action)) {
                createAppointment(request, response, session);
            }
            else if ("ADMIN".equalsIgnoreCase(role) && "update".equals(action)) {
                updateAppointment(request, response);
            }
            else if ("status".equals(action)) {
                updateStatus(request, response);
            }
            else {
                response.sendRedirect(request.getContextPath() + "/appointments");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /* =========================================================
                           ADMIN
       ========================================================= */

    private void handleAdminGet(HttpServletRequest request,
                                HttpServletResponse response,
                                String action)
            throws SQLException, ServletException, IOException {

        if (action == null) {

            int page = 1;
            int limit = 10;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            int offset = (page - 1) * limit;

            List<Appointments> list = appointmentsDAO.findAll(offset, limit);

            request.setAttribute("appointmentList", list);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("appointments.jsp")
                   .forward(request, response);

        } else if ("edit".equals(action)) {

            String idParam = request.getParameter("id");

            if (idParam == null) {
                response.sendRedirect(request.getContextPath() + "/appointments");
                return;
            }

            Appointments appointment =
                    appointmentsDAO.findById(Integer.parseInt(idParam));

            request.setAttribute("appointment", appointment);
            request.getRequestDispatcher("appointment-form.jsp")
                   .forward(request, response);
        }
    }

    /* =========================================================
                           PATIENT
       ========================================================= */

    private void handlePatientGet(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String action,
                                  HttpSession session)
            throws SQLException, ServletException, IOException {

        Users user = (Users) session.getAttribute("loggedUser");

        Patients patient =
                patientsDAO.findByUserId(user.getUser_id());

        if (patient == null) {
            response.sendRedirect(request.getContextPath() + "/appointments");
            return;
        }

        if (action == null) {

            List<Appointments> list =
                    appointmentsDAO.findByPatientId(patient.getPatient_id());

            request.setAttribute("appointmentList", list);
            request.getRequestDispatcher("appointments.jsp")
                   .forward(request, response);

        } else if ("new".equals(action)) {

            request.getRequestDispatcher("appointment-form.jsp")
                   .forward(request, response);
        }
    }

    /* =========================================================
                           DOCTOR  (FIXED)
       ========================================================= */

    private void handleDoctorGet(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session)
            throws SQLException, ServletException, IOException {

        Users user = (Users) session.getAttribute("loggedUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Step 1: Get doctor using user_id
        Doctors doctor =
                doctorsDAO.findByUserId(user.getUser_id());

        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Step 2: Use doctor_id (NOT user_id)
        Integer doctorId = doctor.getDoctor_id();

        // Step 3: Fetch all appointments of this doctor
        List<Appointments> list =
                appointmentsDAO.findByDoctorId(doctorId);

        request.setAttribute("appointmentList", list);

        request.getRequestDispatcher("appointments.jsp")
               .forward(request, response);
    }

    /* =========================================================
                           CREATE
       ========================================================= */

    private void createAppointment(HttpServletRequest request,
                                   HttpServletResponse response,
                                   HttpSession session)
            throws SQLException, IOException {

        Users user = (Users) session.getAttribute("loggedUser");

        Patients patient =
                patientsDAO.findByUserId(user.getUser_id());

        if (patient == null) {
            response.sendRedirect(request.getContextPath() + "/appointments");
            return;
        }

        Appointments appointment = new Appointments();

        appointment.setPatient_id(patient.getPatient_id());
        appointment.setDoctor_id(
                Integer.parseInt(request.getParameter("doctor_id")));
        appointment.setAppointment_date(
                LocalDate.parse(request.getParameter("appointment_date")));
        appointment.setAppointment_time(
                LocalTime.parse(request.getParameter("appointment_time")));
        appointment.setReason(request.getParameter("reason"));
        appointment.setStatus("SCHEDULED");

        appointmentsDAO.save(appointment);

        response.sendRedirect(request.getContextPath() + "/appointments");
    }

    /* =========================================================
                           UPDATE
       ========================================================= */

    private void updateAppointment(HttpServletRequest request,
                                   HttpServletResponse response)
            throws SQLException, IOException {

        Appointments appointment = new Appointments();

        appointment.setAppointment_id(
                Integer.parseInt(request.getParameter("appointment_id")));
        appointment.setPatient_id(
                Integer.parseInt(request.getParameter("patient_id")));
        appointment.setDoctor_id(
                Integer.parseInt(request.getParameter("doctor_id")));
        appointment.setAppointment_date(
                LocalDate.parse(request.getParameter("appointment_date")));
        appointment.setAppointment_time(
                LocalTime.parse(request.getParameter("appointment_time")));
        appointment.setReason(request.getParameter("reason"));
        appointment.setStatus(request.getParameter("status"));

        appointmentsDAO.update(appointment);

        response.sendRedirect(request.getContextPath() + "/appointments");
    }

    /* =========================================================
                           STATUS UPDATE
       ========================================================= */

    private void updateStatus(HttpServletRequest request,
                              HttpServletResponse response)
            throws SQLException, IOException {

        String idParam = request.getParameter("appointment_id");
        String status = request.getParameter("status");

        if (idParam != null && status != null) {
            appointmentsDAO.updateStatus(
                    Integer.parseInt(idParam), status);
        }

        response.sendRedirect(request.getContextPath() + "/appointments");
    }
}
