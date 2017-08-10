package com.ftninformatika.bisis.process_types;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.ProcessTypeBuilder;
import com.ftninformatika.bisis.librarian.process_type_dto.ProcessTypeDTO;
import com.ftninformatika.bisis.librarian.process_type_dto.USubfieldDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Created by Petar on 8/10/2017.
 */
public class PorcessTypeInitializeFromDTOTest {

    private ProcessType pt;

    @Before
    public void setUp(){
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
    public void initializeFromDTO(){
        ProcessTypeDTO ptDTO = new ProcessTypeDTO();
        ptDTO.setLibName("gbns");
        ptDTO.setName("tip obrade test");
        ptDTO.setPubType(1);
        List<USubfieldDTO> sfs = new ArrayList<>();
        sfs.add(new USubfieldDTO("001",'7'));
        sfs.add(new USubfieldDTO("001",'a'));
        sfs.add(new USubfieldDTO("001",'b'));
        sfs.add(new USubfieldDTO("001",'c'));
        sfs.add(new USubfieldDTO("001",'d'));
        //sfs.add(new USubfieldDTO("001",'e'));
        ptDTO.setInitialFields(sfs);

        ProcessType ptt = ProcessTypeBuilder.buildProcessTypeFromDTO(ptDTO);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(ptt,pt));


    }
}
