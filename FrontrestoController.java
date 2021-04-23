/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.gui;

import DB.DataBase;
import Dialog.AlertDialog;

import Entity.reservation_resto;

import resto.gui.RestoController;


import ServiceReservationResto.ServiceReservationResto;
import ServiceResto.SMSAPI;
import ServiceResto.ServiceResto;
import com.jfoenix.controls.JFXTextField;
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import validation.TextFieldvalidation;

/**
 * FXML Controller class
 *
 * @author Maryem Cherif
 */
public class FrontrestoController implements Initializable {
    
     private ObservableList<reservation_resto> dataresv;

    private Connection con;
    private ResultSet rs=null;
    private PreparedStatement pst;

    @FXML
    private JFXTextField tf_nbper;
    @FXML
    private JFXTextField tf_mail;
    @FXML
    private JFXTextField tf_num;
   
    @FXML
    private TableView<reservation_resto> tab_reservationresto;
    @FXML
    private TableColumn<?, ?> nbpersonnec;
    @FXML
    private TableColumn<?, ?> mailc;
    @FXML
    private TableColumn<?, ?> numeroc;
    @FXML
    private Label error_nbpersonne;
    @FXML
    private Label error_mail;
    @FXML
    private Label error_num;

    /**
     * Initializes the controller class.
     
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         con = DataBase.getInstance().getConnection();
         dataresv= FXCollections.observableArrayList();
         loadDatareservation() ;
         afficherreservationresto();
         setCellValueFromTableToTextFieldcat();
                

        
    }    
 

     @FXML
          public void ajouterreservation(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
         
         
             SMSAPI sms1= new SMSAPI();
        boolean isNbperEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nbper, error_nbpersonne, "Nombre de personne est requis");
        boolean isMailEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_mail, error_mail, "Mail est requis");
        boolean isNumEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_num, error_num, "Numero est requis");

            if( isNbperEmpty && isMailEmpty && isNumEmpty )
        {

          
            int i;
             int nbpersonne=Integer.valueOf(tf_nbper.getText());
             String mail= tf_mail.getText();
             int numero=Integer.valueOf(tf_num.getText());
           ServiceReservationResto resr=new ServiceReservationResto();
           
            reservation_resto r = new reservation_resto(nbpersonne,mail,numero);
            i=resr.ajouterreservation(r);
              if(i==1)
        {
            sms1.sendSMS();
                afficherreservationresto();
                loadDatareservation();
               
                Notification.sendNotification("Tabaani"," \n Reservation ajouté ." ,TrayIcon.MessageType.WARNING);
                AlertDialog.display("Info","Reservation ajouté");
 clearTextFieldres();
        }
          
        }  }
 
          private void afficherreservationresto(){

             nbpersonnec.setCellValueFactory(new PropertyValueFactory <>("nbpersonne"));
             mailc.setCellValueFactory(new PropertyValueFactory <>("mail"));
             numeroc.setCellValueFactory(new PropertyValueFactory <>("numero"));


    }

   private void loadDatareservation() {
         dataresv.clear();
         try {
           pst =con.prepareStatement("Select  id_res_resto as id_res_resto,nbpersonne as nbpersonne ,mail as mail ,numero as numero  from reservation_resto");

         rs=pst.executeQuery();
    
       while (rs.next()) {  
         int id=rs.getInt("id_res_resto");
         int nbpersonne=rs.getInt("nbpersonne");
         String mail=rs.getString("mail");
         int numero=rs.getInt("numero");
         
         ServiceReservationResto reservation_resto=new ServiceReservationResto();
         reservation_resto res=new reservation_resto(id,nbpersonne,mail,numero);
       
             dataresv.add(res );
            
                   }
     }       
         
       catch (SQLException ex) {
           Logger.getLogger(reservation_resto.class.getName()).log(Level.SEVERE, null, ex);
       }
         tab_reservationresto.setItems(dataresv);
   }
   
     @FXML
     public void deletereservationR(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
        
         
         
 TableColumn.CellEditEvent edittedcell = null;
            int x=gettemp1(edittedcell);         
            int i;
         ServiceReservationResto reservation_resto=new ServiceReservationResto();
            int idrr=Integer.valueOf(x);
           
           i=reservation_resto.deletereservationR(idrr);
              if(i==1)
        {       
                loadDatareservation();
                afficherreservationresto();
                Notification.sendNotification("Tabaani"," \n Reservation supprimé ." ,TrayIcon.MessageType.WARNING);
                AlertDialog.display("Info","Reservation supprimé");
                
                clearTextFieldres();


        }
              
        }
     
       @FXML
    public void updatereservation(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
       
         boolean isNbperEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nbper, error_nbpersonne, "Nombre de personne est requis");
        boolean isMailEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_mail, error_mail, "Mail est requis");
        boolean isNumEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_num, error_num, "Numero est requis");

            if( isNbperEmpty && isMailEmpty && isNumEmpty )
        {
        
        int i;
              TableColumn.CellEditEvent edittedcell = null;
            int x=gettemp1(edittedcell);
            int nbpersonne = Integer.valueOf(tf_nbper.getText());
            String mail=tf_mail.getText();
            int numero=Integer.valueOf(tf_num.getText());
            
            
          ServiceReservationResto resr=new ServiceReservationResto();
            reservation_resto r;
             r = new reservation_resto(x,nbpersonne,mail,numero);
            i=resr.updatereservation(r);
              if(i==1)
        { 
                 loadDatareservation();
                 afficherreservationresto();
                 Notification.sendNotification("Tabaani"," \n Reservation modifié ." ,TrayIcon.MessageType.WARNING);
                 AlertDialog.display("Info","Reservation modifié");
                 clearTextFieldres();
        }         
         
        
    }}
    
    public reservation_resto gettemp(TableColumn.CellEditEvent edittedCell) {
        reservation_resto test = tab_reservationresto.getSelectionModel().getSelectedItem();
       
        return test;
      }
   public int gettemp1(TableColumn.CellEditEvent edittedCell) {
        reservation_resto test = tab_reservationresto.getSelectionModel().getSelectedItem();
        int x= test.getId_res_resto();
        return x;
    }
    
    
      private void setCellValueFromTableToTextFieldcat(){
          tab_reservationresto.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
       reservation_resto reservation_resto=tab_reservationresto.getItems().get(tab_reservationresto.getSelectionModel().getSelectedIndex());

       tf_nbper.setText(Integer.toString(reservation_resto.getNbpersonne()));
       tf_mail.setText(reservation_resto.getMail());
       tf_num.setText(Integer.toString(reservation_resto.getNumero()));


         TableColumn.CellEditEvent edittedcell = null;
            reservation_resto c=gettemp(edittedcell);  
      System.out.println(c);
            
        }
});
      }   
      
       private void clearTextFieldres(){
    tf_nbper.clear();
    tf_mail.clear();
    tf_num.clear();
           
    }
}



