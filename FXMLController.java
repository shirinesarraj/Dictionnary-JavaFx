
package loginsignup;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;




public class FXMLController implements Initializable {

    
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Hyperlink createaccount;
    
    @FXML
    private BorderPane Login_form;
   @FXML
    private AnchorPane crudfrench;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
      @FXML
    void exit(MouseEvent event) {
    System.exit(0);
    }
    
    public void login_account(ActionEvent event) {

        String sql = "select username , password from login where username = ? and password = ? ";
        connect = database.connect();
        try{
        Alert alert;
        if (username.getText().isEmpty() || (password.getText().isEmpty()))
        {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill the block fildes");
        alert.showAndWait();
        }
        else{
            
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, username.getText());
        prepare.setString(2, password.getText());
        result = prepare.executeQuery();
        if (result.next())
        {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully login");
        alert.showAndWait();
        //login_form.setVisible(false);
      
        loginbtn.getScene().getWindow().hide();
      /* Parent root = FXMLLoader.load(getClass().getResource("FXML1.fxml")); 
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();*/
      
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML1.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
        else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("incorrect username/password");
        alert.showAndWait();
        }
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //create account
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    
    }
    

    
  
    
    
    
}
