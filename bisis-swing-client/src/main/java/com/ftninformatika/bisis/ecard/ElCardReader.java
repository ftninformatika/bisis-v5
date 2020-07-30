package com.ftninformatika.bisis.ecard;


import lombok.Getter;
import lombok.Setter;
import net.devbase.jfreesteel.EidCard;
import net.devbase.jfreesteel.EidInfo;

import javax.smartcardio.*;

@Getter
@Setter
public class ElCardReader {

    private static ElCardReader instance;
    private CardTerminal cardTerminal;

    private ElCardReader() {}

    public static ElCardReader getInstance() {
        if (instance == null) {
            instance = new ElCardReader();
            TerminalFactory factory = TerminalFactory.getDefault();
            instance.cardTerminal = pickTerminal(factory.terminals());
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

    public ElCardInfo getInfo() {
        ElCardInfo retVal = new ElCardInfo();
        if (instance.cardTerminal == null) {
            retVal.setMessage("Citac nije pronadjen"); //TODO move strings to resources
            return retVal;
        }
        try {
            Card card = instance.cardTerminal.connect("*");
            EidCard eidCard = EidCard.fromCard(card);
            EidInfo info = eidCard.readEidInfo();
            retVal.setInfo(info);
            if (info == null) {
                retVal.setMessage("Nije bilo moguce procitati podatke sa licne karte");
                return retVal;
            }

        } catch (CardException exception) {
            exception.printStackTrace();
            retVal.setMessage("Molimo vas ubacite licnu kartu");
            return retVal;
        }

        // TODO set locale from config cyrl/latn
        retVal.setSuccess(true);
        return retVal;
    }
}
