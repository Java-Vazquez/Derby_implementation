package derby_up;

import java.sql.SQLException;
import java.util.Properties;
import org.apache.derby.authentication.UserAuthenticator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author 0215391
 */
public class Autentica_Usuarios2023 implements UserAuthenticator {

    @Override
    public boolean authenticateUser(String user, String pasword, String db, Properties prprts) throws SQLException {
        boolean salida = false;
        System.out.println("Usuario: " + user + "password: " + pasword + "DB: " + db);
        
        StringBuilder pass2 = new StringBuilder (pasword);
        if (user.equalsIgnoreCase(pass2.reverse().toString())){
            salida = true;
        }
        return salida;
    }

}
