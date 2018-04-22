package com.ftninformatika.bisis.admin.unlock;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.DialogUtils;
import com.ftninformatika.utils.Messages;
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
                   DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_INVALID_FORMAT_USER_ID"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
                   return;
               }
        MemberData m = null;

        try {
            m = BisisApp.bisisService.getMemberDataById(userId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (m == null){
            DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_USER_NOT_FOUND"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
            return;
        }
        else {
            if(m.getMember().getInUseBy() == null){
                DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_USER_ALREDY_UNLOCKED"), Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
            }
            else {
                boolean otkljucao = false;
                try {
                    otkljucao = BisisApp.bisisService.releaseMemberById(userId).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                                if (otkljucao) {
                    DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_SUCCESS"),  Messages.getString("UNLOCK_CONTROLLER_USER_SUCCESFULLY_UNLOCKED"), m.getMember().getUserId() + "\n" + m.getMember().getFirstName() + " " + m.getMember().getLastName());
                }
                else {
                    DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_ERROR"), Messages.getString("UNLOCK_CONTROLLER_ERROR_UNLOCKING"),"");
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
            DialogUtils.showInfoDialog( Messages.getString("UNLOCK_CONTROLLER_WARNING"),  Messages.getString("UNLOCK_CONTROLLER_RECORD_NOT_FOUND"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_RN"));
            return;
        }
        else {
            if(r.getInUseBy() == null)
                DialogUtils.showInfoDialog( Messages.getString("UNLOCK_CONTROLLER_WARNING"),  Messages.getString("UNLOCK_CONTROLLER_RECORD_ALREDY_UNLOCKED"), Messages.getString("UNLOCK_CONTROLLER_CHECK_RN"));
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
                    DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_SUCCESS"), Messages.getString("UNLOCK_CONTROLLER_RECORD_SUCESSFULLY_UNLOCKED"), pr.getTitle());
                }
                else {
                    DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_ERROR"), Messages.getString("UNLOCK_CONTROLLER_RECORD_ERROR"),"");
                }
            }
        }
    }
}
