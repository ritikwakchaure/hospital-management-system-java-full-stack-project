package com.hospital.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.hospital.dao.DoctorsDAO;
import com.hospital.daoimpl.DoctorsDAOImpl;
import com.hospital.model.Doctors;

@WebServlet("/doctors")
public class DoctorsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DoctorsDAO doctorsDAO;

    @Override
    public void init() {
        doctorsDAO = new DoctorsDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");

        try {
            if (action == null) {
                listDoctors(request, response);
            } else {
                switch (action) {
                    case "new":
                        request.getRequestDispatcher("/doctor-form.jsp").forward(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteDoctor(request, response);
                        break;
                    default:
                        listDoctors(request, response);
                        break;
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                insertDoctor(request, response);
            } else if ("update".equals(action)) {
                updateDoctor(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/doctors");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listDoctors(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int page = 1;
        int limit = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }

        int offset = (page - 1) * limit;

        List<Doctors> list = doctorsDAO.findAll(offset, limit);
        request.setAttribute("doctorList", list);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher("doctors.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/doctors");
            return;
        }

        Integer id = Integer.parseInt(idParam);
        Doctors doctor = doctorsDAO.findById(id);

        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctors");
            return;
        }

        request.setAttribute("doctor", doctor);
        request.getRequestDispatcher("doctor-form.jsp").forward(request, response);
    }

    private void insertDoctor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        Doctors doctor = buildDoctorFromRequest(request);
        if (doctor == null) return;

        doctorsDAO.save(doctor);
        response.sendRedirect(request.getContextPath() + "/doctors");
    }

    private void updateDoctor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        String idParam = request.getParameter("doctor_id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/doctors");
            return;
        }

        Doctors doctor = buildDoctorFromRequest(request);
        if (doctor == null) return;

        doctor.setDoctor_id(Integer.parseInt(idParam));
        doctorsDAO.update(doctor);

        response.sendRedirect(request.getContextPath() + "/doctors");
    }

    private Doctors buildDoctorFromRequest(HttpServletRequest request)
            throws ServletException, IOException {

        try {
            String userIdParam = request.getParameter("user_id");
            String departmentIdParam = request.getParameter("department_id");
            String qualification = request.getParameter("qualification");
            String experienceParam = request.getParameter("experience_years");
            String feeParam = request.getParameter("consultation_fee");

            if (userIdParam == null || departmentIdParam == null ||
                qualification == null || qualification.trim().isEmpty() ||
                experienceParam == null || feeParam == null) {

                request.setAttribute("error", "All fields are required.");
                request.getRequestDispatcher("doctor-form.jsp").forward(request,
                        (HttpServletResponse) request.getAttribute("jakarta.servlet.response"));
                return null;
            }

            Doctors doctor = new Doctors();
            doctor.setUser_id(Integer.parseInt(userIdParam));
            doctor.setDepartment_id(Integer.parseInt(departmentIdParam));
            doctor.setQualification(qualification.trim());
            doctor.setExperience_years(Integer.parseInt(experienceParam));
            doctor.setConsultation_fee(new BigDecimal(feeParam));

            return doctor;

        } catch (Exception e) {
            request.setAttribute("error", "Invalid input format.");
            request.getRequestDispatcher("doctor-form.jsp").forward(request,
                    (HttpServletResponse) request.getAttribute("jakarta.servlet.response"));
            return null;
        }
    }

    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            doctorsDAO.deleteById(Integer.parseInt(idParam));
        }

        response.sendRedirect(request.getContextPath() + "/doctors");
    }

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null ||
            !"ADMIN".equalsIgnoreCase(session.getAttribute("role").toString())) {

            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
