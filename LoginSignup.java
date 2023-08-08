/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginSignup extends Application {
    
    @Override
    public void start(Stage Stage)  {
      //Parent root;
      try{
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML4.fxml"));
        fxmlLoader.setRoot(new AnchorPane());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
       
        Stage.setScene(scene);
        Stage.show();

             }
     /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start.fxml"));
        fxmlLoader.setRoot(new AnchorPane());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
       
        Stage.setScene(scene);
        Stage.show();*/
      //root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
     /* Scene scene = new Scene (root);
      Stage.setScene(scene);
      Stage.show();
      */
       
      catch(IOException ex)
      {
      ex.printStackTrace();
      }
      
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
