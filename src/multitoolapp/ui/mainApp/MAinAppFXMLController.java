/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.ui.mainApp;

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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static multitoolapp.log.LogFile.start_logging;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class MAinAppFXMLController implements Initializable {

    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @FXML
    private Button btnMainLdt;
    @FXML
    private Button btnCodeCheckout;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start_logging();
        logr.info("Main App Window has been Initilised");

//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/multitoolapp/logWindowUI/LogWindowFXML.fxml"));
//            Stage stage = new Stage();
//            Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
//            boolean add = stage.getIcons().add(image);
//
//            stage.setTitle("Ldt One Click Tool");
//
//            stage.setScene(new Scene(parent));
//            stage.show();
//
//        } catch (IOException ex) {
//            Logger.getLogger(MAinAppFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void LoadLdtToolEvent(ActionEvent event
    ) {

        try {
            System.out.println("You have pressed the ldt loader button on main screen");
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/multitoolapp/ui/LdtMain/LdtMainWindow.fxml"));
            Stage stage = new Stage();
            Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
            boolean add = stage.getIcons().add(image);

            stage.setTitle("Ldt One Click Tool");
            //stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MAinAppFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void CodeCheckOutAction(ActionEvent event
    ) {
        try {
            System.out.println("You have pressed the code checkout button on main screen");
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/multitoolapp/ui/codeCheckMain/CodeCheckoutMainFXML.fxml"));
            Stage stage = new Stage();
            Image image = new Image("/multitoolapp/resources/icon/Azure-Automation-300x300.png");
            boolean add = stage.getIcons().add(image);

            stage.setTitle("Ldt One Click Tool");

            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MAinAppFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCloseAction(ActionEvent event) {
        System.exit(1);
    }

}
