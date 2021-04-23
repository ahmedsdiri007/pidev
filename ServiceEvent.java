/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import ConnexionBD.CnxBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nour
 */
public class ServiceEvent {
     private Connection conn;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    
    
    public ServiceEvent() {
        conn = CnxBD.getInstance().getCnx();
}
    
    
    
    
    
    
    
    
    
    
    
} 
 
