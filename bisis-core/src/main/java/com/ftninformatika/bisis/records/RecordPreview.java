package com.ftninformatika.bisis.records;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.prefixes.def.DefaultPrefixMap;
import com.ftninformatika.utils.string.Signature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
/**
 * Class for web preview of record
 */
public class RecordPreview {

    @JsonIgnore List fields1;
    @JsonIgnore List fields2;
    @JsonIgnore List fields3;
    @JsonIgnore String text = "";
    @JsonIgnore String empty = "";

    private String author;
    private String title;
    private String publisher;
    private String publishingYear;
    private String keywords;
    private String signature;
    private String pages;
    private String dimensions;
    private String subtitle;
    private String subjectHeading;
    private String publishingPlace;
    private String notes;
    private String numerus;
    private String udk;
    private String issn;
    private String format;

    public void init(Record r){
        this.author = getAuthor(r);
        this.title = getTitle(r);
        this.publisher = getPublisher(r);
        this.publishingYear = getYear(r);
        this.signature = getSignatura(r);
        this.pages = getPages(r);
        this.dimensions = getDimensions(r);
        this.subtitle = getSubtitle(r);
        this.publishingPlace = getPublishingPlace(r);
        this.notes = getNotes(r);
        this.numerus = getNumerus(r);
        this.subjectHeading = getSubjectHeading(r);
        this.udk = getUDK(r);
        this.issn = getISSN(r);
        this.format = getFormat(r);
        this.keywords = getKeywords(r);
    }

    public String getKeywords(Record rec){
        StringBuffer retVal = new StringBuffer();
        DefaultPrefixMap prefixMap = new DefaultPrefixMap();
        List<String> keyWords = new ArrayList();

        String fieldContent = "";
        for (String subField: prefixMap.getSubfields("KW")) {
            try {
                fieldContent = rec.getSubfieldContent(subField);
                if (fieldContent == null)
                    continue;
                if (!keyWords.contains(fieldContent))
                    keyWords.add(fieldContent);
            } catch (Exception e) {
                continue;
            }
        }
        for (String s: keyWords)
            retVal.append(s + ", ");
        return retVal.toString();
    }

