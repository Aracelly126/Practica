/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Vista;

import Controller.ApiRest;
import Model.Estudiante;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Aracelly
 */
@WebServlet(name = "EstudianteServlet", urlPatterns = {"/EstudianteServlet"})
public class EstudianteServlet extends HttpServlet {

    private final ApiRest apiClient = new ApiRest();

    private Estudiante parseStudent(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            if (jsonArray.length() > 0) {
                JSONObject jsonStudent = jsonArray.getJSONObject(0);
                String cedula = jsonStudent.getString("cedula");
                String nombre = jsonStudent.getString("nombre");
                String apellido = jsonStudent.getString("apellido");
                String direccion = jsonStudent.getString("direccion");
                String telefono = jsonStudent.getString("telefono");

                return new Estudiante(cedula, nombre, apellido, direccion, telefono);

            } else {
                System.out.println("No se encontro al estudiante");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    private List<Estudiante> parseStudents(String jsonResponse) {
        List<Estudiante> estudiantes = new ArrayList<>();

        try {
            JSONArray listaEstudiantes = new JSONArray(jsonResponse);
            for (int i = 0; i < listaEstudiantes.length(); i++) {
                JSONObject estudiante = listaEstudiantes.getJSONObject(0);
                estudiantes.add(new Estudiante(
                        estudiante.getString("cedula"),
                        estudiante.getString("nombre"),
                        estudiante.getString("apellido"),
                        estudiante.getString("direccion"),
                        estudiante.getString("telefono")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return estudiantes;

    }

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            String cedula = request.getParameter("cedula");
            String jsonResponse = apiClient.getStudentByCedula(cedula);
            Estudiante estudiante = parseStudent(jsonResponse);

            if (estudiante == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Estudiante no Encontrado");
                return;
            }
            
            request.setAttribute("estudiante", estudiante);
            request.getRequestDispatcher("/WEB-INF/editarEstudiante.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/agregarEstudiante.jsp").forward(request, response);
        } else {
            String jsonResponse = apiClient.getStudents();
            List<Estudiante> students = parseStudents(jsonResponse);
            request.setAttribute("students", students);
            request.getRequestDispatcher("/WEB-INF/estudiantes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");
        if ("put".equals(method)) {
            doPut(request, response);

        }if ("delete".equalsIgnoreCase(method)) {
            doDelete(request, response);

        } else {
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String result = apiClient.saveStudent(cedula, nombre, apellido, direccion, telefono);
            response.sendRedirect("EstudianteServlet");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");

        String result = apiClient.updateStudent(cedula, nombre, apellido, direccion, telefono);
        response.getWriter().write(result);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        String result = apiClient.deleteStudents("cedula");
        response.sendRedirect("EstudianteServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
