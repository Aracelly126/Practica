/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author thexe
 */
public class ApiRest {
    
    private final String apiUrl = "http://localhost/SOAPP/SOAP/controllers/apiRest.php";
    
    //Metodo Get para obtener estudiantes
    public String getStudents(){
        StringBuilder resultado = new StringBuilder();
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {                    
                    resultado.append(line);
                }
                
                reader.close();
                
            }else{
                System.out.println("Error de conexion: "+ conn.getResponseCode());
            }
            
            conn.disconnect();
            
        } catch (Exception e) {
        }
        return resultado.toString();
    }
    
    private String sendRequest(String Method, String cedula, String nombre, String apellido, String direccion, String telefono){
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(Method);
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
            e.getStackTrace();
            return "Error al procesar solicitud";
        }
        
    }
    
    //Metodo Post guardar
    public String saveStudent(String cedula, String nombre, String apellido, String direccion, String telefono){
        return sendRequest("POST", cedula, nombre, apellido, direccion, telefono);
    }
    
    //Metodo Put editar
    public String updateStudent(String cedula, String nombre, String apellido, String direccion, String telefono){
        return sendRequest("PUT", cedula, nombre, apellido, direccion, telefono);
    }
    
    //Metodo Delete
    public String deleteStudents(String cedula){
        
        try {
            URL url = new URL(apiUrl + "?cedula="+cedula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder resultado = new StringBuilder();
                while ((line = reader.readLine()) != null) {                    
                    resultado.append(line);
                }
                
                reader.close();
                return resultado.toString();
            }else{
                return "Error de conexion: "+ conn.getResponseCode();
            }
            
        } catch (Exception e) {
             return "Error al eliminar estudiante";
        }
       
    }
    
    public String getStudentByCedula(String cedula){
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(apiUrl + "?cedula="+cedula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                
                while ((line = reader.readLine()) != null) {                    
                    resultado.append(line);
                }
                
                reader.close();
              
            }else{
                System.out.println("Error de conexion: "+ conn.getResponseCode());
            }
            conn.disconnect();
            
        } catch (Exception e) {
             e.printStackTrace();
        }
      return resultado.toString(); 
    }
      
    
}