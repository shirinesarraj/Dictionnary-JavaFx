/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class ImportexportController implements Initializable {

@FXML
private Button btnexport;

@FXML
private Button btnimport;

@FXML
private ListView<String> listview;

@FXML
private Button btngetback;


@FXML
private ListView<String> listView;

@FXML
private void importData(ActionEvent event) {
    try{
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Joindre fichier");

    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
    );
    
    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
        TextInputDialog dialog = new TextInputDialog(",");
        dialog.setTitle("Délimiteur de champ");
        dialog.setHeaderText("Spécifiez le délimiteur de champ utilisé dans le fichier");
        dialog.setContentText("Délimiteur de champ :");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String delimiter = result.get();
            List<String> dataList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(delimiter);
                    StringBuilder sb = new StringBuilder();
                    for (String field : fields) {
                        sb.append(field.trim());
                        sb.append(" ");
                    }
                    dataList.add(sb.toString().trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObservableList<String> observableList = FXCollections.observableArrayList(dataList);
            listview.setItems(observableList);
        }
    }
    } 
      catch (Exception e)
    {
        e.printStackTrace();
    }
    }



  @FXML
void getback(ActionEvent event) {
   
}

    @FXML
    void exportdata(ActionEvent event) {
    try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les données");

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            ObservableList<String> items = listview.getItems();
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
            writer.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    }
    
    
 

