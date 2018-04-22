/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.ui.LdtMain.InstanceList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import multitoolapp.common.PropertiesFileOperations;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class InstanceListFXMLController implements Initializable {

    @FXML
    private TextField textMasterInstanceNames;
    @FXML
    private Button btnSaveMasterInstance;
    @FXML
    private Button btnMasterCancle;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
 
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            String[] KeyValuePair = propFileOper.ReadPropertiesFile("MASTERTINSTANCELIST");
            System.out.println("hear" + KeyValuePair[1]);//Arrays.toString(KeyValuePair));
            if (KeyValuePair[1].trim().length() == 0 || "null".equals(KeyValuePair[1].trim())) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the instance and save");
                alert.showAndWait();
            }

            textMasterInstanceNames.setText(KeyValuePair[1]);
        } catch (Exception ex) {
            Logger.getLogger(InstanceListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void BtnInstanceListSaveAction(ActionEvent event) {
        //((Node) event.getSource()).getScene().getWindow().hide();
        try {
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            propFileOper.WritePropertiesFile("MASTERTINSTANCELIST", textMasterInstanceNames.getText());
        } catch (Exception ex) {
            Logger.getLogger(InstanceListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage;
        stage = (Stage) btnSaveMasterInstance.getScene().getWindow();
        stage.close();

        //((Node) event.getSource()).getScene().getWindow().hide();
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/multitoolapp/ui/LdtMain/LdtMainWindow.fxml"));
            Stage stagea = new Stage();
            stage.setTitle("Ldt One Click Tool");
            // stage.initModality(Modality.WINDOW_MODAL);
            stagea.setScene(new Scene(parent));
            stagea.show();
        } catch (IOException ex) {
            Logger.getLogger(InstanceListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnInstanceListCancleAction(ActionEvent event) {
        Stage stage;
        stage = (Stage) btnMasterCancle.getScene().getWindow();
        stage.close();
    }

}
