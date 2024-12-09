<%@page import="javax.swing.JOptionPane"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="Model.Estudiante"%>
<%
    Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Estudiante</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <div class="container my-5">
            <h2 class="text-center">Editar Estudiante</h2>
            <form action="EstudianteServlet" method="post" class="border p-4 rounded bg-light">
                <input type="hidden" name="_method" value="put">
                <input type="hidden" name="cedula" value="<%= estudiante.getCedula()%>">
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" id="nombre" class="form-control" value="<%= estudiante.getNombre()%>" required>
                </div>
                <div class="form-group">
                    <label for="apellido">Apellido</label>
                    <input type="text" name="apellido" id="apellido" class="form-control" value="<%= estudiante.getApellido()%>" required>
                </div>
                <div class="form-group">
                    <label for="direccion">Dirección</label>
                    <input type="text" name="direccion" id="direccion" class="form-control" value="<%= estudiante.getDireccion()%>" required>
                </div>
                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <input type="text" name="telefono" id="telefono" class="form-control" value="<%= estudiante.getTelefono()%>" required>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Actualizar Estudiante</button>
            </form>
        </div>
    </body>
</html>