    public String getDimensions(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("215d");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    private String getPages(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("215a");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    public String getPublishingPlace(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("210a");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    private String getUDK(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("675a");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        return text;
    }


    private String getSignatura(Record rec){
        if (rec == null)
            return "";
        text = "";
        try {
            Iterator it = rec.getPrimerci().iterator();
            while (it.hasNext()){
                if (!text.equals(""))
                    text = text + ", ";
                text = text + Signature.format((Primerak)it.next());
            }
            empty = text.substring(0, 1);
        } catch (Exception e1) {
            Iterator it = rec.getGodine().iterator();
            while (it.hasNext()){
                if (!text.equals(""))
                    text = text + ", ";
                text = text + Signature.format((Godina)it.next());
            }
        }
        return text;
    }

    public String getAuthor(Record rec){
        List fields1;
        List fields2;
        List fields3;
        String text = "";
        String empty = "";

        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("700a");
            fields2 = rec.getSubfieldsContent("700b");
            text = fieldsToString(fields1, fields2);
            empty = text.substring(0, 2);
        } catch (Exception e1) {
            try {
                fields1 = rec.getSubfieldsContent("710a");
                text = fieldsToString(fields1);
            } catch (Exception e2) {
            }
        }
        fields1 = null;
        fields2 = null;
        return text;
    }

    private String fieldsToString(List fields){
        String tmp = "";
        Iterator it = fields.iterator();
        while (it.hasNext()){
            String tmp1 = (String)it.next();
            if (!tmp1.equals("")){
                if (!tmp.equals(""))
                    tmp = tmp + "; ";
                tmp = tmp + tmp1;
            }
        }
        return tmp;
    }

    private String fieldsToString(List fields1, List fields2){
        String tmp = "";
        String item1 = "";
        String item2 = "";
        Iterator it1 = fields1.iterator();
        Iterator it2 = fields2.iterator();
        while (it1.hasNext() && it2.hasNext()){
            item1 = it1.next().toString();
            item2 = it2.next().toString();
            if (!item1.equals("") && !item2.equals(""))
                item1 = item1 + ", ";
            item1 = item1 + item2;
            if (!item1.equals("")){
                if (!tmp.equals(""))
                    tmp = tmp + "; ";
                tmp = tmp + item1;
            }
        }
        return tmp;
    }

    private String fieldsToString(List fields1, List fields2, List fields3, String fieldname){
        String tmp = "";
        String item1 = "";
        String item2 = "";
        String item3 = "";
        Iterator it1 = fields1.iterator();
        Iterator it2 = fields2.iterator();
        Iterator it3 = fields3.iterator();
        while (it1.hasNext() && it2.hasNext() && it3.hasNext()){
            item1 = it1.next().toString();
            item2 = it2.next().toString();
            item3 = it3.next().toString();
            if (!item1.equals("") && !item2.equals(""))
                item1 = item1 + ", ";
            item1 = item1 + item2;
            if (!item1.equals("") && !item3.equals(""))
                item1 = item1 + ", ";
            item1 = item1 + getNameFromCode(fieldname,item3);
            if (!item1.equals("")){
                if (!tmp.equals(""))
                    tmp = tmp + "; ";
                tmp = tmp + item1;
            }
        }
        return tmp;
    }

    private String getNameFromCode(String field, String content){
        // if (BisisApp.getFormat().getSubfield(field).getCoder().getValue(content) != null)
        // return BisisApp.getFormat().getSubfield(field).getCoder().getValue(content);
        return "";
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

    public String getTitle(Record rec){


        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("200a");
            text = fieldsToString(fields1);
            empty = text.substring(0, 1);
        } catch (Exception e1) {
            try {
                fields1 = rec.getSubfieldsContent("200i");
                text = fieldsToString(fields1);
                empty = text.substring(0, 1);
            } catch (Exception e2) {
                try {
                    fields1 = rec.getSubfieldsContent("540a");
                    text = fieldsToString(fields1);
                } catch (Exception e3) {
                }
            }
        }
        fields1 = null;
        return text;
    }

    private String getPublisher(Record rec) {
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("210c");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    private String getYear(Record rec) {
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("100c");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    private String getPlace(Record rec) {
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("210a");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
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



    public String getISSN(Record r){
        try {
            if (r.getSubfieldContent("011a") != null && !r.getSubfieldContent("011a").equals(""))
                return r.getSubfieldContent("011a");
            if (r.getSubfieldContent("011e") != null && !r.getSubfieldContent("011e").equals(""))
                return r.getSubfieldContent("011e");
        }catch (Exception e) {
            return "";
        }
        return "";
    }

    public String getSubtitle(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("200e");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    public String getNotes(Record rec){
        if (rec == null)
            return "";
        try {
            fields1 = rec.getSubfieldsContent("300a");
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    public String getSubjectHeading(Record rec){
        if (rec == null)
            return "";
        try {
            Iterator it = rec.getFields().iterator();
            Field fl;
            fields1 = new ArrayList<String>();
            while (it.hasNext()){
                fl = (Field)it.next();
                if ((fl.getName().substring(0,1).equals("6") && !fl.getName().equals("675")) || fl.getName().substring(0,2).equals("96")){
                    Iterator it2 = fl.getSubfields().iterator();
                    while (it2.hasNext()){
                        fields1.add(((Subfield)it2.next()).getContent());
                    }
                }
            }
            text = fieldsToString(fields1);
        } catch (Exception e1) {
        }
        fields1 = null;
        return text;
    }

    public String getFormat(Record rec){
        if (rec == null)
            return "";
        text = "";
        String tmp;
        try {
            Iterator it = rec.getPrimerci().iterator();
            while (it.hasNext()){
                tmp = ((Primerak)it.next()).getSigFormat();
                if (tmp != null){
                    if (!text.equals(""))
                        text = text + ", ";
                    text = text + tmp;
                }
            }
            empty = text.substring(0, 1);
        } catch (Exception e1) {
            Iterator it = rec.getGodine().iterator();
            while (it.hasNext()){
                tmp = ((Godina)it.next()).getSigFormat();
                if (tmp != null){
                    if (!text.equals(""))
                        text = text + ", ";
                    text = text + tmp;
                }
            }
        }
        return text;
    }

    public String getNumerus(Record rec){
        if (rec == null)
            return "";
        text = "";
        String tmp;
        try {
            Iterator it = rec.getPrimerci().iterator();
            while (it.hasNext()){
                tmp = ((Primerak)it.next()).getSigNumerusCurens();
                if (tmp != null){
                    if (!text.equals(""))
                        text = text + ", ";
                    text = text + tmp;
                }
            }
            empty = text.substring(0, 1);
        } catch (Exception e1) {
            Iterator it = rec.getGodine().iterator();
            while (it.hasNext()){
                tmp = ((Godina)it.next()).getSigNumerusCurens();
                if (tmp != null){
                    if (!text.equals(""))
                        text = text + ", ";
                    text = text + tmp;
                }
            }
        }
        return text;
    }

}
