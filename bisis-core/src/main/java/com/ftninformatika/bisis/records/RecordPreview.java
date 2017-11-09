package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Petar on 11/9/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordPreview {

    private HashMap etal;

    private String author;
    private String titleAdditions;
    private String title;
    private String publisher;
    private String publishingYear;
    private String keywords;
    private String signature;
    private String pages;
    private String dimensions;
    private String genre;
    private String bibliographyNumber;
    private String publishingPlace;
    private String invertoryAdditions;
    private String publishedCount;
    private String udk;

    public void init(Record r){
        this.author = getAuthor(r, "sr").toString();
        this.title = getTitle(r);
        this.publisher = getPublisher(r);
        this.publishingYear = getYear(r);
        this.titleAdditions = r.getSubfieldContent("200e");
        this.signature = null;
        this.pages = r.getSubfieldContent("215a");
        this.dimensions = r.getSubfieldContent("215d");
        this.genre = r.getSubfieldContent("225a");
        this.publishingPlace = r.getSubfieldContent("210a");
        this.invertoryAdditions = r.getSubfieldContent("996r");
        this.publishedCount = r.getSubfieldContent("992c");
        this.bibliographyNumber = null;
        this.udk = null;

    }



    private StringBuffer getAuthor(Record rec, String locale) {
        StringBuffer retVal = new StringBuffer();
        List<Field> f700 = rec.getFields("700");
        if (f700.size() > 0) {
            Field f1 = (Field)f700.get(0);
            if (f1.getInd2() == '1') {
                Subfield sfa = f1.getSubfield('a');
                Subfield sfb = f1.getSubfield('b');
                if (sfa != null)
                    retVal.append(sfa.getContent());
                if (sfb != null) {
                    if (retVal.length() > 0)
                        retVal.append(", ");
                    retVal.append(sfb.getContent());
                }
            } else {
                Subfield sfa = f1.getSubfield('a');
                if (sfa != null)
                    retVal.append(sfa.getContent());
            }
            if (imaViseAutora(rec)) {
                //appendEtAl(retVal, locale);
                return retVal;
            }
        }
        List<Field> f701 = rec.getFields("701");
        if (f701.size() > 0 &&
                f701.get(0).getSubfieldContent('a')!=null &&
                !f701.get(0).getSubfieldContent('a').equals("")) {
            if (retVal.length() > 0) {
                //appendEtAl(retVal, locale);
                return retVal;
            }
            Field f1 = (Field)f701.get(0);
            if (f1.getInd2() == '1') {
                Subfield sfa = f1.getSubfield('a');
                Subfield sfb = f1.getSubfield('b');
                if (sfa != null)
                    retVal.append(sfa.getContent());
                if (sfb != null) {
                    if (retVal.length() > 0)
                        retVal.append(", ");
                    retVal.append(sfb.getContent());
                }
            } else {
                Subfield sfa = f1.getSubfield('a');
                if (sfa != null)
                    retVal.append(sfa.getContent());
            }
            if (f701.size() > 1) {
                //appendEtAl(retVal, locale);
                return retVal;
            }
        }
        List<Field> f702 = rec.getFields("702");
        if (f702.size() > 0 &&
                f702.get(0).getSubfieldContent('a')!=null &&
                !f702.get(0).getSubfieldContent('a').equals("")) {
            if (retVal.length() > 0) {
                // appendEtAl(retVal, locale);
                return retVal;
            }
            Field f1 = (Field)f702.get(0);
            if (f1.getInd2() == '1') {
                Subfield sfa = f1.getSubfield('a');
                Subfield sfb = f1.getSubfield('b');
                if (sfa != null)
                    retVal.append(sfa.getContent());
                if (sfb != null) {
                    if (retVal.length() > 0)
                        retVal.append(", ");
                    retVal.append(sfb.getContent());
                }
            } else {
                Subfield sfa = f1.getSubfield('a');
                if (sfa != null)
                    retVal.append(sfa.getContent());
            }
    /*  if (f702.size() > 1) {
        appendEtAl(retVal, locale);
        return retVal;
      }
*/    }
        return retVal;
    }


    // da li ima vise polja 700 u kojima stvarno nesto pise
    private boolean imaViseAutora(Record rec){
        List<Field> f700 = rec.getFields("700");
        if(f700.size()<=1) return false;
        else{
            if(f700.get(1).getSubfield('a')==null) return false;
            else if(f700.get(1).getSubfieldContent('a')!=null &&
                    !f700.get(1).getSubfieldContent('a').equals(""))
                return true;
        }
        return false;

    }

    /*
     * vraca sve sto se nalazi u 200
     * */
    private String getTitle(Record rec) {
        StringBuffer titleBuff = new StringBuffer();
        Field f200 = rec.getField("200");
        String temp;
        if (f200 != null) {
            temp = getAllSubfieldsContent(f200, 'a');
            titleBuff.append(temp);
            temp = getAllSubfieldsContent(f200, 'b');
            if (!temp.equals("")) titleBuff.append(", ");
            titleBuff.append(temp);
            temp = getAllSubfieldsContent(f200, 'c');
            if (!temp.equals("")) titleBuff.append(", ");
            titleBuff.append(temp);
            temp = getAllSubfieldsContent(f200, 'd');
            if (!temp.equals("")) titleBuff.append(", ");
            titleBuff.append(temp);
            temp = getAllSubfieldsContent(f200, 'e');
            if (!temp.equals("")) titleBuff.append(", ");
            titleBuff.append(temp);

            //h i i
            List<Subfield> sf200h = f200.getSubfields('h');
            List<Subfield> sf200i = f200.getSubfields('i');

            temp = "";
            boolean prvi = true;
            for(int i=0;i<sf200h.size();i++){
                String hiStr = sf200h.get(i).getContent();
                if(i<sf200i.size()){
                    hiStr.concat(" "+sf200i.get(i).getContent());
                }
                if(prvi){
                    temp = temp+hiStr;
                    prvi = false;
                }else
                    temp = temp + ", "+hiStr;
            }

            if (!temp.equals("")) titleBuff.append(", ");
            titleBuff.append(temp);

    	/*
      Subfield sfa = f200.getSubfield('a');
      if (sfa != null)
        return sfa.getContent();*/
        }
        return titleBuff.toString();
    }

    private String getPublisher(Record rec) {
        Field f210 = rec.getField("210");
        if (f210 != null) {
            Subfield sfc = f210.getSubfield('c');
            if (sfc != null)
                return sfc.getContent();
        }
        return "";
    }

    private String getYear(Record rec) {
        Field f100 = rec.getField("100");
        if (f100 != null) {
            Subfield sfc = f100.getSubfield('c');
            if (sfc != null)
                return sfc.getContent();
        }
        return "";
    }

    private String getPlace(Record rec) {
        Field f210 = rec.getField("210");
        if (f210 != null) {
            Subfield sfa = f210.getSubfield('a');
            if (sfa != null)
                return sfa.getContent();
        }
        return "";
    }

    private String getEdition(Record rec){
        Field f205 = rec.getField("205");
        if(f205!=null){
            Subfield sfa = f205.getSubfield('a');
            if (sfa != null)
                return sfa.getContent();
        }
        return "";
    }

    private String getAllSubfieldsContent(Field f, char sfName){
        StringBuffer retVal = new StringBuffer();
        boolean prvi = true;
        for(Subfield sf : f.getSubfields(sfName)){
            if(prvi){
                retVal.append(sf.getContent());
                prvi = false;
            }
            else
                retVal.append(", "+sf.getContent());
        }
        return retVal.toString();
    }

    /*private void appendEtAl(StringBuffer sb, String locale) {
        String i_dr = (String)etal.get(locale);
        if (i_dr != null)
            sb.append(i_dr);
    }*/

}
