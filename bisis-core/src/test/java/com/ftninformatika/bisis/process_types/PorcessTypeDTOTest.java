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
public class PorcessTypeDTOTest {

    private ProcessType expectedPt;
    private ProcessTypeDTO expectedPtDTO;

    @Before
    public void setUp(){
        //ProcessType for comparation
        expectedPt = new ProcessType();
        expectedPt.setPubType(PubTypes.getPubType(1));
        expectedPt.setLibName("gbns");
        expectedPt.setName("tip obrade test");
        expectedPt.setIndicators(new ArrayList<>());

        List<USubfield> sfs = new ArrayList<>();
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(0));
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(1));
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(2));
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(3));
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(4));
        sfs.add(expectedPt.getPubType().getField("001").getSubfields().get(5));
        expectedPt.setInitialSubfields(sfs);

        //ProcessTypeDTO for comparation
        expectedPtDTO = new ProcessTypeDTO();
        expectedPtDTO.setLibName("gbns");
        expectedPtDTO.setName("tip obrade test");
        expectedPtDTO.setPubType(1);
        List<USubfieldDTO> sfs2 = new ArrayList<>();
        sfs2.add(new USubfieldDTO("001",'7'));
        sfs2.add(new USubfieldDTO("001",'a'));
        sfs2.add(new USubfieldDTO("001",'d'));
        sfs2.add(new USubfieldDTO("001",'e'));
        expectedPtDTO.setInitialFields(sfs2);

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
        sfs.add(new USubfieldDTO("001",'e'));
        ptDTO.setInitialFields(sfs);

        ProcessType resultingPt = ProcessTypeBuilder.buildProcessTypeFromDTO(ptDTO);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(resultingPt, expectedPt));

    }

    @Test
    public void initializeDTOFromProcessType(){
        ProcessType pt = new ProcessType();
        pt.setPubType(PubTypes.getPubType(1));
        pt.setLibName("gbns");
        pt.setName("tip obrade test");
        pt.setIndicators(new ArrayList<>());

        List<USubfield> sfs = new ArrayList<>();
        sfs.add(pt.getPubType().getField("001").getSubfields().get(0));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(1));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(4));
        sfs.add(pt.getPubType().getField("001").getSubfields().get(5));
        pt.setInitialSubfields(sfs);

        ProcessTypeDTO resultinPtDTO = ProcessTypeBuilder.buildDTOFromProcessType(pt);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(resultinPtDTO, expectedPtDTO));
    }
}
