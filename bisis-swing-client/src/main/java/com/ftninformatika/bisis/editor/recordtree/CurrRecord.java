package com.ftninformatika.bisis.editor.recordtree;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.editor.inventar.InventarValidation;
import com.ftninformatika.bisis.format.*;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.PrimerakSerializer;
import com.ftninformatika.utils.Messages;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class CurrRecord {

    public static Record record = new Record();
    public static Field selectedField = null;

    // false ako se pravi novi zapis/true ako se modifikuje postojeci
    public static boolean update = false;
    public static boolean savedOnce = false;
    private static boolean valid = false;
    private static Logger log = Logger.getLogger(CurrRecord.class);

    public static void recordInitialize(Record rec) {
        int pubType = 0;
        if (rec != null) {
            if (update) {
                record = rec;
                pubType = record.getPubType();
            } else {
                //pravi se novi zapis na osnovu postojeceg
                pubType = CurrFormat.getPubType();
                if (rec.getField("000") != null) {
                    PrimerakSerializer.polje000UMetapodatke(rec);
                }
                record = rec.copyWithoutHoldings();
                if (record.getSubfield("992b") != null) {
                    System.out.println("992b");
                    record.getSubfield("992b").setContent("");
                }
            }
            RecordUtils.addElementsFromProcessType();
        } else {
            pubType = CurrFormat.getPubType();
            record = new Record();
            record.setPubType(pubType);
            RecordUtils.addElementsFromProcessType();
            RecordUtils.sortFields();
        }
        RecordUtils.sortFields();
        if (pubType == 0)
            log.warn("Publication type undefined, recordId=" + record.getRecordID());
    }

    public static boolean saveCurrentRecord() throws UValidatorException {
        boolean ok = false;
        valid = false;
        String message = validateRecord();
        // String holdingsMessage = validateHoldingsData();
        if (!valid) {
            throw new UValidatorException(message);
        } else {
            if (!update) {
                if (!savedOnce && record.getRecordID() == 0) {
                    int id = BisisApp.recMgr.getNewID("recordid");
                    int rn = BisisApp.recMgr.getNewID("RN");
                    record.setRecordID(id);
                    record.setRN(rn);
                    record.setPubType(CurrFormat.getPubType());
                    record.setCreator(new Author(BisisApp.appConfig.getLibrarian().getUsername(), BisisApp.appConfig.getClientConfig().getLibraryName()));
                    record.setModifier(new Author(BisisApp.appConfig.getLibrarian().getUsername(), BisisApp.appConfig.getClientConfig().getLibraryName()));
                    Record r = null;
                    try {
                        r = BisisApp.bisisService.createRecord(record).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (r != null)
                        record = r;
                        //record.set_id(r.get_id());
                    ok = r != null;
                    savedOnce = true;
                    log.info("add record, recordId=" + r.get_id() + ", creator: " + record.getCreator().getUsername());
                } else {
                    Record r = null;
                    try {
                        r = BisisApp.bisisService.updateRecord(record).execute().body();
                        if (r != null) {
                            record = r;
                        }
                        //record.set_id(r.get_id());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ok = r != null;
                }
            } else {
                record.setModifier(new Author(BisisApp.appConfig.getLibrarian().getUsername(), BisisApp.appConfig.getClientConfig().getLibraryName()));
                Record r = null;
                try {
                    r = BisisApp.bisisService.updateRecord(record).execute().body(); //ovo je update
                    if (r != null) {
                        record = r;
                    }
                    //record.set_id(r.get_id());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ok = r != null;
            }
        }
        return ok;
    }

    /*
     * kreira string sa podacima o zapisu koji se snima
     */
    public static String saveRecordReport() {
        StringBuffer buff = new StringBuffer();
        if (record.getRecordID() != 0) {
            buff.append("ID: " + record.getRecordID() + "\n");
            buff.append("RN: " + record.getRN() + "\n");
        }
        buff.append("TI: " + record.getSubfieldContent("200a"));
        return buff.toString();
    }

    public static void unlockRecord() {
        try {
            BisisApp.recMgr.unlock(record.get_id());
        } catch (NullPointerException e) {
            log.fatal(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] addField(UField uf, boolean withSubfields) throws UValidatorException {
        Obrada.editorFrame.recordUpdated();
        Field f = null;
        String fName = uf.getName();
        List l = new ArrayList(); // za treePath
        if (selectedField == null ||
                (selectedField != null && !CurrFormat.block4.contains(selectedField.getName()))) {
            f = record.getField(fName);
            if (f == null) {
                if (!withSubfields)
                    f = RecordUtils.getRecordField(uf);
                else
                    f = RecordUtils.getRecordFieldWithoutSubfield(uf);
                record.add(f);
            } else {
                if (uf.isRepeatable()) {
                    if (!withSubfields)
                        f = RecordUtils.getRecordField(uf);
                    else
                        f = RecordUtils.getRecordFieldWithoutSubfield(uf);
                    record.add(f);
                } else {
                    throw new UValidatorException(MessageFormat.format(Messages.getString("FIELD.0.1.NOT_REPEATABLE"), fName, uf.getDescription()));
                }
            }
            l.add(record);
            l.add(f);
        } else if (selectedField != null && CurrFormat.block4.contains(selectedField.getName())) {
            if (uf.isSecondary()) {
                if (RecordUtils.secondaryFieldAlreadyExist(uf) && !uf.isRepeatable())
                    throw new UValidatorException(MessageFormat.format(Messages.getString("FIELD.0.1.NOT_REPEATABLE"), fName, uf.getDescription()));
                Subfield sf = new Subfield('1');
                if (!withSubfields)
                    f = RecordUtils.getRecordField(uf);
                else
                    f = RecordUtils.getRecordFieldWithoutSubfield(uf);
                sf.setSecField(f);
                selectedField.add(sf);
                l.add(record);
                l.add(selectedField);
                l.add(sf);
                l.add(f);
            } else
                throw new UValidatorException(MessageFormat.format(Messages.getString("FIELD.0.1.NOT_SECONDARY"), fName, uf.getDescription()));
        }


        //     dodavanje indikatora
    /*  UIndicator ind1 = uf.getInd1();
      UIndicator ind2 = uf.getInd2();
      if(ind1!=null) 
        if(ind1.getDefaultValue()!=null) f.setInd1(ind1.getDefaultValue().charAt(0));
        else f.setInd1(' ');
      if(ind2!=null) 
        if(ind2.getDefaultValue()!=null) f.setInd2(ind2.getDefaultValue().charAt(0));
        else f.setInd2(' ');*/
        RecordUtils.sortFields();
        return l.toArray();
    }

    /*
     * dodaje polje u zapis
     * polje se dodaje na mesto index
     * vrzi se provera ponovljivosti
     */
    public static void addField(Field field, int index) throws UValidatorException {
        UField uf = CurrFormat.getFullFormat().getField(field.getName());
        if (record.getField(field.getName()) != null && !uf.isRepeatable())
            throw new UValidatorException(MessageFormat.format(Messages.getString("FIELD.0.1.NOT_REPEATABLE"), uf.getName(), uf.getDescription()));
        else
            record.getFields().add(index, field);
        RecordUtils.sortFields();
    }


    public static Subfield addSubfied(USubfield us, String text) throws UValidatorException {
        Obrada.editorFrame.recordUpdated();
        UField uf = us.getOwner();
        char sfName = us.getName();
        Field f;
        if (selectedField != null && selectedField.getName().equals(us.getOwner().getName())) {
            f = selectedField;
            if (f != null) {
                if (f.getSubfield(sfName) != null) {
                    // polje vec sadrzi dato potpolje
                    if (!us.isRepeatable()) {
                        // potpolje nije ponovljivo
                        throw new UValidatorException(MessageFormat.format(Messages.getString("SUBFIELD.0.1.NOT_REPEATABLE_IN_FIELDS.n.2.3"), us.getName(), us.getDescription(), uf.getName(), uf.getDescription()));
                    }
                }
            }
        } else if (RecordUtils.getFieldCount(uf.getName()) == 1) {
            f = record.getField(uf.getName());
        } else f = null;
        if (f != null) {
            Subfield sf = new Subfield(sfName);
            sf.setContent(text);
            RecordUtils.addOnRightPlace(f, sf);
            RecordUtils.sortFields();

            return sf;
        }
        return null;
    }

    /*
     * dodaje potpolje sf u polje f
     * na mesto index
     * vrsi se provera ponovljivosti
     */
    public static void addSubfiled(Field f, Subfield sf, int index) throws UValidatorException {
        USubfield usf = CurrFormat.getFullFormat()
                .getSubfield(f.getName() + sf.getName());
        UField uf = CurrFormat.getFullFormat().getField(f.getName());
        if (f.getSubfield(sf.getName()) != null && !usf.isRepeatable())
            throw new UValidatorException(MessageFormat.format(Messages.getString("SF.0.1.NOT_REPEATABLE_IN_FIELDS.2.3"), usf.getName(), usf.getDescription(), uf.getName(), uf.getDescription()));
        else
            f.getSubfields().add(index, sf);
    }

    public static void changeSubfieldConetent(Subfield sf, String fieldName, String newContent) throws UValidatorException {
        if (newContent.endsWith("\n")) {
            newContent = newContent.substring(0, newContent.length() - 1);
        }
        Obrada.editorFrame.recordUpdated();
        USubfield us = CurrFormat.getUSubfield(fieldName, sf.getName());
        if (!newContent.equals("")) {
            //prazan string se ne validira
            if (us.getCoder() != null) {
                if (us.getOwner().getName().equalsIgnoreCase("992") && (us.getName() == 'b')) {
                    //validira nad podacima iz tabele
                    if (!BisisApp.appConfig.getCodersHelper().isValid992b(newContent))
                        throw new UValidatorException
                                (MessageFormat.format(Messages.getString("CHOSEN_CODE_NOT_DEFINED_FOR_FIELD.0.1"), us.getName(), us.getDescription()));
                } else if (us.getOwner().getName().equalsIgnoreCase("992") && (us.getName() == 'f')) {
                    if (!BisisApp.appConfig.getCodersHelper().isValidLibrarian(newContent))
                        throw new UValidatorException
                                (MessageFormat.format(Messages.getString("CHOSEN_CODE_NOT_DEFINED_FOR_SUBFIELD.n.0.1"), us.getName(), us.getDescription()));
                } else {
                    if (!newContent.equals("") && us.getCoder().getValue(newContent) == null) {
                        throw new UValidatorException
                                (MessageFormat.format(Messages.getString("CHOSEN_CODE_NOT_DEFINED_FOR_SUBFIELD.0.1"), us.getName(), us.getDescription()));
                    }
                }
            }
            String target = us.getOwner().getName() + us.getName();
            UValidator v = ValidatorFactory.getValidator(target);
            if (v != null && !v.isValid(newContent).equals("")) {
                throw new UValidatorException(v.isValid(newContent));
            }
        }
    }

    public static void changeIndicatroValue(UIndicator ui, String newValue) throws UValidatorException {
        if (newValue.endsWith("\n")) {
            newValue = newValue.substring(0, newValue.length() - 1);
        }
        if (newValue.equals("") || ui.getValue(newValue) == null)
            throw new UValidatorException
                    (Messages.getString("CODE_NOT_DEFINED_FOR_INDICATOR"));
    }

    //da li je zapis na cirilicnom pismu
    public static boolean isCyrRecord(){
        if (record.getSubfieldContent("100h") != null && record.getSubfieldContent("100h").equals("scc"))
            return true;
        return false;
    }

    public static String validateRecord() {
        StringBuffer izvestaj = new StringBuffer();
        StringBuffer message = new StringBuffer();
        String potpoljePref = Messages.getString("NOT_ENTERED_MANDATORY_FIELD");
        valid = true;
        List<USubfield> mandatorySubfields = CurrFormat.returnMandatorySubfields();
        for (int i = 0; i < mandatorySubfields.size(); i++) {
            USubfield usf = mandatorySubfields.get(i);
            String usfFullName = usf.getOwner().getName() + usf.getName();
            if (record.getSubfield(usfFullName) == null ||
                    (!CurrFormat.block4.contains(usfFullName.substring(0, 3)) // nije polje iz bloka 4 i treba da sadrzi tekst
                            && record.getSubfieldContent(usfFullName).equals(""))

                    ||
                    (CurrFormat.block4.contains(usfFullName.substring(0, 3)) // jeste polje iz bloka 4 i proveravamo da li je uneto neko skundarno polje
                            && record.getSubfield(usfFullName).getSecField() == null)
                    ) {
                valid = false;
                message.append(potpoljePref + usfFullName + "-" + usf.getDescription() + "\n");
            }
        }
        if (valid) {
            izvestaj.append(Messages.getString("RECORD_IS_VALID"));
        } else {
            izvestaj.append(Messages.getString("RECORD_NOT_VALID"));
            izvestaj.append("\n");
            izvestaj.append(message);
        }
        return izvestaj.toString();
    }

    public static String validateHoldingsData() {
        StringBuffer retVal = new StringBuffer();
        for (Primerak p : record.getPrimerci())
            retVal.append(InventarValidation.validateInvBrojUnique(p.getInvBroj()));
        for (Godina g : record.getGodine())
            retVal.append(InventarValidation.validateInvBrojUnique(g.getInvBroj()));
        valid = retVal.toString().equals("");
        return retVal.toString();
    }


    public static String getTitle() {
        if (record.getSubfield("200a") != null)
            return record.getSubfieldContent("200a");
        else return "";
    }

    // vraca vrednost iz 675b
    public static String getSigUdk() {
        for (Field f : record.getFields("675")) {
            if (f.getSubfield('b') != null && !f.getSubfield('b').getContent().equals(""))
                return f.getSubfield('b').getContent();
        }
        return "";
    }

    public static int brojPrimeraka() {
        return record.getPrimerci().size();
    }

    public static Primerak getPrimerak(int i) {
        return record.getPrimerci().get(i);
    }

    public static void addPrimerak(Primerak p) {
        record.getPrimerci().add(p);
    }

    public static Record getRecord() {
        return record;
    }

    public static void removePrimerak(int i) {
        if (record.getPrimerci().size() > 0)
            record.getPrimerci().remove(i);
    }

    public static int brojGodina() {
        return record.getGodine().size();
    }

    public static Godina getGodina(int i) {
        return record.getGodine().get(i);
    }

    public static void addGodina(Godina g) {
        record.getGodine().add(g);
    }

    public static void removeGodina(int i) {
        if (record.getGodine().size() > 0)
            record.getGodine().remove(i);
    }
}

