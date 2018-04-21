package com.ftninformatika.bisis.admin.unlock;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UnlockController {


    @FXML TextField unlockId;

    //Try to unlock member
    public void clkMember() {
        String userId = "";
               try {
                   userId = Validator.convertUserId2DB(unlockId.getText());
               }
               catch (NullPointerException e){
                   DialogUtils.showInfoDialog("Упозорење", "Неисправан формат корисника", "Проверите да ли сте исправно унели ID!");
                   return;
               }
        MemberData m = null;

        try {
            m = BisisApp.bisisService.getMemberDataById(userId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (m == null){
            DialogUtils.showInfoDialog("Упозорење", "Корисник није пронађен", "Проверите да ли сте исправно унели ID!");
            return;
        }
        else {
            if(m.getMember().getInUseBy() == null){
                DialogUtils.showInfoDialog("Упозорење", "Корисник је већ откључан", "Проверите да ли сте исправно унели ID!");
            }
            else {
                boolean otkljucao = false;
                try {
                    otkljucao = BisisApp.bisisService.releaseMemberById(userId).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                                if (otkljucao) {
                    DialogUtils.showInfoDialog("Успешно", "Успешно сте откључали korisnika:", m.getMember().getUserId() + "\n" + m.getMember().getFirstName() + " " + m.getMember().getLastName());
                }
                else {
                    DialogUtils.showInfoDialog("Грешка", "Грешка при откључавању корисника","");
                }
            }
        }
    }

    //Try to unlock record
    public void clkRecord() {
        Record r = null;
        try {
            r = BisisApp.bisisService.getRecordByRN(unlockId.getText()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (r == null){
            DialogUtils.showInfoDialog("Упозорење", "Запис није пронађен", "Проверите да ли сте исправно унели RN!");
            return;
        }
        else {
            if(r.getInUseBy() == null)
                DialogUtils.showInfoDialog("Упозорење", "Запис је већ откључан", "Проверите да ли сте исправно унели RN!");
            else {
                boolean otkljucao = false;
                try {
                    otkljucao = BisisApp.bisisService.unlockByRN(unlockId.getText()).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RecordPreview pr = new RecordPreview();
                pr.init(r);
                if (otkljucao) {
                    DialogUtils.showInfoDialog("Успешно", "Успешно сте откључали запис:", pr.getTitle());
                }
                else {
                    DialogUtils.showInfoDialog("Грешка", "Грешка при откључавању записа","");
                }
            }
        }
    }
}
