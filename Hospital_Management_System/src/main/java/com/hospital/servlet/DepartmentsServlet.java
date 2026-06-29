package com.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.hospital.dao.DepartmentsDAO;
import com.hospital.daoimpl.DepartmentsDAOImpl;
import com.hospital.model.Departments;

@WebServlet("/departments")
public class DepartmentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DepartmentsDAO departmentsDAO;

    @Override
    public void init() {
        departmentsDAO = new DepartmentsDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");

        try {
            if (action == null) {
                listDepartments(request, response);
            } else {
                switch (action) {
                    case "new":
                    	request.getRequestDispatcher("/department-form.jsp").forward(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteDepartment(request, response);
                        break;
                    default:
                        listDepartments(request, response);
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
                insertDepartment(request, response);
            } else if ("update".equals(action)) {
                updateDepartment(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/departments");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listDepartments(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Departments> list = departmentsDAO.findAll();
        request.setAttribute("departmentList", list);
        request.getRequestDispatcher("/departments.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/departments");
            return;
        }

        Integer id = Integer.parseInt(idParam);
        Departments department = departmentsDAO.findById(id);

        if (department == null) {
            response.sendRedirect(request.getContextPath() + "/departments");
            return;
        }

        request.setAttribute("department", department);
        request.getRequestDispatcher("/department-form.jsp").forward(request, response);
    }

    private void insertDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        String name = request.getParameter("department_name");
        String description = request.getParameter("description");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Department name is required.");
            request.getRequestDispatcher("/department-form.jsp").forward(request, response);
            return;
        }

        Departments existing = departmentsDAO.findByName(name.trim());
        if (existing != null) {
            request.setAttribute("error", "Department name already exists.");
            request.getRequestDispatcher("/department-form.jsp").forward(request, response);
            return;
        }

        Departments department = new Departments();
        department.setDepartment_name(name.trim());
        department.setDescription(description);

        departmentsDAO.save(department);

        response.sendRedirect(request.getContextPath() + "/departments");
    }

    private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        String idParam = request.getParameter("department_id");
        String name = request.getParameter("department_name");
        String description = request.getParameter("description");

        if (idParam == null || name == null || name.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/departments");
            return;
        }

        Integer id = Integer.parseInt(idParam);

        Departments existing = departmentsDAO.findByName(name.trim());
        if (existing != null && !existing.getDepartment_id().equals(id)) {
            request.setAttribute("error", "Department name already exists.");
            request.setAttribute("department", departmentsDAO.findById(id));
            request.getRequestDispatcher("/department-form.jsp").forward(request, response);
            return;
        }

        Departments department = new Departments();
        department.setDepartment_id(id);
        department.setDepartment_name(name.trim());
        department.setDescription(description);

        departmentsDAO.update(department);

        response.sendRedirect(request.getContextPath() + "/departments");
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null) {
            Integer id = Integer.parseInt(idParam);
            departmentsDAO.deleteById(id);
        }

        response.sendRedirect(request.getContextPath() + "/departments");
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
