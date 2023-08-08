/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import static java.awt.SystemColor.text;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class FXML3Controller implements Initializable {

    @FXML
    private Label lableword;

    @FXML
    private Label labeltype;

    @FXML
    private Label labeltraduction;

    @FXML
    private Label labelexample;

    @FXML
    private TextField tfword;

    @FXML
    private TextField tftype;

    @FXML
    private TextField tfexample;

    @FXML
    private TextField tftraduction;

    @FXML
    private TableView<Word> tvword;

    @FXML
    private TableColumn<Word, Integer> collid;

    @FXML
    private TableColumn<Word, Integer> collword;

    @FXML
    private TableColumn<Word, Integer> colltype;

    @FXML
    private TableColumn<Word, Integer> colltraduction;

    @FXML
    private TableColumn<Word, Integer> collexample;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnswitch;
  @FXML
    private TextField text;
    @FXML
    void HandleButtonAction(ActionEvent event) {

    }

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException {

        if (event.getSource() == btnInsert)
        {
            insertRecord();
        }
        else if (event.getSource() == btnUpdate)
        {
           updateRecord();
        }
        else if (event.getSource() == btnDelete )
        {
           deleteRecord();
        }
        else if (event.getSource() == btnsearch )
        {
         searchWord();
        }
    }
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement st;

    public void insertRecord() throws SQLException
   {

        tfword.clear();
        tftype.setVisible(true);
        tftraduction.setVisible(true);
        tfexample.setVisible(true);
        connect = database.connect();
        st = connect.createStatement();

        String word = tfword.getText();
        if(word.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a word");
            alert.showAndWait();
          }
        else{
        String type = tftype.getText();
        String traduction = tftraduction.getText();
        String example = tfexample.getText();

        String insertQuery = "INSERT INTO moten (word, type, traduction, example) VALUES ('" + word + "','" + type + "','" + traduction + "','" + example + "')";
        executeQuery(insertQuery);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Successfully inserted");
        alert.showAndWait();
        }
    }
      public void searchWord() throws SQLException
   {   
      String mot = text.getText();
      if (mot.trim().isEmpty())
          
      {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("You have to enter an English word");
            alert.showAndWait();
      }
      else
      {
    try {
        connect = database.connect();
        String query1 = "select id, word, type, traduction,example from moten where word=?";
        prepare = connect.prepareStatement(query1);
        prepare.setString(1, text.getText());
        result = prepare.executeQuery();
        ObservableList<Word> wordlist = FXCollections.observableArrayList();
        if(!result.next()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Désolé le mot En n'existe pas dans la base de données.");
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
}}
    
   public void updateRecord() {
    String word = tfword.getText().trim();
    if (word.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please enter a word");
        alert.showAndWait();
        return;
    }

    boolean wordExists = isWordExists(word);
    if (!wordExists) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("You have to enter the English word");
        alert.showAndWait();
        return;
    }

    tfword.setDisable(true);
    tftype.setVisible(true);
    tftraduction.setVisible(true);
    tfexample.setVisible(true);
    try {
        Connection connect = database.connect();
        String query = "UPDATE motEn SET type=?, traduction=?, example=? WHERE word=?";
        PreparedStatement stmt = connect.prepareStatement(query);
        stmt.setString(1, tftype.getText());
        stmt.setString(2, tftraduction.getText());
        stmt.setString(3, tfexample.getText());
        stmt.setString(4, word);
        stmt.executeUpdate();
        stmt.close();
        connect.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

      public boolean isWordExists(String word) {
    boolean exists = false;
    try {
        // Create a connection to the database
        connect = database.connect();
        // Create a statement
        String sql = "SELECT * FROM moten WHERE word = ?";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, word);

       
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            exists = true;
        }

        // Close the statement and the connection
        stmt.close();
        connect.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return exists;
}

     public void deleteRecord()
   {
    boolean exits = false;
    lableword.setVisible(true);
    tfword.setVisible(true);
    tftype.setVisible(false);
    tftraduction.setVisible(false);
    tfexample.setVisible(false);
    String word = tfword.getText();

    if(word.trim().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please enter a word");
        alert.showAndWait();
        return;
        } else if (isWordExists(tfword.getText()) == true) {
            lableword.setVisible(false);
            tfword.setDisable(true);
            tftype.setVisible(true);
            tftraduction.setVisible(true);
            tfexample.setVisible(true);

    try {
        connect = database.connect();
        String query = "SELECT type, traduction, example FROM moten WHERE word=?";
        PreparedStatement stmt = connect.prepareStatement(query);
        stmt.setString(1, tfword.getText());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String type = rs.getString("type");
            String translation = rs.getString("traduction");
            String example = rs.getString("example");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the word \"" + tfword.getText() + "\"?\nType: " + type + "\nTranslation: " + translation + "\nExample: " + example);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                query = "DELETE FROM motEn WHERE word=?";
                stmt = connect.prepareStatement(query);
                stmt.setString(1, tfword.getText());
                stmt.executeUpdate();
                Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
                deletedAlert.setHeaderText(null);
                deletedAlert.setContentText("The word \"" + tfword.getText() + "\" has been deleted from the database.");
                deletedAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("The word \"" + tfword.getText() + "\" does not exist in the database.");
            alert.showAndWait();
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
} else {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText("You have to enter the english word.");
    alert.showAndWait();
}

   }
      private void executeQuery(String query)
    {
            connect = database.connect();
           try {
               st = connect.createStatement();
               st.executeUpdate(query);
           }
           catch(Exception e){
           e.printStackTrace();
           }
    }
      public void TextfieldDesign()
    {
         if (tfword.isFocused())
        {
            //tfid.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tfword.setStyle("-fx-border-width:2px ; -fx-background-color:#fff");
            tftype.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tftraduction.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tfexample.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        }else if (tftype.isFocused())
        {
            //tfid.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tfword.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tftype.setStyle("-fx-border-width:2px ; -fx-background-color:#fff");
            tftraduction.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
            tfexample.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");  
        }else if (tftraduction.isFocused())
        {
            
             //tfid.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tfword.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tftype.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tftraduction.setStyle("-fx-border-width:2px ; -fx-background-color:#fff");
        tfexample.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");  
        }
        else 
        {
       //tfid.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tfword.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tftype.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tftraduction.setStyle("-fx-border-width:1px ; -fx-background-color:transparent");
        tfexample.setStyle("-fx-border-width:2px ; -fx-background-color:#fff");  
        }
        
    
    }
       @FXML
    void switchfr(ActionEvent event) throws IOException {
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to switch to from French to English?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML1.fxml"));
                    loader.setRoot(new AnchorPane());
                    
                       Parent root = loader.load();
        
        // Create a new window or replace the content of the current window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
                }
    }
    public void defaultId()
    {
        text.setStyle("-fx-border-width:2px ; -fx-background-color:#fff");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defaultId();
        //lableword.setVisible(false);
    }    

   
    }

