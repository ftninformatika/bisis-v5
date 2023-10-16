package com.ftninformatika.utils;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

public class BookCommonHelper {
    public static SearchModel generateIsbnSearchModel(String isbn) {
        SearchModel searchModel = new SearchModel();
        searchModel.setPref1("BN");
        searchModel.setText1(isbn);
        searchModel.setOper1("AND");
        searchModel.setPref2("");
        searchModel.setText2("");
        searchModel.setOper2("AND");
        searchModel.setPref3("");
        searchModel.setText3("");
        searchModel.setOper3("AND");
        searchModel.setPref4("");
        searchModel.setText4("");
        searchModel.setOper4("AND");
        searchModel.setPref5("");
        searchModel.setText5("");
        searchModel.setOper5("AND");
        return searchModel;
    }

    /**
     * Proverava da li je prvi 010a zapravo ISBN koji je pronasao (drugi se koristi za izdavacku delatnost - BGB)
     */
    public static boolean checkIf1st010FieldisIsbn(Record record, String isbn) {
        List<Field> _010Fields = record.getFields("010");
        isbn = isbn.replace("-", "").replace(" ", "").replace("978", "");
        if (_010Fields != null && _010Fields.size() != 0) {
            String _1stIsbn = _010Fields.get(0).getSubfieldContent('a');
            if (_1stIsbn != null) {
                _1stIsbn = _1stIsbn.replace("-", "").replace(" ", "");
                return _1stIsbn.indexOf(isbn) > 0;
            }
        }
        return false;
    }

    /**
     * Neki isbn su uneti u formatu [10] a neki u formatu [13]
     * pretragu vrsimo za obe varijante jer referenciraju isti zapis
     */
    public static  List<String> generateIsbnPair(String isbn) {
        List<String> isbnPair = new ArrayList<>();
        if (!validateIsbn(isbn)) return null;
        isbnPair.add(isbn);
        String isbnSecFormat;
        if (!validateIsbn10(isbn)) {
            isbnSecFormat = isbn.substring(3).replaceAll("-", "").trim();
            isbnPair.add(isbnSecFormat);
            return isbnPair;
        }
        if (!validateIsbn13(isbn)) {
            isbnSecFormat = 978 + isbn.replaceAll("-", "").trim();
            isbnPair.add(isbnSecFormat);
            return isbnPair;
        }
        return isbnPair;
    }

    public static  boolean validateIsbn(String isbn) {
        return  validateIsbn10(isbn) || validateIsbn13(isbn);
    }

    public static  boolean validateIsbn10(String isbn) {
        if (isbn == null) {
            return false;
        }
        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
        if (isbn.length() != 10) {
            return false;
        }
        try {
            Double.parseDouble(isbn.substring(0, 9));
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static  boolean validateIsbn13(String isbn) {
        if ( isbn == null ) {
            return false;
        }
        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
        if (isbn.length() != 13) {
            return false;
        }
        try {
            Double.parseDouble(isbn.substring(0, 12));
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
}
