/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import ConnexionBD.CnxBD;
import Entity.Event;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author nour
 */
public class EventController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfNbplace;
    @FXML
    private TextField tfLieux;
    @FXML
    private TextField tfPrix;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<?> tvEvent;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colNbplace;
    @FXML
    private TableColumn<?, ?> colLieux;
    @FXML
    private TableColumn<?, ?> colPrix;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object btninsert = null;
       if(event.getSource() == btninsert){
            insertRecord();    
        } /*else if (event.getSource()= btnupdate){
            updateRecord();
    } else if (event.getSource()= btndelete){
            deleteButton();
            }*/
            
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /* public Connection getConnection(){ 
     Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pivision", "root","");
            return conn;
        }catch(SQLException ex){
           System.out.println("Error: " + ex.getMessage()); 
           return null;
        }
    }*/
   
    
    public ObservableList<Event> getEventList(){
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        Connection connection = null ;
        connection = CnxBD.getInstance().getCnx();
        String query = "select * FROM event ";
        Statement st;
        ResultSet rs;
        
        try{
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Event event;
            while(rs.next()){
            event = new Event(rs.getInt("id_event"), rs.getString("description"), rs.getInt("nb_place"), rs.getString("lieux"), rs.getFloat("prix"));
            eventList.add(event);
            }
        }catch(SQLException ex){
    }
        return eventList;
        }
    
    
    public void showEvent(){
        ObservableList<Event> getEventList = null;
        ObservableList<Event> list = getEventList;
        
       
}
    
    
    private void insertRecord(){
        String query = "INSERT INTO event VALUES (" + tfDescription.getText() + "','" + tfNbplace.getText() + "','" 
                + tfLieux.getText() + "'," + tfPrix.getText() + ") " ;
        executeQuery(query);
        showEvent();
    }
    
    /*private void updateRecord(){
        String query = "UPDATE event SET  description =  "'nb_place = '" + tfNbplace.getText() + "',lieux" + tfLieux.getText() + ",prix" + tfPrix.getText() +" ;
        
        executeQuery(query);
        showEvent();
    } */ 
    
    
    private void deleteButton(){
    
        String query = " DELETE FROM event WHERE nb_place = " + tfNbplace.getText() + "";
        executeQuery(query);
         showEvent();

     
    }

    private void executeQuery(String query) {      
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(SQLException ex){ 
        }
        
}

    private Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    }
    
    





    
       
    
    
    

