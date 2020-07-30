package com.ftninformatika.bisis.ecard;

import org.junit.Before;
import org.junit.Test;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ECardTests {

    CardTerminal cardTerminal = null;

    @Before
    public void setUp() throws Exception {
        TerminalFactory terminalFactory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = terminalFactory.terminals().list();
        if (terminals.size() == 0)
            throw new Exception("No e card readers discovered");
        if (terminals.size() > 1)
            System.out.println("Multiple readers are attached to device, picking 1st");
        cardTerminal = terminals.get(0);
        System.out.println("Selected terminal: " + cardTerminal.getName());
    }

    @Test
    public void readerConnectedTest() {
        System.out.println("Checking if terimnal is initialised");
        assertNotEquals(null, cardTerminal);
    }

}