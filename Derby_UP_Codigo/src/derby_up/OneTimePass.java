/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package derby_up;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Javier
 */
public class OneTimePass {
  // Longitud de la contraseña generada
  private static final int PASSWORD_LENGTH = 10;
  
  // Caracteres permitidos en la contraseña
  private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  
  public static void main(String[] args) throws SQLException {
    // Establecer la conexión a la base de datos
    String url = "jdbc:derby://localhost:1527/myDB";
    String user = "username";
    String password = "password";
    String passwordString;
    String username;
      // Generar una cadena aleatoria para la contraseña
      try (Connection conn = DriverManager.getConnection(url, user, password)) {
          // Generar una cadena aleatoria para la contraseña
          SecureRandom random = new SecureRandom();
          char[] passwordChars = new char[PASSWORD_LENGTH];
          for (int i = 0; i < PASSWORD_LENGTH; i++) {
              passwordChars[i] = PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length()));
          }     passwordString = new String(passwordChars);
          // Insertar la contraseña en la base de datos
          username = "usuario";
          String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
          PreparedStatement stmt = conn.prepareStatement(sql);
          stmt.setString(1, username);
          stmt.setString(2, passwordString);
          stmt.executeUpdate();
          // Cerrar la conexión
      }
    
    // Imprimir la contraseña generada
    System.out.println("La contraseña generada para el usuario " + username + " es: " + passwordString);
  }
}  
