/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.logWindowUI;

import java.net.URL;
import java.io.PrintStream;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class LogWindowFXMLController implements Initializable {

    @FXML
    private TextArea textAreaLog;

    private PrintStream standardOut;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // textAreaLog = new TextArea(50, 10);
        textAreaLog.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textAreaLog));

        // keeps reference of standard output stream
        standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

        //printLog();
    }

}
