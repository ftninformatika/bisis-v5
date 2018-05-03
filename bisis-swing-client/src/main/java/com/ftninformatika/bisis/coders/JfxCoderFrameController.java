package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.UItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class JfxCoderFrameController implements Initializable{

    @FXML Label coderName = new Label();
    @FXML TableView<UItem> coderTable;
    @FXML TableColumn<UItem, String> codeColumn;
    @FXML TableColumn<UItem, String> descriptionColumn;

    public void initCoder(int coder) {
        coderName.setText(BisisApp.appConfig.getCodersHelper().getLocaleCoderName(coder));
        List<UItem> coderUitemList = BisisApp.appConfig.getCodersHelper().getCoder(coder);
        coderUitemList.sort(Comparator.comparing(c -> c.getCode()));
        coderTable.getItems().setAll(coderUitemList);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeColumn.setCellValueFactory(new PropertyValueFactory<UItem, String>("code"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<UItem, String>("value"));
        codeColumn.prefWidthProperty().bind(coderTable.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(coderTable.widthProperty().multiply(0.75));
        codeColumn.setResizable(false);
        descriptionColumn.setResizable(false);
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
    }
}
