/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class FXML4Controller implements Initializable {


    @FXML
    private Button btnuser;

    @FXML
    private Button btnadminstrator;

    @FXML
    private Button btnexportimport;
    @FXML
    private Button listage;

    @FXML
    void eventhandler(ActionEvent event) throws IOException {
  if (event.getSource() == btnuser)
        {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML5.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
            
        }
        else if (event.getSource() == btnadminstrator)
        {
            
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
        else if (event.getSource() ==  btnexportimport)
        {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("importexport.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
        else if (event.getSource() ==  listage)
        {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Listage.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
