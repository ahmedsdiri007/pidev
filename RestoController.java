/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resto.gui;

import java.awt.image.BufferedImage;
import DB.DataBase;
import Dialog.AlertDialog;
import Entity.Categorie;
import Entity.resto;
import Entity.reservation_resto;
import ServiceCategorie.ServiceCategorie;
import ServiceResto.ServiceResto;
import ServiceReservationResto.ServiceReservationResto;
import ServiceResto.SMSAPI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import validation.TextFieldvalidation;

/**
 * FXML Controller class
 *
 * @author Maryem Cherif
 */
public class RestoController implements Initializable {
     private ObservableList<resto> datar;
     private ObservableList<Categorie> datac;
          private ObservableList<reservation_resto> datares;

    private Connection con;
    private ResultSet rs=null;
    private PreparedStatement pst;
    
    @FXML
    private JFXTextField tf_nomresto;
    @FXML
    private JFXTextField tf_budget;
    @FXML
    private JFXTextField tf_nbplace;
    @FXML
    private JFXComboBox<String> cmb_cat;
    @FXML
    private ImageView imgview;
    @FXML
    private TableView<resto> tab_resto;
    @FXML
    private TableColumn<resto, ?> nomrestoc;
    @FXML
    private TableColumn<resto, ?> budgetc;
    @FXML
    private TableColumn<resto, ?> breplacec;
    @FXML
    private Pane pn_resto;
    @FXML
    private Pane pn_cat;
    @FXML
    private JFXTextField tf_nomcat;
    @FXML
    private TableView<Categorie> tab_cat;
    @FXML
    private TableColumn<Categorie, ?> categoriec;
    @FXML
    private JFXButton btnresto;
    @FXML
    private JFXButton btncategorie;
    @FXML
    private JFXTextField resto_search;
    @FXML
    private JFXButton btn_res;
    @FXML
    private Pane panres;
    @FXML
    private TableColumn<?, ?> nbpersonnec;
    @FXML
    private TableColumn<?, ?> mailc;
    @FXML
    private TableColumn<?, ?> numeroc;
    @FXML
    private TableView<reservation_resto> tab_reservationresto;
    @FXML
    private Label error_cat;
    @FXML
    private Label error_nomr;
    @FXML
    private Label error_budg;
    @FXML
    private Label error_nbplace;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         con = DataBase.getInstance().getConnection();
         datac= FXCollections.observableArrayList();
         datar= FXCollections.observableArrayList();
                  datares= FXCollections.observableArrayList();

