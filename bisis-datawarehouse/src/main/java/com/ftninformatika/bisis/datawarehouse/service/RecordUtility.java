package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.records.Record;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class RecordUtility {
    public static String getTitle(Record rec){
        String sf200a= nvl(rec.getSubfieldContent("200a"));
        return sf200a;
    }
    public static String getPublisher(Record rec){
        String sf210c = nvl(rec.getSubfieldContent("210c"));
        return sf210c;
    }
    public static String getPublicationYear(Record rec){
        String sf210d = nvl(rec.getSubfieldContent("210d"));
        return sf210d;
    }
    public static String getAutor(Record rec) {
        if (rec.getField("700") != null) {
            String sfa = nvl(rec.getSubfieldContent("700a")).trim();
            String sfb = nvl(rec.getSubfieldContent("700b")).trim();
            if (sfa.length() > 0) {
                if (sfb.length() > 0)
                    return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
                else
                    return toSentenceCase(sfa);
            } else {
                if (sfb.length() > 0)
                    return toSentenceCase(sfb);
                else
                    return "";
            }
        } else if (rec.getField("701") != null) {
            String sfa = nvl(rec.getSubfieldContent("701a")).trim();
            String sfb = nvl(rec.getSubfieldContent("701b")).trim();
            if (sfa.length() > 0) {
                if (sfb.length() > 0)
                    return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
                else
                    return toSentenceCase(sfa);
            } else {
                if (sfb.length() > 0)
                    return toSentenceCase(sfb);
                else
                    return "";
            }
        } else if (rec.getField("702") != null) {
            String sfa = nvl(rec.getSubfieldContent("702a")).trim();
            String sfb = nvl(rec.getSubfieldContent("702b")).trim();
            if (sfa.length() > 0) {
                if (sfb.length() > 0)
                    return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
                else
                    return toSentenceCase(sfa);
            } else {
                if (sfb.length() > 0)
                    return toSentenceCase(sfb);
                else
                    return "";
            }
        }
        return "";
    }

    private static String toSentenceCase(String s) {
        StringBuffer retVal = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, " -.", true);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            if (word.length() > 0)
                retVal.append(Character.toUpperCase(word.charAt(0))
                        + word.substring(1).toLowerCase());

        }
        return retVal.toString();
    }

    private static String nvl(String s) {
        return s == null ? "" : s.trim();
    }

    public static String getRecordType(Record rec){
        String sf001b = nvl(rec.getSubfieldContent("001b"));
        return sf001b;
    }
    public static String getTarget(Record rec){
        String sf100e = nvl(rec.getSubfieldContent("100e"));
        return sf100e;
    }
    public static String getBibliographicLevel(Record rec){
        String sf001c = nvl(rec.getSubfieldContent("001c"));
        return sf001c;
    }

    public static String getSerialType(Record rec){
        String sf001c = nvl(rec.getSubfieldContent("110a"));
        return sf001c;
    }

    public static List<String> getCountry(Record rec){
        List<String> sf102a = rec.getSubfieldsContent("102a");
        return sf102a;
    }
    public static List<String> getLanguage(Record rec){
        List<String> sf101a = rec.getSubfieldsContent("101a");
        return sf101a;
    }

    public static List<String> getContentType(Record rec){
        List<String> sf105b = rec.getSubfieldsContent("105b");
        return sf105b;
    }
    public static List<String> getUDKs(Record rec){
        List<String> sf675a = rec.getSubfieldsContent("675a");
        return sf675a.stream().filter(s->!s.isEmpty()).map(s -> s.substring(0,1)).collect(Collectors.toList());
    }


}
