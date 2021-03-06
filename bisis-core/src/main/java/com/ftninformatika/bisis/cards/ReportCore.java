package com.ftninformatika.bisis.cards;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.file.FileUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author badf00d21  20.9.19.
 */
public class ReportCore {

    public static int brRed;
    public static String currentType="";
    public  static String locale;
    public  static String nextPage;
    public  static int brRedova;

    public static String[] reportTypeDesc;
    public static String[] reportTypes;
    public static int translateX;
    public static int translateY;
    public static String fontSize;
    static String izlaz1;
    static int brmax;
    static int bkmax;

    private static Logger log = Logger.getLogger(ReportCore.class.getName());
    private static Record record=null;


    static{
        loadReportTypes();
    }

    /** Formira izvestaj za vise zapisa.
     @param docIDs niz ID zapisa
     @param typeCode tip listica
     @return HTML izvestaj
     */
    public static String makeMulti(int[] docIDs, Record[] recs, String typeCode) {
        String retVal = "";
        for (int i = 0; i < docIDs.length; i++) {
            retVal += makeOne(docIDs[i], recs[i], typeCode);
        }
        return retVal;
    }

//    public static String makeMulti(Record[] recs, boolean saInventarnim){
//        String retVal = "";
//        for(Record rec:recs){
//            retVal += makeOne(rec, saInventarnim);
//            retVal += "<BR><BR>";
//        }
//        return retVal;
//    }

    /** Formira izvestaj za jedan zapis.
     @param docID ID zapisa
     @param typeCode tip listica
     @return HTML izvestaj
     */

