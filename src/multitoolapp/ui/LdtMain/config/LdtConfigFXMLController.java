/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.ui.LdtMain.config;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import multitoolapp.common.PropertiesFileOperations;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class LdtConfigFXMLController implements Initializable {

    @FXML
    private TextField txtFieldInstanceName;
    @FXML
    private TextField textFieldUnixHost;
    @FXML
    private TextField textFieldUnixUser;
    @FXML
    private PasswordField TextUnixPassword;
    @FXML
    private TextField textFieldEnvCmd;
    @FXML
    private TextField TextFieldTempPath;
    @FXML
    private TextField TextFieldJDBCstring;
    @FXML
    private PasswordField TextAppsPassword;
    @FXML
    private Button btnSaveConfig;
    @FXML
    private Button btnConfigOk;
    @FXML
    private TextField txtNonAppsDBUser;
    @FXML
    private TextField pwdNonAppsDbUsePwd;

    @FXML
    private void btnSaveConfigAction(ActionEvent event) {
        String instanceName = txtFieldInstanceName.getText();

        if (instanceName.length() != 0 && textFieldUnixHost.getText().trim().length() != 0
                && textFieldUnixUser.getText().trim().length() != 0
                && TextUnixPassword.getText().trim().length() != 0
                && textFieldEnvCmd.getText().trim().length() != 0
                && TextFieldTempPath.getText().trim().length() != 0
                && TextFieldJDBCstring.getText().trim().length() != 0
                && TextAppsPassword.getText().trim().length() != 0) {
            try {

                System.out.println("Saving Config FIle");
                PropertiesFileOperations propFileOper = new PropertiesFileOperations();
                propFileOper.WritePropertiesFile(instanceName + "_INSTANCENAME", instanceName);
                propFileOper.WritePropertiesFile(instanceName + "_UNIX_HOST", textFieldUnixHost.getText());
                propFileOper.WritePropertiesFile(instanceName + "_UNIX_USER", textFieldUnixUser.getText());
                propFileOper.WritePropertiesFile(instanceName + "_UNIX_USER_PWD", TextUnixPassword.getText());
                propFileOper.WritePropertiesFile(instanceName + "_UNIX_ENV_CMD", textFieldEnvCmd.getText());
                propFileOper.WritePropertiesFile(instanceName + "_UNIX_TEMP_PATH", TextFieldTempPath.getText());
                propFileOper.WritePropertiesFile(instanceName + "_JDBC_CON_STRING", TextFieldJDBCstring.getText());
                propFileOper.WritePropertiesFile(instanceName + "_APPS_PWD", TextAppsPassword.getText());
                
                propFileOper.WritePropertiesFile(instanceName + "_NONAPPS_USR", txtNonAppsDBUser.getText());
                propFileOper.WritePropertiesFile(instanceName + "_NONAPPS_USR_PWD", pwdNonAppsDbUsePwd.getText());

                //confirm user that all data saved successfully
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Configuration!!");
                alert.setHeaderText(null);
                alert.setContentText("Configuration has been saved successfully!!");
                alert.showAndWait();
                Stage stage;
                stage = (Stage) btnSaveConfig.getScene().getWindow();
                stage.close();

            } catch (Exception ex) {
                Logger.getLogger(LdtConfigFXMLController.class.getName()).log(Level.SEVERE, null, ex);

            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Setup Missing");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all information correctly");
            alert.showAndWait();
            return;
        }

    }

    public void setInstanceName(String InstanceName) {
        txtFieldInstanceName.setText(InstanceName);
        System.out.println("txtFieldInstanceName.getText()before initilization"+txtFieldInstanceName.getText());
        setDetailsFromPropFile(txtFieldInstanceName.getText());

    }

    private void setDetailsFromPropFile(String InstanceName) {

        String[] InstanceInfoKeyArray = {InstanceName + "_INSTANCENAME",
            InstanceName + "_UNIX_HOST",
            InstanceName + "_UNIX_USER",
            InstanceName + "_UNIX_USER_PWD",
            InstanceName + "_UNIX_ENV_CMD",
            InstanceName + "_UNIX_TEMP_PATH",
            InstanceName + "_JDBC_CON_STRING",
            InstanceName + "_APPS_PWD"};

        String[] pro_INSTANCENAME = new String[2];
        String[] pro_UNIX_HOST = new String[2];
        String[] pro_UNIX_USER = new String[2];
        String[] pro_UNIX_USER_PWD = new String[2];
        String[] pro_UNIX_ENV_CMD = new String[2];
        String[] pro_UNIX_TEMP_PATH = new String[2];
        String[] pro_JDBC_CON_STRING = new String[2];
        String[] pro_APPS_PWD = new String[2];

        try {
            //reading the details from config file and setting in config window
            System.out.println("pro_UNIX_HOST:"+pro_UNIX_HOST[1]);
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            pro_INSTANCENAME = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[0]);
            pro_UNIX_HOST = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[1]);
            pro_UNIX_USER = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[2]);
            pro_UNIX_USER_PWD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[3]);
            pro_UNIX_ENV_CMD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[4]);
            pro_UNIX_TEMP_PATH = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[5]);
            pro_JDBC_CON_STRING = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[6]);
            pro_APPS_PWD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[7]);

            textFieldUnixHost.setText(pro_UNIX_HOST[1]);
            textFieldUnixUser.setText(pro_UNIX_USER[1]);
            TextUnixPassword.setText(pro_UNIX_USER_PWD[1]);
            textFieldEnvCmd.setText(pro_UNIX_ENV_CMD[1]);
            TextFieldTempPath.setText(pro_UNIX_TEMP_PATH[1]);
            TextFieldJDBCstring.setText(pro_JDBC_CON_STRING[1]);
            TextAppsPassword.setText(pro_APPS_PWD[1]);

        } catch (Exception ex) {
            Logger.getLogger(LdtConfigFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnConfigOkAction(ActionEvent event) {
        try {
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            String instanceName = txtFieldInstanceName.getText();
            propFileOper.WritePropertiesFile(instanceName + "_INSTANCENAME", instanceName);
            propFileOper.WritePropertiesFile(instanceName + "_UNIX_HOST", textFieldUnixHost.getText());
            propFileOper.WritePropertiesFile(instanceName + "_UNIX_USER", textFieldUnixUser.getText());
            propFileOper.WritePropertiesFile(instanceName + "_UNIX_USER_PWD", TextUnixPassword.getText());
            propFileOper.WritePropertiesFile(instanceName + "_UNIX_ENV_CMD", textFieldEnvCmd.getText());
            propFileOper.WritePropertiesFile(instanceName + "_UNIX_TEMP_PATH", TextFieldTempPath.getText());
            propFileOper.WritePropertiesFile(instanceName + "_JDBC_CON_STRING", TextFieldJDBCstring.getText());
            propFileOper.WritePropertiesFile(instanceName + "_APPS_PWD", TextAppsPassword.getText());

            propFileOper.WritePropertiesFile(instanceName + "_NONAPPS_USR", TextAppsPassword.getText());
            propFileOper.WritePropertiesFile(instanceName + "_NONAPPS_USR_PWD", TextAppsPassword.getText());

            Stage stage;
            stage = (Stage) btnConfigOk.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(LdtConfigFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("txtFieldInstanceName.getText()"+txtFieldInstanceName.getText());
        //setDetailsFromPropFile(txtFieldInstanceName.getText());
    }
//
}
