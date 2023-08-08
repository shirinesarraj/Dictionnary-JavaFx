/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class FXML1Controller implements Initializable {

   
    @FXML
    private TextField tfword;

    @FXML
    private TextField tftype;

    @FXML
    private TextField tftraduction;

    @FXML
    private TextField tfexample;

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
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;
    
    @FXML
    private TextField text;
    @FXML
    private Label infolabel;
        
    @FXML
    private Button btnswitch;
    @FXML
    private Button btnexportimport;

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException, IOException {

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
        else if (event.getSource() == btnSearch )
        {
          searchWord();
        }
      
    
    
    }
   
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement st;

   
    public boolean isconfirmed()
    {
        boolean exits = false;
       infolabel.setVisible(true);
       tfword.setVisible(true);
       tftype.setVisible(false);
       tftraduction.setVisible(false);
       tfexample.setVisible(false);
       
       if (tfword.getText() == null)
       {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Now you have to enter a word");
            alert.showAndWait();
       }
       else if (isWordExists(tfword.getText()) == true )
       {
           infolabel.setVisible(false);
       tfword.setDisable(true);
       tftype.setVisible(true);
       tftraduction.setVisible(true);
       tfexample.setVisible(true);
       //btnconfirm.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Now you have to click on update to update it");
            alert.showAndWait();
                exits = true;
       }
       else if (isWordExists(tfword.getText()) == false ) 
       {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("You have to enter the french word");
            alert.showAndWait();
       }
       return exits;
    }
    public ObservableList<Word> getWordList()
    {
        
    ObservableList<Word> wordlist = FXCollections.observableArrayList();
    String query = "select * from motfr";
    connect = database.connect();
     try{
        st = connect.createStatement();
        result = st.executeQuery(query);
        Word word;
        while(result.next())
        {
           word = new Word(result.getInt("id") , result.getString("word") , result.getString("type"),result.getString("traduction") ,result.getString("example"));
           wordlist.add(word);
        }
        }
    catch(Exception e)
    {
     e.printStackTrace();
    }
    return wordlist;
    }
     
     public void showWord(ResultSet rs)
     {
    ObservableList<Word> List = FXCollections.observableArrayList();
    try {
        while(rs.next()) {
            Word w = new Word();
            w.setId(0); 
            w.setWord(rs.getString("word"));
            w.setType(rs.getString("type"));
            w.setTraduction(rs.getString("traduction"));
            w.setExample(rs.getString("example"));
            List.add(w);
            
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    collid.setVisible(false); 
    collword.setCellValueFactory(new PropertyValueFactory<>("word"));
    colltype.setCellValueFactory(new PropertyValueFactory<>("type"));
    colltraduction.setCellValueFactory(new PropertyValueFactory<>("traduction"));
    collexample.setCellValueFactory(new PropertyValueFactory<>("example"));
    tvword.setItems(List);
}
/*
 ObservableList<Word> List = getWordList();
    collid.setCellValueFactory(new PropertyValueFactory<> ("id"));
    collword.setCellValueFactory(new PropertyValueFactory<> ("Word"));
    colltype.setCellValueFactory(new PropertyValueFactory<> ("type"));
    colltraduction.setCellValueFactory(new PropertyValueFactory<> ("traduction"));
    collexample.setCellValueFactory(new PropertyValueFactory<> ("example"));
    
    tvword.setItems(List);
    */
    
   

            
   public void insertRecord() throws SQLException
   {

   String word = tfword.getText();

if(word.trim().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Please enter a word");
    alert.showAndWait();
    return;
}

String type = tftype.getText();
String traduction = tftraduction.getText();
String example = tfexample.getText();

if (type.trim().isEmpty() || traduction.trim().isEmpty() || example.trim().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Please enter type, translation and example");
    alert.showAndWait();
    return;
}

connect = database.connect();

String insertQuery = "INSERT INTO motfr (word, type, traduction, example) VALUES (?, ?, ?, ?)";

try {
    PreparedStatement stmt = connect.prepareStatement(insertQuery);
    stmt.setString(1, word);
    stmt.setString(2, type);
    stmt.setString(3, traduction);
    stmt.setString(4, example);

    int result = stmt.executeUpdate();

    if (result > 0) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Successfully inserted");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Insertion failed");
        alert.showAndWait();
    }

} catch (SQLException e) {
    e.printStackTrace();
} finally {
    connect.close();
}

     }
       /*String query1 = "insert into motfr values (" + tfid.getText() + ",'" + tfword.getText() + "','" + tftype.getText() + "','" + tftraduction.getText() + "','" + tfexample.getText() + "')";
       executeQuery(query1);
       showWord();*/
   
   public void deleteRecord() {
String word = tfword.getText();

   if(word.trim().isEmpty()) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText("Please enter a word");
    alert.showAndWait();
    return;
} else if (!isWordExists(word)) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText("You have to enter the French word.");
    alert.showAndWait();
    return;
}

tfword.setVisible(true);
tftype.setVisible(false);
tftraduction.setVisible(false);
tfexample.setVisible(false);
infolabel.setVisible(false);
tfword.setDisable(true);
tftype.setVisible(true);
tftraduction.setVisible(true);
tfexample.setVisible(true);

try {
    connect = database.connect();
    String query = "SELECT type, traduction, example FROM motfr WHERE word=?";
    PreparedStatement stmt = connect.prepareStatement(query);
    stmt.setString(1, word);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        String type = rs.getString("type");
        String translation = rs.getString("traduction");
        String example = rs.getString("example");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the word \"" + word + "\"?\nType: " + type + "\nTranslation: " + translation + "\nExample: " + example);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            query = "DELETE FROM motfr WHERE word=?";
            stmt = connect.prepareStatement(query);
            stmt.setString(1, word);
            stmt.executeUpdate();
            Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
            deletedAlert.setHeaderText(null);
            deletedAlert.setContentText("The word \"" + word + "\" has been deleted from the database.");
            deletedAlert.showAndWait();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("The word \"" + word + "\" does not exist in the database.");
        alert.showAndWait();
    }
} catch(Exception e) {
    e.printStackTrace();
} finally {
    tfword.setDisable(false);
}
}


    
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
        alert.setContentText("You have to enter the french word");
        alert.showAndWait();
        return;
    }

    tfword.setDisable(true);
    tftype.setVisible(true);
    tftraduction.setVisible(true);
    tfexample.setVisible(true);
    try {
        Connection connect = database.connect();
        String query = "UPDATE motFr SET type=?, traduction=?, example=? WHERE word=?";
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
        connect = database.connect();
        String sql = "SELECT * FROM motfr WHERE word = ?";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, word);

       
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            exists = true;
        }

        stmt.close();
        connect.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return exists;
}

   public void searchWord() throws SQLException
   {   
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
            alert.setContentText("Désolé le mot fr n'existe pas dans la base de données.");
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
    void switchenglish(ActionEvent event) throws IOException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to switch to from English to french?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML3.fxml"));
                  //FXML3Controller controller = new FXML3Controller(); 
              //  loader.setController(controller);
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
        //showWord();
        infolabel.setVisible(false);
    }  

    void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
   
}
