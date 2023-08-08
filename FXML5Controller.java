/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class FXML5Controller implements Initializable {

    @FXML
    private Button tofrench;
    @FXML
    private TextField text;

    @FXML
    private Button btnsearch;

    @FXML
    private TableView<Word> tvword;

    @FXML
    private TableColumn<Word, Integer> collid;

    @FXML
    private TableColumn<Word, String> collword;

    @FXML
    private TableColumn<Word, String> colltype;

    @FXML
    private TableColumn<Word, String> colltraduction;

    @FXML
    private TableColumn<Word, String> collexample;

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException {
  if (event.getSource() == btnsearch)
        {
            searchWord();
        }
  else if (event.getSource() == tofrench)
  {
    searchWordeng();
  }
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement st;
    
  
     private void searchWordeng() throws SQLException {
      
          String mot = text.getText();
      if (mot.trim().isEmpty())
          
      {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("You have to enter the french word");
            alert.showAndWait();
      }
      else
      {
    try {
        connect = database.connect();
        String query1 = "select id, word, traduction, type, example from moten where word=?";
        prepare = connect.prepareStatement(query1);
        prepare.setString(1, text.getText());
        result = prepare.executeQuery();
        ObservableList<Word> wordlist = FXCollections.observableArrayList();
        if(!result.next()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Désolé we cannot find the word");
            alert.showAndWait();
        } else {
            do {
                Word word = new Word(result.getInt("id"), result.getString("word"), result.getString("type"), result.getString("traduction"), result.getString("example"));
                wordlist.add(word);
            } while (result.next());

            collid.setCellValueFactory(new PropertyValueFactory<>("id"));
            collword.setCellValueFactory(new PropertyValueFactory<>("word"));
            colltype.setCellValueFactory(new PropertyValueFactory<>("type"));
            colltraduction.setCellValueFactory(new PropertyValueFactory<>("traduction"));
            collexample.setCellValueFactory(new PropertyValueFactory<>("example"));

            tvword.setItems(wordlist);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (result != null) {
            result.close();
        }
        if (prepare != null) {
            prepare.close();
        }
        if (connect != null) {
            connect.close();
        }
    }
}

     }  
      private void searchWord() throws SQLException {
      
          String mot = text.getText();
      if (mot.trim().isEmpty())
          
      {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("You have to enter the french word");
            alert.showAndWait();
      }
      else
      {
    try {
        connect = database.connect();
        String query1 = "select id, word, traduction, type, example from motfr where word=?";
        prepare = connect.prepareStatement(query1);
        prepare.setString(1, text.getText());
        result = prepare.executeQuery();
        ObservableList<Word> wordlist = FXCollections.observableArrayList();
        if(!result.next()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Désolé we cannot find the word");
            alert.showAndWait();
        } else {
            do {
                Word word = new Word(result.getInt("id"), result.getString("word"), result.getString("type"), result.getString("traduction"), result.getString("example"));
                wordlist.add(word);
            } while (result.next());

            collid.setCellValueFactory(new PropertyValueFactory<>("id"));
            collword.setCellValueFactory(new PropertyValueFactory<>("word"));
            colltype.setCellValueFactory(new PropertyValueFactory<>("type"));
            colltraduction.setCellValueFactory(new PropertyValueFactory<>("traduction"));
            collexample.setCellValueFactory(new PropertyValueFactory<>("example"));

            tvword.setItems(wordlist);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (result != null) {
            result.close();
        }
        if (prepare != null) {
            prepare.close();
        }
        if (connect != null) {
            connect.close();
        }
    }
}

    }
      
      
}
