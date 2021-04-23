/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.gui;

import Entity.resto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Maryem Cherif
 */
public class RestooController  {

    @FXML
    private ImageView imgv;
    @FXML
    private Label nomresto;

    
    
     public void setData(resto resto){
      String photo=resto.getRestopic();
                    System.out.println(photo);
        Image imageURL= new Image("file:///C:/wamp64/www/pidev/public/images/resto/" + photo);
        System.out.println(imageURL);
        imgv.setImage(imageURL);
        nomresto.setText(resto.getNomresto());
        
        
    }    
    
}
