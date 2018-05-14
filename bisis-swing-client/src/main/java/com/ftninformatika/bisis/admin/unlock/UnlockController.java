package com.ftninformatika.bisis.admin.unlock;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CircConfig;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.Environment;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.DialogUtils;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.string.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class UnlockController {


    @FXML TextField unlockId;

    //Try to unlock member
    public void clkMember() {
        if (unlockId.getText().equals("") || unlockId.getText() == null)
            return;

        String userId = "";
               try {
                   userId = convertUserId2DBForUnlock(unlockId.getText());
               }
               catch (NullPointerException e){
                   JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_INVALID_FORMAT_USER_ID"),
                           Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);

                   //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_INVALID_FORMAT_USER_ID"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
                   return;
               }
        MemberData m = null;

        try {
            m = BisisApp.bisisService.getMemberDataById(userId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (m == null){
            JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_NOT_FOUND"),
                    Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
            //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_USER_NOT_FOUND"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
            return;
        }
        else {
            if(m.getMember().getInUseBy() == null){
                JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_ALREDY_UNLOCKED"),
                        Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);

                //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_USER_ALREDY_UNLOCKED"), Messages.getString("UNLOCK_CONTROLLER_CHECK_ID"));
            }
            else {
                boolean otkljucao = false;
                try {
                    otkljucao = BisisApp.bisisService.releaseMemberById(userId).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (otkljucao) {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_SUCCESFULLY_UNLOCKED"),
                            Messages.getString("UNLOCK_CONTROLLER_SUCCESS"), JOptionPane.INFORMATION_MESSAGE);

                    //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_SUCCESS"),  Messages.getString("UNLOCK_CONTROLLER_USER_SUCCESFULLY_UNLOCKED"), m.getMember().getUserId() + "\n" + m.getMember().getFirstName() + " " + m.getMember().getLastName());
                }
                else {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_ERROR_UNLOCKING"),
                            Messages.getString("UNLOCK_CONTROLLER_ERROR"), JOptionPane.ERROR_MESSAGE);

                    //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_ERROR"), Messages.getString("UNLOCK_CONTROLLER_ERROR_UNLOCKING"),"");
                }
            }
        }
    }

    //Try to unlock record
    public void clkRecord() {
        if (unlockId.getText().equals("") || unlockId.getText() == null)
            return;

        Record r = null;
        try {
            r = BisisApp.bisisService.getRecordByRN(unlockId.getText()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (r == null){
            JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_NOT_FOUND"),
                    Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);


            //DialogUtils.showInfoDialog( Messages.getString("UNLOCK_CONTROLLER_WARNING"),  Messages.getString("UNLOCK_CONTROLLER_RECORD_NOT_FOUND"),  Messages.getString("UNLOCK_CONTROLLER_CHECK_RN"));
            return;
        }
        else {
            if(r.getInUseBy() == null) {
                JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_ALREDY_UNLOCKED"),
                        Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);


                //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_WARNING"), Messages.getString("UNLOCK_CONTROLLER_RECORD_ALREDY_UNLOCKED"), Messages.getString("UNLOCK_CONTROLLER_CHECK_RN"));
            }
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
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_SUCESSFULLY_UNLOCKED"),
                            Messages.getString("UNLOCK_CONTROLLER_SUCCESS"), JOptionPane.INFORMATION_MESSAGE);


                    //DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_SUCCESS"), Messages.getString("UNLOCK_CONTROLLER_RECORD_SUCESSFULLY_UNLOCKED"), pr.getTitle());
                }
                else {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_ERROR"),
                            Messages.getString("UNLOCK_CONTROLLER_ERROR"), JOptionPane.ERROR_MESSAGE);


                   // DialogUtils.showInfoDialog(Messages.getString("UNLOCK_CONTROLLER_ERROR"), Messages.getString("UNLOCK_CONTROLLER_RECORD_ERROR"),"");
                }
            }
        }
    }

    private static String convertUserId2DBForUnlock(String userid) {
        try {
            CircConfig config = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
            String envString = config.getCircOptionsXML();
            Environment env = new Environment(envString);
            env.loadOptions();
            //barcode
            if (userid.startsWith("K")) {
                return userid.substring(1);
            }
            if ((userid.length() == env.getUseridLength()) && (userid.indexOf(env.getUseridSeparatorSign()) == -1)) {
                return userid;
            }

            if (!env.getUseridPrefix() || !env.getUseridSeparator()) {
                if (userid.length() <= env.getUseridLength()) {
                    return StringUtils.padChars(userid, '0', env.getUseridLength());
                }
            } else {
                int ind = userid.indexOf(env.getUseridSeparatorSign());
                if (ind != -1) {
                    String part1 = userid.substring(0, ind).trim();
                    String part2 = userid.substring(ind + 1).trim();
                    if (env.getUseridInput() == 1) {
                        if (part2.length() <= env.getUseridPrefixLength() &&
                                part1.length() <= env.getUseridLength() - env.getUseridPrefixLength()) {
                            return StringUtils.padChars(part2, '0', env.getUseridPrefixLength()) +
                                    StringUtils.padChars(part1, '0', env.getUseridLength() - env.getUseridPrefixLength());
                        }
                    } else {
                        if (part1.length() <= env.getUseridPrefixLength() &&
                                part2.length() <= env.getUseridLength() - env.getUseridPrefixLength()) {
                            return StringUtils.padChars(part1, '0', env.getUseridPrefixLength()) +
                                    StringUtils.padChars(part2, '0', env.getUseridLength() - env.getUseridPrefixLength());
                        }
                    }
                } else {
                    if (userid.length() <= env.getUseridLength() - env.getUseridPrefixLength()) {
                        return StringUtils.padChars(Integer.toString(env.getUseridDefaultPrefix()), '0', env.getUseridPrefixLength()) +
                                StringUtils.padChars(userid, '0', env.getUseridLength() - env.getUseridPrefixLength());
                    }
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