         searchR();
        affichercategorie();
        loadDatacategorie();
        setCellValueFromTableToTextFieldcat();
        afficherreservationresto();
        loadDatareservation();
        afficherresto();
       loadDataresto();
        setCellValueFromTableToTextFieldresto();
         initcatcombo();
    } 
    @FXML
       private void handlebuttonAction(ActionEvent event){
            
           if (event.getSource() ==btnresto )
           {
               pn_resto.toFront();
           }
       
           else  if (event.getSource() == btncategorie)
           {
               pn_cat.toFront();
           }
           else  if (event.getSource() == btn_res)
           {
               panres.toFront();
           }
       }
       
       
     @FXML
    public void deleteresto(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
        
 TableColumn.CellEditEvent edittedcell = null;
            int x=gettemp1(edittedcell);         
            int i;
         ServiceResto resto=new ServiceResto();
            int idr1=Integer.valueOf(x);
           
           i=resto.deleteresto(idr1);
              if(i==1)
        {            
                      afficherresto();
                      loadDataresto();
                        Notification.sendNotification("Tabaani"," \n Resto supprimé ." ,TrayIcon.MessageType.WARNING);
                        AlertDialog.display("Info","resto supprimé");
                      clearTextFieldresto();

        }
              
        }
    
    
    @FXML
    public void updateresto(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
          boolean isNomEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nomresto, error_nomr, "Nom resto est requis");
        boolean isBudgetEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_budget, error_budg, "Budget est requis");
        boolean isNbplaceEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nbplace, error_nbplace, "Nombre de place est requis");

            if( isNomEmpty && isBudgetEmpty && isNbplaceEmpty )
        
        {
        int i;
              TableColumn.CellEditEvent edittedcell = null;
            int x=gettemp1(edittedcell);
            String c=cmb_cat.getSelectionModel().getSelectedItem();
            String nom_resto= tf_nomresto.getText();
            int budget=Integer.valueOf(tf_budget.getText());
            int nbplace=Integer.valueOf(tf_nbplace.getText());
            
            Image image1=null;
             image1= imgview.getImage();
            String photo = null;
             photo = saveToFileImageNormal(image1);
            
          ServiceResto res=new ServiceResto();
            ServiceCategorie cat=new ServiceCategorie();
            resto r;
             r = new resto(x,nbplace,budget,nom_resto,photo, cat.getIdatbyName(c));
            i=res.updateresto(r);
              if(i==1)
        {
                                   afficherresto();
                      loadDataresto();
                        Notification.sendNotification("Tabaani"," \n Resto modifié ." ,TrayIcon.MessageType.WARNING);
                        AlertDialog.display("Info","resto modifié");
                      clearTextFieldresto();

        }         
        }  
        
    }
    
    
    @FXML
   public void ajouterresto(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
       
        boolean isNomEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nomresto, error_nomr, "Nom resto est requis");
        boolean isBudgetEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_budget, error_budg, "Budget est requis");
        boolean isNbplaceEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nbplace, error_nbplace, "Nombre de place est requis");

            if( isNomEmpty && isBudgetEmpty && isNbplaceEmpty )
        {
            int i;
                           SMSAPI sms1= new SMSAPI();

        Image image1=null;
             image1= imgview.getImage();
            String photo = null;
             photo = saveToFileImageNormal(image1);
            String nom_resto= tf_nomresto.getText();
            int budget=Integer.valueOf(tf_budget.getText());
            int nbplace=Integer.valueOf(tf_nbplace.getText());
            String c = cmb_cat.getSelectionModel().getSelectedItem();
      
            ServiceResto res=new ServiceResto();
            ServiceCategorie cat=new ServiceCategorie();
            resto r = new resto( nbplace,budget,nom_resto,photo, cat.getIdatbyName(c));
            
            i=res.ajouterresto(r);
              if(i==1)
        {   afficherresto();
            loadDataresto();
             Notification.sendNotification("Tabaani"," \n Resto ajouté ." ,TrayIcon.MessageType.WARNING);
             AlertDialog.display("Info","resto ajouté");
           clearTextFieldresto();
                       

        }
           }}
   
   
     @FXML
    private void addImage(ActionEvent event) throws IOException{
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
              Image image = SwingFXUtils.toFXImage(bufferedImage, null);
             imgview.setImage(image);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     public static String saveToFileImageNormal(Image image)throws SQLException  {

        String ext = "jpg";
        File dir = new File("C:\\wamp64\\www\\pidev\\public\\images\\resto");
        String name;
        name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return name;
    }



    private void afficherresto(){

             nomrestoc.setCellValueFactory(new PropertyValueFactory <>("nomresto"));
             budgetc.setCellValueFactory(new PropertyValueFactory <>("budget"));
             breplacec.setCellValueFactory(new PropertyValueFactory <>("nbplace"));
    }
    
    
     private void loadDataresto() {
         datar.clear();
         try {
           pst =con.prepareStatement("Select id_resto as id_resto , nbplace as nbplace , budget as budget ,nomresto as nomresto ,restopic as restopic ,idCategorie as idCategorie from resto");

         rs=pst.executeQuery();
    
       while (rs.next()) {  
         int id_resto=rs.getInt("id_resto");
         int nbplace=rs.getInt("nbplace");
         int budget=rs.getInt("budget");
         String nomresto=rs.getString("nomresto");
         String restopic=rs.getString("restopic");
         int idCategorie=rs.getInt("idCategorie");

         //Servicecategorie cat =new Servicecategorie();
         ServiceResto resto=new ServiceResto();
         resto r=new resto(id_resto,nbplace,budget,nomresto,restopic,idCategorie);
       
             datar.add(r );
            
                   }
     }       
         
       catch (SQLException ex) {
           Logger.getLogger(ServiceResto.class.getName()).log(Level.SEVERE, null, ex);
       }
           tab_resto.setItems(datar);
    }
     
     
     private void initcatcombo() {
        ObservableList datacat=FXCollections.observableArrayList();
        cmb_cat.getSelectionModel().clearSelection();
             try {
           pst =con.prepareStatement("SELECT * from categorie");

            rs=pst.executeQuery();
         while (rs.next()) {                
             datacat.add(rs.getString(2));
     }       }
           catch (SQLException ex) {
           Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
       }
          cmb_cat.setItems(datacat);

}
     
     
   public resto gettemp(TableColumn.CellEditEvent edittedCell) {
        resto test = tab_resto.getSelectionModel().getSelectedItem();
       
        return test;
    }
    public int gettemp1(TableColumn.CellEditEvent edittedCell) {
        resto test = tab_resto.getSelectionModel().getSelectedItem();
        int x= test.getId_resto();
        return x;
    }
    
    
      private void setCellValueFromTableToTextFieldresto(){
    tab_resto.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        
        public void handle(MouseEvent event) {
       resto resto=tab_resto.getItems().get(tab_resto.getSelectionModel().getSelectedIndex());


       tf_nbplace.setText(Integer.toString(resto.getNbplace()));
       tf_budget.setText(Integer.toString(resto.getBudget()));
       tf_nomresto.setText(resto.getNomresto());


        cmb_cat.getSelectionModel();
         TableColumn.CellEditEvent edittedcell = null;
            resto r=gettemp(edittedcell);  
      System.out.println(r);
            String photo=r.getRestopic();
                    System.out.println(photo);
        Image imageURL= new Image("file:///C:/wamp64/www/pidev/public/images/resto/" + photo);
        System.out.println(imageURL);
        imgview.setImage(imageURL);
        }
});
      }   
      
      private void clearTextFieldresto(){
    tf_nomresto.clear();
    tf_budget.clear();
        tf_nbplace.clear();
           cmb_cat.getSelectionModel().clearSelection();
      
}
      
      
      
      
      public void searchR(){
resto_search.setOnKeyReleased(e->{
    if(resto_search.getText().equals("")){
        loadDataresto();
    }
    else{
        datar.clear();
          String sql = "Select * from resto where nomresto LIKE '%"+resto_search.getText()+"%'"
                + "UNION Select * from resto where budget LIKE '%"+resto_search.getText()+"%'" ;
    try {
      
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        while(rs.next())
        {
              int id_resto=rs.getInt("id_resto");
         int nbplace=rs.getInt("nbplace");
         int budget=rs.getInt("budget");
         String nomresto=rs.getString("nomresto");
         String restopic=rs.getString("restopic");
         int idCategorie=rs.getInt("idCategorie");

         //Servicecategorie cat =new Servicecategorie();
         ServiceResto resto=new ServiceResto();
         datar.add(new resto(id_resto,nbplace,budget,nomresto,restopic,idCategorie));
       
             
 
        }
        
        tab_resto.setItems(datar);
    } catch (SQLException ex) {
        Logger.getLogger(RestoController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }
});
}
      
    
       
      ///Categorie//////////////////////////////////////////////////////////////////////////:
 
      
    @FXML
 public void deletecategorie(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
        
        
 TableColumn.CellEditEvent edittedcell = null;
            int x=gettempidcat(edittedcell);         
            int i;
            ServiceCategorie cat=new ServiceCategorie();
            int idr1=Integer.valueOf(x);
           
            
            i=cat.deletecategorie(idr1);
              if(i==1)
        {
            affichercategorie();
             loadDatacategorie();
             Notification.sendNotification("Tabaani"," \n Categorie supprimé ." ,TrayIcon.MessageType.WARNING);
              AlertDialog.display("Info","Categorie supprimé");
                          clearTextFieldcat(); 
                         initcatcombo();
            
        }
               

              
    }          
         @FXML    
          public void ajoutercategorie(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
           boolean isCatEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nomcat, error_cat, "Categorie est requis");
       

            if( isCatEmpty  )
        {
            int i;
            String nom_categorie= tf_nomcat.getText();
           
            ServiceCategorie cat=new ServiceCategorie();
            Categorie c = new Categorie(nom_categorie);
            
            i=cat.ajoutercategorie(c);
              if(i==1)
        {         affichercategorie();
                  loadDatacategorie();
                  Notification.sendNotification("Tabaani"," \n Categorie ajouté ." ,TrayIcon.MessageType.WARNING);
                 AlertDialog.display("Info","Categorie ajouté");
                clearTextFieldcat();
           initcatcombo();
           
           
        }
          
        }  
          }
        
    
    
     private void affichercategorie(){

             categoriec.setCellValueFactory(new PropertyValueFactory <>("nom_categorie"));
    }
    
      
     
     private void loadDatacategorie() {
           datac.clear();
         try {
           pst =con.prepareStatement("Select id_categorie as id_categorie , nom_categorie as nom_categorie  from categorie");

          rs=pst.executeQuery();
    
            while (rs.next()) {  
         int id_categorie=rs.getInt("id_categorie");
         String nom_categorie=rs.getString("nom_categorie");
        

         //Servicecategorie cat =new Servicecategorie();
            ServiceCategorie cat=new ServiceCategorie();
         Categorie c=new Categorie(id_categorie,nom_categorie);
       
             datac.add(c);
            
                   }
     }       
         
       catch (SQLException ex) {
           Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
       }
       tab_cat.setItems(datac);
    }

     
  public Categorie gettempcat(TableColumn.CellEditEvent edittedCell) {
        Categorie test = tab_cat.getSelectionModel().getSelectedItem();
       
        return test;
      }
   public int gettempidcat(TableColumn.CellEditEvent edittedCell) {
        Categorie test = tab_cat.getSelectionModel().getSelectedItem();
        int x= test.getId_categorie();
        return x;
    }
   
   private void setCellValueFromTableToTextFieldcat(){
          tab_cat.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
       Categorie categorie=tab_cat.getItems().get(tab_cat.getSelectionModel().getSelectedIndex());

     tf_nomcat.setText(categorie.getNom_categorie());


         TableColumn.CellEditEvent edittedcell = null;
            Categorie c=gettempcat(edittedcell);  
      System.out.println(c);
            
        }
});
      }   
      
   
    @FXML
    public void updatecategorie(ActionEvent event) throws SQLException, AWTException, MalformedURLException {
        boolean isCatEmpty=validation.TextFieldvalidation.isTextFieldNoEmpty(tf_nomcat, error_cat, "Categorie est requis");
       

            if( isCatEmpty  )
        
        {
        int i;
        TableColumn.CellEditEvent edittedcell = null;
            int x=gettempidcat(edittedcell);
               String nom_categorie= tf_nomcat.getText();
            ServiceCategorie cat=new ServiceCategorie();
            Categorie c;
             c = new Categorie(x,nom_categorie);
;
            i=cat.updatecategorie(c);
              if(i==1)
        {
                    affichercategorie();
                    loadDatacategorie();
                    Notification.sendNotification("Tabaani"," \n Categorie modifié ." ,TrayIcon.MessageType.WARNING);
                     AlertDialog.display("Info","Categorie modifié");
           clearTextFieldcat();  ;
                    initcatcombo();

            
        } 
        }  
    }

private void clearTextFieldcat(){
    tf_nomcat.clear();
  }




////////////////////////Reservation///////////////////////////

private void afficherreservationresto(){

             nbpersonnec.setCellValueFactory(new PropertyValueFactory <>("nbpersonne"));
             mailc.setCellValueFactory(new PropertyValueFactory <>("mail"));
             numeroc.setCellValueFactory(new PropertyValueFactory <>("numero"));


    }

   private void loadDatareservation() {
         datares.clear();
         try {
           pst =con.prepareStatement("Select  nbpersonne as nbpersonne ,mail as mail ,numero as numero  from reservation_resto");

         rs=pst.executeQuery();
    
       while (rs.next()) {  
         
         int nbpersonne=rs.getInt("nbpersonne");
         String mail=rs.getString("mail");
         int numero=rs.getInt("numero");
         
         ServiceReservationResto reservation_resto=new ServiceReservationResto();
         reservation_resto res=new reservation_resto(nbpersonne,mail,numero);
       
             datares.add(res );
            
                   }
     }       
         
       catch (SQLException ex) {
           Logger.getLogger(reservation_resto.class.getName()).log(Level.SEVERE, null, ex);
       }
           tab_reservationresto.setItems(datares);
    }
}

   


 