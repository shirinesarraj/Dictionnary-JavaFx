/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginsignup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class ListageController implements Initializable {

    @FXML
    private TextField champRecherche;

    @FXML
    private TextArea champResultat;
    @FXML
    private ChoiceBox<String> choixLangue;
     @FXML
    private void rechercherMot(ActionEvent event) {
    String motRecherche = champRecherche.getText();
    String langueSelectionnee = choixLangue.getValue();
    String resultat = "";
    if (langueSelectionnee.equals("English")) {
        String sql = "SELECT traduction FROM moten WHERE word = ?";
        try (
            Connection connect = database.connect();
            PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, motRecherche);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat += rs.getString("traduction") + ", ";
            }
            if (!resultat.isEmpty()) {
                resultat = resultat.substring(0, resultat.length() - 2); 
            } else {
                resultat = "Aucune traduction trouvée pour ce word.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else if (langueSelectionnee.equals("French")) {
        String sql = "SELECT traduction FROM motfr WHERE word = ?";
        try (
            Connection connect = database.connect();
             PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, motRecherche);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultat += rs.getString("traduction") + ", ";
            }
            if (!resultat.isEmpty()) {
                resultat = resultat.substring(0, resultat.length() - 2); 
            } else {
                resultat = "Aucune traduction trouvée pour ce mot.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    champResultat.setText(resultat);
}

        @FXML
        private void listerTousLesMots(ActionEvent event) {
        String langueSelectionnee = choixLangue.getValue();
        String sql = "";
        if (langueSelectionnee.equals("English")) {
        sql = "SELECT word, GROUP_CONCAT(traduction SEPARATOR ', ') AS traduction "
        + "FROM moten GROUP BY word ORDER BY word";
        } else if (langueSelectionnee.equals("French")) {
        sql = "SELECT word, GROUP_CONCAT(traduction SEPARATOR ', ') AS traduction "
        + "FROM motfr GROUP BY word ORDER BY word";
        }
        String resultat = "";
        try (Connection connect = database.connect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
        resultat += rs.getString("word") + " : " + rs.getString("traduction") + "\n";
        }
        if (resultat.isEmpty()) {
        resultat = "Aucun mot trouvé dans la langue sélectionnée.";
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        champResultat.setText(resultat);
        }

@FXML
private void enregistrerDansUnFichier(ActionEvent event) {
    String contenu = champResultat.getText();
    try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier texte");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(contenu);
            fileWriter.close();
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<String> langues = FXCollections.observableArrayList("English", "French");
    choixLangue.setItems(langues);

    }    
    
}
