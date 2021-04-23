/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.gui;

import Entity.reservation_resto;
import Entity.resto;
import ServiceResto.ServiceResto;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 * FXML Controller class
 *
 * @author Maryem Cherif
 */

public class RestoreservationController implements Initializable {
private int PostId;
    @FXML
    private Button btn_rs;
        public resto restoo;
    @FXML
    private HBox reservationContainer;
       private List<resto> comments;
       private resto CommentData;
        
List<reservation_resto> reservation;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Platform.runLater(()->{
           System.out.println("gg");
          
           try {
               comments =new ArrayList<>(comments());
               
           } catch (SQLException ex) {
               Logger.getLogger(RestoreservationController.class.getName()).log(Level.SEVERE, null, ex);
           }
               System.out.println(PostId);
           

            try {
                
for (int i=0;i<comments.size();i++){
    FXMLLoader fxmlloader=new FXMLLoader();
    fxmlloader.setLocation(getClass().getResource("Restoo.fxml"));
    
        HBox commentbox = fxmlloader.load();
        RestooController restoController = fxmlloader.getController();
        
    
        restoController.setData(comments.get(i));
   
    
        System.out.println(comments.get(i));
        reservationContainer.getChildren().add(commentbox);
        
    }
} catch (IOException ex) {
        Logger.getLogger(RestoreservationController.class.getName()).log(Level.SEVERE, null, ex);
    }
            
        });
        
    }    
      public void setID(int id){
          this.PostId=id ;
    }
       public int getID(){
          return this.PostId ;  
    }
       public List<resto> comments() throws SQLException {
           System.out.println("xx");
        List<resto> ls=new ArrayList<>();
        ls= new ServiceResto().getNamerestobyId();
        
        return ls;}
    
   
     
     

    @FXML
    private void reserver_resto(ActionEvent event) {
             FXMLLoader Loader=new FXMLLoader();
        Loader.setLocation(getClass().getResource( "/resto/gui/frontresto.fxml"));
        try {
            Loader.load();  
        } catch (IOException ex) {
            Logger.getLogger(RestoreservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
               FrontrestoController iec=Loader.getController();
              //  iec.=this.restoo;
             //   iec.setinfo();
                
                Parent p=Loader.getRoot();
                Stage inscriE=new Stage();
                Scene scene = new Scene(p);
                inscriE.setScene(scene);
                inscriE.show();
        }
}
    

