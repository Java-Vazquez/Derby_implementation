/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derby_up;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lelguea
 */
public class CreaDB {
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB;create=true;user=APP;pass=ppa";
    // jdbc Connection
    private static Connection conn = null;


    public static void main(String[] args) {
        createConnection();
        shutdown();
    }
    
    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(URL); 
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException except) {
            System.err.println(except.toString());
        }
    }
    
    
    private static void shutdown() {
        try {
            if (conn != null) {
                DriverManager.getConnection(URL + ";shutdown=true");
                conn.close();
            }           
        } catch (SQLException sqlExcept) {
            if (!sqlExcept.toString().contains("Cierre de la base de datos")) {
                System.err.println("1.- "+sqlExcept.toString());
            } else {
                System.out.println("Se cerro la base de datos");
            }

        }
    }
}