    public static String makeOne(int docID, Record  rec, String typeCode){

        String izlaz;
        boolean formatRed=true;
        boolean formatStrana=true;
        int brSignatura;
        String type;
        record=rec;

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(ReportCore.class, "/templejti/" +locale+"/");

        BaseCore Base=new BaseCore(docID, rec, typeCode);

        Template temp=null;
        Template temp1=null;
        try{
            temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");

        }catch(Exception e1){
            e1.printStackTrace();
            try{
                temp = cfg.getTemplate(typeCode+".ftl");

            }catch(Exception e2){
                e2.printStackTrace();
                return izlaz= Messages.getString("CARDS_NO_CARD_TYPE");
            }
        }
        try{
            temp1 = cfg.getTemplate("_novaStr_"+locale+".ftl");
        }catch(Exception e3){
            try{
                temp1 = cfg.getTemplate("_novaStr.ftl");
            }catch(Exception e4){
                return izlaz= Messages.getString("CARDS_INADEQUATE_CARD_TYPE");
            }

        }

        Map<String,Object> root=Base.struktura();

        RecordUtilitiesCore recUtilities = new RecordUtilitiesCore(rec);
        root.put("recUtil", recUtilities);
        root.put("docID",new Integer(Base.getID()));
        root.put("saInventarnim", true);
        root.put("nextPage",nextPage);

        try{
            ResourceBundle rb = PropertyResourceBundle.getBundle(
                    //Report.class.getPackage().getName()+".templejti."+locale+"."+typeCode,new Locale(locale));//
                    "templejti." + locale + "." + typeCode,new Locale(locale));

            //format=rb.getString("format").equals("true");
            formatRed=rb.getString("formatRed").equals("true");
            formatStrana=rb.getString("formatStrana").equals("true");

            brmax=Base.toInt(rb.getString("brmax"));
            bkmax=Base.toInt(rb.getString("bkmax"));

            type=rb.getString("type");
            brSignatura=Base.toInt(rb.getString("brSignatura"));
            root.put("brSignatura",new Integer(brSignatura));
            root.put("brk",new Integer (bkmax-1));
        }catch(Exception ex1){
            return izlaz= Messages.getString("CARDS_ERROR_DETERMINING_PROPERTIES");
        }

        if (!Base.checkPubType(type))
            return izlaz = Messages.getString("CARDS_WRONG_PUB_TYPE");

        Writer out=new StringWriter();
        Writer out1=new StringWriter();
        try {
            temp.process(root,out);
            temp1.process(root,out1);
        } catch (Exception ex) {
            log.fatal("Ne valja templejt1");
        }

        try {
            out.flush();
            out1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        izlaz1=out1.toString();
        izlaz1=Base.format(izlaz1);

        izlaz=out.toString();
        // if (format)
        izlaz=Base.format(izlaz);

        if(formatRed && !formatStrana) {
            izlaz=Base.formatRed1(izlaz);
        }
        else if (formatRed){
            izlaz=Base.formatRed(izlaz);
        }
        if(formatStrana)
            if(!izlaz.equals("")){
                izlaz=Base.formatStrana(izlaz);
            }
        return izlaz;
    }


    /**
     * @author bojana dimic
     *
     * kreira listic za prosledjeni zapis
     * pri cemu se programski odredjuje koji listic
     * ce se prikazati u zavisnosti od tipa publikacije
     *
     * monografska publikacija (tip 1) - monogarfski_locale.ftl
     * serijska publikacija (tip 2) - serijski_locale.ftl
     * analitika (tip 3) - analiticki_locale.ftl
     *
     *
     */

    public static String makeOne(Record  rec, boolean saInventarnim, LibraryConfiguration libraryConfiguration) {
        ucitajParam(libraryConfiguration);
        String izlaz;
        boolean formatRed=true;
        boolean formatStrana=true;
        int brSignatura;
        String type;

        Configuration  cfg = new Configuration();

        cfg.setClassForTemplateLoading(ReportCore.class, "/templejti/" +locale+"/");
        BaseCore Base=new BaseCore(rec);
        Template temp=null;
        Template temp1=null;
        String typeCode = "";

        switch(rec.getPubType()){
            case 1:typeCode = "monografski"; break;
            case 2:typeCode = "serijski"; break;
            case 3:typeCode = "analiticki"; break;
            case 4:typeCode = "neknjizna"; break;
            case 5:typeCode = "elektronskiIzvori"; break;
        }

        if(typeCode.equals(""))
            return Messages.getString("CARDS_NONEXISTING_CARD_TYPE_FOR_RECORD");


        try{
            temp = cfg.getTemplate(typeCode+"_"+locale+".ftl");

        }catch(Exception e1){
            e1.printStackTrace();

            try{
                temp = cfg.getTemplate(typeCode+".ftl");
            }catch(Exception e2){
                e2.printStackTrace();
                return izlaz= Messages.getString("CARDS_NO_CARD_TYPE");
            }
        }
        try{
            temp1 = cfg.getTemplate("_novaStr_"+locale+".ftl");
        }catch(Exception e3){
            try{
                temp1 = cfg.getTemplate("_novaStr.ftl");
            }catch(Exception e4){
                return izlaz= Messages.getString("CARDS_INADEQUATE_CARD_TYPE");
            }
        }

        Map<String,Object> root=Base.struktura();

        RecordUtilitiesCore recUtilities = new RecordUtilitiesCore(rec);
        root.put("saInventarnim", saInventarnim);
        root.put("recUtil", recUtilities);
        root.put("docID",rec.getRecordID());
        root.put("nextPage",nextPage);

        try{
            ResourceBundle rb = PropertyResourceBundle.getBundle(
                    "templejti." + locale + "." + typeCode,new Locale(locale));


            //format=rb.getString("format").equals("true");
            formatRed=rb.getString("formatRed").equals("true");
            formatStrana=rb.getString("formatStrana").equals("true");

            brmax=Base.toInt(rb.getString("brmax"));
            bkmax=Base.toInt(rb.getString("bkmax"));

            type=rb.getString("type");
            brSignatura=Base.toInt(rb.getString("brSignatura"));
            root.put("brSignatura",new Integer(brSignatura));
            root.put("brk",new Integer (bkmax-1));
        }catch(Exception ex1){
            return izlaz= Messages.getString("CARDS_ERROR_DETERMINING_PROPERTIES");
        }

        if (!Base.checkPubType(type))
            return izlaz = Messages.getString("CARDS_WRONG_PUB_TYPE");

        Writer out=new StringWriter();
        Writer out1=new StringWriter();
        try {
            temp.process(root,out);
            temp1.process(root,out1);
        } catch (Exception ex) {
            log.fatal("Ne valja templejt1");
        }

        try {
            out.flush();
            out1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        izlaz1=out1.toString();
        izlaz1=Base.format(izlaz1);

        izlaz=out.toString();
        // if (format)
        izlaz=Base.format(izlaz);

        if(formatRed && !formatStrana) {
            izlaz=Base.formatRed1(izlaz);
        }
        else if (formatRed){
            izlaz=Base.formatRed(izlaz);
        }
        if(formatStrana)
            if(!izlaz.equals("")){
                izlaz=Base.formatStrana(izlaz);
            }

        String dodatak="";
        if(locale.equals("bs"))
            dodatak=RecordUtilitiesCore.getGodineSabac(rec);

        return izlaz+dodatak;

    }


    public static void ucitajParam(LibraryConfiguration libraryConfiguration){

        try{
            translateX = Integer.parseInt(libraryConfiguration.getBookcardsTranslateX());
            translateY = Integer.parseInt(libraryConfiguration.getBookcardsTranslateY());
            fontSize = libraryConfiguration.getBookcardsFontSize();
            brRedova = Integer.parseInt(libraryConfiguration.getBookcardsBrRedova());
            locale = libraryConfiguration.getBookcardsLocale();
            nextPage= libraryConfiguration.getBookcardsNextPage();
            currentType = libraryConfiguration.getBookcardsCurrentType();
        }catch(Exception e) {

            e.printStackTrace();
        }


    }

    public final static void loadReportTypes()  {
        String str="";
        try {
            String dirName = "/templejti/" +locale;
            String[] files = FileUtils.listFiles(ReportCore.class, dirName);
            int brojFajlova=0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".ftl")){
                    str=files[i].replaceAll("_"+locale+".ftl","");
                    str=str.substring(str.lastIndexOf(locale)+locale.length()+1);
                    if (!str.startsWith("_")) {
                        brojFajlova++;
                    }
                }
            }
            reportTypes = new String[brojFajlova];
            boolean existCurrent=false;
            int j=0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".ftl")) {
                    str=files[i].replaceAll("_"+locale+".ftl","");
                    str=str.substring(str.lastIndexOf(locale)+locale.length()+1);

                    if( !str.startsWith("_")) {
                        reportTypes[j]=str;
                        if (str.equals(currentType))
                            existCurrent=true;
                        j++;
                    }
                }
            }
            if(!existCurrent ){
                JOptionPane.showMessageDialog(null, Messages.getString("CARDS_NO_CURRENT_TYPE"));
            }
        } catch (Exception ex) {
            log.fatal(Messages.getString("CARDS_NO_CARDS_TYPE_DIRECTORY"));
        }
    }
}
