package com.ftninformatika.bisis.ecard;

import com.ftninformatika.utils.OsCheck;
import net.devbase.jfreesteel.EidCard;
import net.devbase.jfreesteel.EidInfo;

import javax.smartcardio.*;

public class ElCardReader {

    private static final String ARCH_MODEL_PARAM = "sun.arch.data.model";
    private static final String LIBPCS_PARAM = "sun.security.smartcardio.library";
    private static final String LINUX_64_LIBPCSLITE_PATH = "/lib/x86_64-linux-gnu/libpcsclite.so.1";
    private static final String LINUX_32_LIBPCSLITE_PATH = "/lib/i386-linux-gnu/libpcsclite.so.1";

    static {
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        if (osType == OsCheck.OSType.Linux) {
            String archModel = System.getProperty(ARCH_MODEL_PARAM);
            if (archModel != null) {
                if (archModel.equals("32"))
                    System.setProperty(LIBPCS_PARAM, LINUX_32_LIBPCSLITE_PATH);
                else if (archModel.equals("64"))
                    System.setProperty(LIBPCS_PARAM, LINUX_64_LIBPCSLITE_PATH);
            }
        }
    }

    private static ElCardReader instance;
    private CardTerminal cardTerminal;

    private ElCardReader() {}

    public static ElCardReader getInstance() {
        if (instance == null || instance.cardTerminal == null) {
            instance = new ElCardReader();
            TerminalFactory factory = TerminalFactory.getDefault();
            instance.cardTerminal = pickTerminal(factory.terminals());
        }
        else {
            try {
                if (TerminalFactory.getDefault().terminals().list().isEmpty())
                    instance.cardTerminal = null;
            } catch (CardException e) {
                instance.cardTerminal = null;
            }
        }
        return instance;
    }

    private static CardTerminal pickTerminal(CardTerminals terminals) {
        try {
            if (terminals == null || terminals.list() == null || terminals.list().size() == 0) {
                return null;
            }
            if (terminals.list().size() >= 1) {
                return terminals.list().get(0);
            }
            return null;
        } catch (CardException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public ElCardInfo getInfo(boolean latnLocale) {
        ElCardInfo retVal = new ElCardInfo();
        retVal.setLatnLocale(latnLocale);
        if (instance.cardTerminal == null) {
            retVal.setMessageKey("ecard.readernotfound");
            return retVal;
        }
        try {
            Card card = instance.cardTerminal.connect("*");
            EidCard eidCard = EidCard.fromCard(card);
            EidInfo info = eidCard.readEidInfo();
            retVal.setInfo(info);
            if (info == null) {
                retVal.setMessageKey("ecard.datanotred");
                return retVal;
            }

        } catch (CardException exception) {
            exception.printStackTrace();
            retVal.setMessageKey("ecard.cardnotfound");
            return retVal;
        } catch (IllegalArgumentException ex2) {
            ex2.printStackTrace();
            retVal.setMessageKey("ecard.wrongcard");
            return retVal;
        }

        retVal.setSuccess(true);
        return retVal;
    }
}
