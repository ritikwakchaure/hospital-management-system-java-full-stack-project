package com.hospital.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.hospital.dao.PatientsDAO;
import com.hospital.daoimpl.PatientsDAOImpl;
import com.hospital.model.Patients;
import com.hospital.model.Users;

@WebServlet("/patients")
public class PatientsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PatientsDAO patientsDAO;

    @Override
    public void init() {
        patientsDAO = new PatientsDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = session.getAttribute("role").toString();
        String action = request.getParameter("action");

        try {
            if ("ADMIN".equalsIgnoreCase(role)) {
                handleAdminGet(request, response, action);
            } else if ("PATIENT".equalsIgnoreCase(role)) {
                handlePatientGet(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = session.getAttribute("role").toString();
        String action = request.getParameter("action");

        try {
            if ("ADMIN".equalsIgnoreCase(role) && "update".equals(action)) {
                updateByAdmin(request, response);
            } else if ("PATIENT".equalsIgnoreCase(role) && "update".equals(action)) {
                updateOwnProfile(request, response, session);
            } else {
                response.sendRedirect(request.getContextPath() + "/patients");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleAdminGet(HttpServletRequest request, HttpServletResponse response, String action)
            throws SQLException, ServletException, IOException {

        if (action == null) {
            int page = 1;
            int limit = 10;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            int offset = (page - 1) * limit;

            List<Patients> list = patientsDAO.findAll(offset, limit);
            request.setAttribute("patientList", list);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("patients.jsp").forward(request, response);

        } else if ("edit".equals(action)) {

            String idParam = request.getParameter("id");
            if (idParam == null) {
                response.sendRedirect(request.getContextPath() + "/patients");
                return;
            }

            Patients patient = patientsDAO.findById(Integer.parseInt(idParam));
            if (patient == null) {
                response.sendRedirect(request.getContextPath() + "/patients");
                return;
            }

            request.setAttribute("patient", patient);
            request.getRequestDispatcher("patient-form.jsp").forward(request, response);

        } else if ("delete".equals(action)) {

            String idParam = request.getParameter("id");
            if (idParam != null) {
                patientsDAO.deleteById(Integer.parseInt(idParam));
            }

            response.sendRedirect(request.getContextPath() + "/patients");
        }
    }

    private void handlePatientGet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("loggedUser");

        Patients patient = patientsDAO.findByUserId(user.getUser_id());

        if (patient == null) {
            // Redirect to profile creation form
            response.sendRedirect(request.getContextPath() + "/patients?action=create-profile");
            return;
        }

        request.setAttribute("patient", patient);
        request.getRequestDispatcher("/patient-profile.jsp").forward(request, response);

    }

    private void updateByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String idParam = request.getParameter("patient_id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/patients");
            return;
        }

        Patients patient = buildPatientFromRequest(request);
        if (patient == null) {
            response.sendRedirect(request.getContextPath() + "/patients");
            return;
        }

        patient.setPatient_id(Integer.parseInt(idParam));
        patientsDAO.update(patient);

        response.sendRedirect(request.getContextPath() + "/patients");
    }

    private void updateOwnProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws SQLException, IOException {

        Users user = (Users) session.getAttribute("loggedUser");
        Patients existing = patientsDAO.findByUserId(user.getUser_id());

        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/patients");
            return;
        }

        Patients patient = buildPatientFromRequest(request);
        if (patient == null) {
            response.sendRedirect(request.getContextPath() + "/patients");
            return;
        }

        patient.setPatient_id(existing.getPatient_id());
        patient.setUser_id(user.getUser_id());

        patientsDAO.update(patient);

        response.sendRedirect(request.getContextPath() + "/patients");
    }

    private Patients buildPatientFromRequest(HttpServletRequest request) {

        try {
            String userIdParam = request.getParameter("user_id");
            String dobParam = request.getParameter("date_of_birth");
            String gender = request.getParameter("gender");
            String bloodGroup = request.getParameter("blood_group");

            if (dobParam == null || gender == null || bloodGroup == null) {
                return null;
            }

            Patients patient = new Patients();

            if (userIdParam != null && !userIdParam.isEmpty()) {
                patient.setUser_id(Integer.parseInt(userIdParam));
            }

            patient.setDate_of_birth(LocalDate.parse(dobParam));
            patient.setGender(gender);
            patient.setBlood_group(bloodGroup);

            return patient;

        } catch (Exception e) {
            return null;
        }
    }
}
