/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.ui.LdtMain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import multitoolapp.common.GetFileOverNetwork;
//import ldtTool.config.ConfigViewController;
import multitoolapp.common.PropertiesFileOperations;
import multitoolapp.common.PutFileOnNetwork;
import multitoolapp.common.ShellExecuter;
import multitoolapp.ui.LdtMain.config.LdtConfigFXMLController;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class LdtMainWindowController implements Initializable {

    private final String instanceDefaultValue = "Select An Instance!";
    private final String ComponentTypeDefaultValue = "Select Cmponent Type!";
    private final String NoDirSelected = "No Directory/File Selected";
    public String SelectedinstanceName;
    String dirPathToSaveLDT;
    String FileNameToBeUploaded;
    String dirPathFromUploadLDT;

    @FXML
    private MenuItem MenuBarInstanceSetup;
    @FXML
    private RadioButton rdbtnDownload;
    @FXML
    private ToggleGroup OperationGroup;
    @FXML
    private RadioButton rdbtnUpload;
    @FXML
    private ComboBox<String> ComboBoxInstance;
    @FXML
    private Button btnConfiguration;
    @FXML
    private ComboBox<String> ComboBoxCompType;
    @FXML
    private TextField TextFieldCompName;
    @FXML
    private TextField FileFolderPath;
    @FXML
    private Button BtnOk;
    @FXML
    private Button BtnCancel;

    ObservableList<String> instanceList = null;// FXCollections.observableArrayList("BIODEVC");
    ObservableList<String> CompoTypeList = FXCollections.observableArrayList("Valueset", "Concurrent Programe",
            "Lookups", "Alerts", "Menu");

    @FXML
    private Button btnBrowse;
    @FXML
    private MenuBar menuBarLdtMain;

    public LdtMainWindowController() {
        this.SelectedinstanceName = null;
    }

    @FXML
    private void ComboBoxInstanceAction(ActionEvent event) {
        try {
            System.out.println("ComboBoxInstanceACTION:" + ComboBoxInstance.getValue());
            SelectedinstanceName = ComboBoxInstance.getValue();
            // Read The Properties File as soon as instance get selected
        } catch (Exception ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void MenuInstanceSetupAction(ActionEvent event) {

        try {

            Stage stage = (Stage) menuBarLdtMain.getScene().getWindow();
            stage.hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/multitoolapp/ui/LdtMain/InstanceList/InstanceListFXML.fxml").openStream());

            Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
            boolean add = primaryStage.getIcons().add(image);

            primaryStage.initModality(Modality.APPLICATION_MODAL); // 1 Add one

            primaryStage.setTitle("InstanceNameList Setup Window");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ConfigBtnAction(ActionEvent event) {

        try {
            if (ComboBoxInstance.getValue() != instanceDefaultValue) {
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(
                        getClass().getResource("/multitoolapp/ui/LdtMain/config/LdtConfigFXML.fxml").openStream());
                Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
                boolean add = primaryStage.getIcons().add(image);
                LdtConfigFXMLController configViewController = (LdtConfigFXMLController) loader.getController();
                configViewController.setInstanceName(ComboBoxInstance.getValue());
                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setTitle("Configuration iSetup Window");
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                // stage.setScene(new Scene(root));
                primaryStage.show();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Instance Not Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select an Instance!!");
                alert.showAndWait();

            }
        } catch (IOException ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ComboBoxComponentNameAction(ActionEvent event) {
    }

    @FXML
    private void ValidateCompFieldAction(ActionEvent event) {
        System.out.println("Validate compName action");

    }

    @FXML
    private void FileChooserAction(ActionEvent event) {

        if (rdbtnUpload.isSelected()) {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            File file = chooser.showOpenDialog(new Stage());
            // List<File> file = chooser.showOpenMultipleDialog(new Stage());
            // showOpenMultipleDialog
            if (file == null) {
                FileFolderPath.setText(NoDirSelected);
            } else {
                FileFolderPath.setText(file.getAbsolutePath());
                // file.getParentFile().toString();
                FileNameToBeUploaded = file.getName();

                dirPathFromUploadLDT = file.getParentFile().toString();
                System.out.println("dirPathFromUploadLDT:" + dirPathFromUploadLDT);// Directory Path from where ldt will

                TextFieldCompName.setText(FileNameToBeUploaded);

            }
        }
        if (rdbtnDownload.isSelected()) {
            System.out.println("Bingo its working");
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory == null) {
                FileFolderPath.setText(NoDirSelected);
            } else {

                dirPathToSaveLDT = selectedDirectory.getAbsolutePath();
                FileFolderPath.setText(dirPathToSaveLDT);
            }

        }

    }

    @FXML
    private void RadioBtnDwnldAction(ActionEvent event) {
        TextFieldCompName.setDisable(false);

    }

    @FXML
    private void RadioBtnUplodAction(ActionEvent event) {
        TextFieldCompName.setDisable(true);
    }

    @FXML
    private void OkBtnAction(ActionEvent event) {
        // validation of fields on LDT main screen Applicaion
        if (instanceDefaultValue == null ? (ComboBoxInstance.getValue()) == null
                : instanceDefaultValue.equals(ComboBoxInstance.getValue())) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, please Select Instance!!");

            alert.showAndWait();

            event.consume();
        } else if (ComponentTypeDefaultValue == null ? (ComboBoxCompType.getValue()) == null
                : ComponentTypeDefaultValue.equals(ComboBoxCompType.getValue())) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, please Select Component Type!!");

            alert.showAndWait();

            event.consume();

        } else if (TextFieldCompName.getText().trim().length() == 0) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, Please enter Component name!");

            alert.showAndWait();

            event.consume();

        } else if (NoDirSelected.equals(FileFolderPath.getText()) || FileFolderPath.getLength() == 0) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, Please select Directory/File!");

            alert.showAndWait();

            event.consume();

        } else {
            // System.out.println("First Else");
            int flag = 1;
            
            String[] AllConnDetails;
            PropertiesFileOperations propertFile = new PropertiesFileOperations();
    
            AllConnDetails = propertFile.readInastanceSetupInfo(ComboBoxInstance.getValue());
            // check for configuration details
            for (int i = 0; i <= 6; i++) {
                if (AllConnDetails[i] == "" || AllConnDetails[i].equals("null")) {
                    // System.out.println("checking config setups");
                    flag = 0;
                    break;
                }
            }
            // System.out.println("flag" + flag);
            if (flag == 1) {
                // System.out.println("flag2" + flag);

                if (rdbtnUpload.isSelected()) {

                    PutFileOnNetwork doFtpToserver = new PutFileOnNetwork();

                    doFtpToserver.putFile(AllConnDetails[1], // instance Host
                            AllConnDetails[2], // unix user
                            AllConnDetails[3], // unix pwd
                            dirPathFromUploadLDT, // "C:\\Users\\671757\\Documents\\TCS_GE_WIP\\Devlopment\\Putty",
                            AllConnDetails[5], FileNameToBeUploaded);

                    // Run the shell command to upload the LDT file
                    ShellExecuter shellCommand = new ShellExecuter();
                    shellCommand.uploadFndlaod(AllConnDetails[1], // instance Host
                            AllConnDetails[2], // unix user
                            AllConnDetails[3], // unix pwd
                            ComboBoxCompType.getValue(), FileNameToBeUploaded, AllConnDetails[7], AllConnDetails[4],
                            AllConnDetails[5]);

                }

                if (rdbtnDownload.isSelected()) {
                    String[] multiCompName = TextFieldCompName.getText().split((",|\\;"));

                    for (String str : multiCompName) {
                        if (str.length() == 0) {
                            continue;
                        }
                        System.out.println(str);
                        ShellExecuter shellCommand = new ShellExecuter();

                        shellCommand.downloadFndlaod(AllConnDetails, ComboBoxCompType.getValue(), str);

                        GetFileOverNetwork doFtpToLocalSystem = new GetFileOverNetwork();
                        doFtpToLocalSystem.getFile(AllConnDetails[1], // instance Host
                                AllConnDetails[2], // unix user
                                AllConnDetails[3], // unix pwd
                                AllConnDetails[5] + "/" + str/* TextFieldCompName.getText() */ + ".ldt",
                                dirPathToSaveLDT);

                    }

                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Task Completion Status!!");

                alert.setHeaderText("Task Has been completed for all Component ");
                alert.setContentText("Press OK to continue!!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Instance Configuration is Missing!!");
                alert.setHeaderText(null);
                alert.setContentText("Please complete the configuration Setup!!");
                alert.showAndWait();

            }
        }

    }

    @FXML
    private void CancleBtnAction(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/multitoolapp/ui/mainApp/MAinAppFXML.fxml"));
            Stage stage = new Stage();

            Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
            boolean add = stage.getIcons().add(image);

            stage.setTitle("Multi Tool Utility");
            //stage.setTitle("Ldt One Click Tool");
            // stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadWindow(String Loc, String Title) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(Loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(Title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String getSelectedInstanceName(){
        return SelectedinstanceName;
    }

    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] InstanceListKeyPair = null;
        try {
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            InstanceListKeyPair = propFileOper.ReadPropertiesFile("MASTERTINSTANCELIST");
        } catch (Exception ex) {
            Logger.getLogger(LdtMainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("hear" + InstanceListKeyPair[1]);// Arrays.toString(KeyValuePair));
        if (InstanceListKeyPair[1].trim().length() == 0 || "null".equals(InstanceListKeyPair[1].trim())) {

            loadWindow("/multitoolapp/ui/LdtMain/InstanceList/InstanceListFXML.fxml", "Master Instance List");
            return;

        }

        String[] ListInstance = InstanceListKeyPair[1].split("[/" + "," + "/" + ";" + "]");
        instanceList = FXCollections.observableArrayList(ListInstance);
        ComboBoxInstance.setValue(instanceDefaultValue);
        ComboBoxInstance.setItems(instanceList);

        ComboBoxCompType.setValue(ComponentTypeDefaultValue);
        ComboBoxCompType.setItems(CompoTypeList);
        FileFolderPath.setDisable(true);
        rdbtnDownload.setSelected(true);

    }

}
