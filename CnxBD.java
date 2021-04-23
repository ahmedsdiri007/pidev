/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nour
 */
public class CnxBD {
    private String url="jdbc:mysql://127.0.0.1/pivision";
    private String login="root";
    private String pwd="";
    private Connection cnx;
    
    private static CnxBD instance;

    private CnxBD() {
        try {
            cnx=DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion");
        } catch (SQLException ex) {
            Logger.getLogger(CnxBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static CnxBD getInstance(){
        if(instance==null)
         instance=new CnxBD();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
    
}
