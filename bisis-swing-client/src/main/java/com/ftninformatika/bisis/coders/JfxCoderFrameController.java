package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.UItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class JfxCoderFrameController {

    @FXML Label coderName = new Label();
    @FXML TableView codersTable = new TableView();

    public void initCoder(int coder) {
        coderName.setText(coder + "");
        List<UItem> coderUitemList = BisisApp.appConfig.getCodersHelper().getCoder(coder);
        TableColumn codeColumn = new TableColumn("Шифарник");
        TableColumn descColumn = new TableColumn("Опис");
        for (UItem u: coderUitemList){
            codersTable.getColumns().addAll(u.getCode(), u.getValue());
        }
    }

}
