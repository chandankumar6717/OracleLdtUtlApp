/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.ui.codeCheckMain;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import multitoolapp.common.JdbcDoaImpl;

import multitoolapp.common.ExcelFileUTL;

/**
 * FXML Controller class
 *
 * @author Chandan
 */
public class CodeCheckoutMainFXMLController implements Initializable {

    @FXML
    private TextField TextFieldExcelPathName;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnOk;
    @FXML
    private TextField CompColmName;

    //
    public static final String compStatus = "OBJECT_STATUS";
    public static final String compSql = "OBJECT_SQL";
    @FXML
    private MenuBar menuBarLdtMain;
    @FXML
    private MenuItem MenuBarInstanceSetup;
    @FXML
    private TextField excelSheetName;
    @FXML
    private ComboBox<?> CodeCheckComboBoxInstance;
    @FXML
    private Button CodeCheckInstConfig;

    @FXML
    private void btnOkEvent(ActionEvent event) {
        runExcelOperation();

    }

    @FXML
    private void btnBrowseEvent(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file == null) {
            TextFieldExcelPathName.setText("NoDirSelected");
        } else {
            TextFieldExcelPathName.setText(file.getAbsolutePath());
            
        }

    }

    public void runExcelOperation() {
        new Thread() {
            @Override
            public void run() {
                try {
                    JdbcDoaImpl dbobj = new JdbcDoaImpl();
                    ExcelFileUTL eat = new ExcelFileUTL(TextFieldExcelPathName.getText());
                    eat.SetHeaderCell("Credentials", compStatus);
                    eat.SetHeaderCell("Credentials", compSql);
                    String[][] SheetData = eat.readCellData("Credentials", "ComponentName", "ComponentType");
                    for (int row = 1; row < SheetData.length; row++) {
                        for (int col = 0; col < SheetData[row].length; col++) {

                            System.out.print(SheetData[row][col] + "\t");
                            eat.setCellData("Credentials", compStatus, row + 1, dbobj.FindDbObject("InstanceName",SheetData[row][0], SheetData[row][1]));
                            eat.setCellData("Credentials", compSql, row + 1, "select OBJECT_NAME,OBJECT_TYPE,STATUS from SYS.DBA_OBJECTS where object_name =" + SheetData[row][0] + " AND Object_type = " + SheetData[row][1]);

                        }
                        System.out.println();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CodeCheckoutMainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void MenuInstanceSetupAction(ActionEvent event) {
    }

}
