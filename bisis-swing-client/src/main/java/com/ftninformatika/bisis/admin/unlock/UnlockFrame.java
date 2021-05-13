package com.ftninformatika.bisis.admin.unlock;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CircConfig;
import com.ftninformatika.bisis.circ.Environment;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.string.StringUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author marijakovacevic
 */
public class UnlockFrame extends JInternalFrame {
    private JButton unlockRecord = null;
    private JButton unlockMember = null;
    private JTextField unlockId = null;
    private JLabel imageLbl = null;

    public UnlockFrame() {
        super(Messages.getString("MAINFRAME_UNLOCK_TITLE"), false, true, false, true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(620, 220);

        this.initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        MigLayout layout = new MigLayout("", "[center] [center] [center]", "[] [] [] []");
        panel.setLayout(layout);

        JLabel lockUnlockLbl = new JLabel(Messages.getString("UNLOCK_FRAME_LABEL"));
        JLabel rnLbl = new JLabel(Messages.getString("RN_LABEL"));
        JLabel memberNumberLbl = new JLabel(Messages.getString("circulation.usernumber"));

        unlockId = new JTextField(10);
        unlockRecord = getUnlockRecordBtn();
        unlockMember = getUnlockMemberBtn();
        imageLbl = getImageLbl();

        panel.add(lockUnlockLbl, "cell 1 0");
        panel.add(unlockId, "cell 1 1");
        panel.add(rnLbl, "cell 0 2");
        panel.add(memberNumberLbl, "cell 2 2");
        panel.add(unlockRecord, "cell 0 3");
        panel.add(imageLbl, "cell 1 3");
        panel.add(unlockMember, "cell 2 3");

        this.add(panel);
    }

    private JLabel getImageLbl() {
        if (imageLbl == null) {
            imageLbl = new JLabel();
            imageLbl.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/icons/unlock/unlock.png"))
                    .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        }
        return imageLbl;
    }

    private JButton getUnlockRecordBtn() {
        if (unlockRecord == null) {
            unlockRecord = new JButton();
            unlockRecord.setText(Messages.getString("UNLOCK_RECORD"));
            unlockRecord.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/icons/unlock/record.png"))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            unlockRecord.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clkRecord();
                }
            });
        }
        return unlockRecord;
    }

    private JButton getUnlockMemberBtn() {
        if (unlockMember == null) {
            unlockMember = new JButton();
            unlockMember.setText(Messages.getString("UNLOCK_MEMBER"));
            unlockMember.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/icons/unlock/member.png"))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            unlockMember.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clkMember();
                }
            });
        }
        return unlockMember;
    }

    //Try to unlock member
    public void clkMember() {
        if (unlockId.getText().equals("") || unlockId.getText() == null)
            return;

        String userId = "";
        try {
            userId = convertUserId2DBForUnlock(unlockId.getText());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_INVALID_FORMAT_USER_ID"),
                    Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        MemberData m = null;

        try {
            m = BisisApp.bisisService.getMemberDataById(userId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (m == null) {
            JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_NOT_FOUND"),
                    Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
        } else {
            if (m.getMember().getInUseBy() == null) {
                JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_ALREDY_UNLOCKED"),
                        Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
            } else {
                boolean otkljucao = false;
                try {
                    otkljucao = BisisApp.bisisService.releaseMemberById(userId).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (otkljucao) {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_USER_SUCCESFULLY_UNLOCKED"),
                            Messages.getString("UNLOCK_CONTROLLER_SUCCESS"), JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_ERROR_UNLOCKING"),
                            Messages.getString("UNLOCK_CONTROLLER_ERROR"), JOptionPane.ERROR_MESSAGE);
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
        if (r == null) {
            JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_NOT_FOUND"),
                    Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
        } else {
            if (r.getInUseBy() == null) {
                JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_ALREDY_UNLOCKED"),
                        Messages.getString("UNLOCK_CONTROLLER_WARNING"), JOptionPane.WARNING_MESSAGE);
            } else {
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
                } else {
                    JOptionPane.showMessageDialog(null, Messages.getString("UNLOCK_CONTROLLER_RECORD_ERROR"),
                            Messages.getString("UNLOCK_CONTROLLER_ERROR"), JOptionPane.ERROR_MESSAGE);
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
        public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the Swing JPanel and make visible
        frame.getContentPane().add(new UnlockFrame());
        frame.setVisible(true);
    }
}
