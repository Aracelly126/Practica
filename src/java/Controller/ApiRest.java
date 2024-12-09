package Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRest {

    private final String apiUrl = "http://localhost/SOAPP/SOAP/controllers/apiRest.php";

    // Método GET para obtener la lista de estudiantes (ya implementado)
    public String getStudents() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    // Método POST para guardar un estudiante
    public String saveStudent(String cedula, String nombre, String apellido, String direccion, String telefono) {
        return sendRequest("POST", cedula, nombre, apellido, direccion, telefono);
    }

    // Método PUT para actualizar un estudiante
    public String updateStudent(String cedula, String nombre, String apellido, String direccion, String telefono) {
        return sendRequest("PUT", cedula, nombre, apellido, direccion, telefono);
    }

    // Método DELETE para eliminar un estudiante
    public String deleteStudent(String cedula) {
        try {
            URL url = new URL(apiUrl + "?cedula=" + cedula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } else {
                return "Error: " + conn.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar el estudiante";
        }
    }

    private String sendRequest(String method, String cedula, String nombre, String apellido, String direccion, String telefono) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String params = "cedula=" + cedula + "&nombre=" + nombre + "&apellido=" + apellido + "&direccion=" + direccion + "&telefono=" + telefono;
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes(params);
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar la solicitud";
        }
    }

    public String getStudentByCedula(String cedula) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl + "?cedula=" + cedula);
            System.out.println("URL: " + url); // Verifica la URL generada
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Respuesta de la API: " + result.toString()); // Verifica la respuesta
        return result.toString();
    }

}
