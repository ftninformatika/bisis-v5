package com.ftninformatika.bisis.admin.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.format.UItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class JfxCoderFrameController implements Initializable{

    @FXML Label coderName = new Label();
    @FXML TableView<UItem> coderTable;
    @FXML TableColumn<UItem, String> codeColumn;
    @FXML TableColumn<UItem, String> descriptionColumn;
    @FXML TextField selectedCoderId;
    @FXML TextArea selectedCoderDescription;
    private Map<String, Coder> coderMap;
    private int coder;


    public void initCoder(int coder) {
        coderName.setText(BisisApp.appConfig.getCodersHelper().getLocaleCoderName(coder));
        List<UItem> coderUitemList = BisisApp.appConfig.getCodersHelper().getCoderUItemList(coder);
        coderMap = BisisApp.appConfig.getCodersHelper().getCoderMap(coder);
        coderUitemList.sort(Comparator.comparing(c -> c.getCode()));
        coderTable.getItems().setAll(coderUitemList);
        this.coder = coder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeColumn.setCellValueFactory(new PropertyValueFactory<UItem, String>("code"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<UItem, String>("value"));
        codeColumn.prefWidthProperty().bind(coderTable.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(coderTable.widthProperty().multiply(0.75));
        codeColumn.setResizable(false);
        descriptionColumn.setResizable(false);
        selectedCoderId.setText(null);
        selectedCoderDescription.setWrapText(true);
        selectedCoderDescription.setText("");
        //wrap description text
        descriptionColumn.setCellFactory(tc -> {
            TableCell<UItem, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(descriptionColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        coderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedCoder) -> {
            if (selectedCoder != null) {
                selectedCoderId.setText(selectedCoder.getCode());
                selectedCoderDescription.setText(selectedCoder.getValue());
            }
        });
    }

    public void deleteCoder() {
        if (coderTable.getSelectionModel().getSelectedItem() != null) {
            UItem toBeDeleted = coderTable.getSelectionModel().getSelectedItem();
            System.out.println("Brisemo - " + toBeDeleted.toString());

        }
        else {
            JOptionPane.showMessageDialog(null, "Прво одаберите шифарник!",
                    "Грешка", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void addUpdateCoder() {

    }
}
