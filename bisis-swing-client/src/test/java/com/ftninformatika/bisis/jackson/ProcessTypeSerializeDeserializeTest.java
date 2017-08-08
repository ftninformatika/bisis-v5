package com.ftninformatika.bisis.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okio.Utf8;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.nio.cs.UTF_32;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Petar on 8/8/2017.
 */
public class ProcessTypeSerializeDeserializeTest {

    private ObjectMapper om;
    private ProcessType pt;


    @Before
    public void setUp() throws Exception {
        om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        pt = new ProcessType();
        pt.setPubType(PubTypes.getPubType(1));
        pt.setLibName("gbns");
        pt.setName("tip obrade test");
        pt.setIndicators(new ArrayList<>());

        List<USubfield> sfs = new ArrayList<>();
        sfs.add(pt.getPubType().getField("001").getSubfields().get(0));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(1));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(2));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(3));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(4));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(5));
        pt.setInitialSubfields(sfs);
    }

    @Test
    public void serializeTest(){
        try  {

            om.writeValue(new File("src/test/resources/jackson/Output.json"), pt);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("JsonProcessingException e");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException e");
        }

    }

    @Test
    public void deserializeTest(){
        ProcessType nPt = null;
        try{
            nPt = om.readValue(new File("src/test/resources/jackson/Output.json"), ProcessType.class);
            boolean flag = true;
            for (USubfield sf :nPt.getInitialSubfields())
                if(sf.getOwner() == null) {
                    flag = false;
                    break;
            }
            assertTrue("Process type not properely deserialized", flag);
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Test file not found.");
        }

    }



}
