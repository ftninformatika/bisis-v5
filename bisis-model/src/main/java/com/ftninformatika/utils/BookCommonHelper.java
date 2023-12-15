package com.ftninformatika.utils;

import com.ftninformatika.bisis.search.SearchModel;

public class BookCommonHelper {

    public static SearchModel generateSearchModel(String pref, String value) {
        SearchModel searchModel = new SearchModel();
        searchModel.setPref1(pref);
        searchModel.setText1(value);
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
     * Proverava da li je prvi 010a ili 011a zapravo ISBN/ISSN koji je pronasao (drugi se koristi za izdavacku delatnost - BGB)
     */
//    public static boolean isValidRecord(Record record, String id) {
//        if (id == null)
//            return false;
//        List<Field> _010Fields = record.getFields("010");
//        List<Field> _011Fields = record.getFields("011");
//        id = id.replace("-", "").replace(" ", "").replace("978", "");
//        if (_010Fields != null && _010Fields.size() != 0) {
//            String _1stIsbn = _010Fields.get(0).getSubfieldContent('a');
//            if (_1stIsbn != null) {
//                _1stIsbn = _1stIsbn.replace("-", "").replace(" ", "");
//                return _1stIsbn.indexOf(id) > 0;
//            }
//        }else if (_011Fields != null && _011Fields.size() != 0) {
//            String _1stIssn = _011Fields.get(0).getSubfieldContent('a');
//            if (_1stIssn != null) {
//                _1stIssn = _1stIssn.replace("-", "").replace(" ", "");
//                return _1stIssn.indexOf(id) > 0;
//            }
//        }
//        return false;
//    }

    /**
     * Neki isbn su uneti u formatu [10] a neki u formatu [13]
     * pretragu vrsimo za obe varijante jer referenciraju isti zapis
     */
//    public static  List<String> generateIsbnPair(String isbn) {
//        List<String> isbnPair = new ArrayList<>();
//        if (!validateIsbn(isbn)) return isbnPair;
//        isbnPair.add(isbn);
//        String isbnSecFormat;
//        if (!validateIsbn10(isbn)) {
//            isbnSecFormat = isbn.substring(3).replaceAll("-", "").trim();
//            isbnPair.add(isbnSecFormat);
//            return isbnPair;
//        }
//        if (!validateIsbn13(isbn)) {
//            isbnSecFormat = 978 + isbn.replaceAll("-", "").trim();
//            isbnPair.add(isbnSecFormat);
//            return isbnPair;
//        }
//        return isbnPair;
//    }

//    public static  boolean validateIsbn(String isbn) {
//        return  validateIsbn10(isbn) || validateIsbn13(isbn);
//    }

//    public static  boolean validateIsbn10(String isbn) {
//        if (isbn == null) {
//            return false;
//        }
//        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
//        if (isbn.length() != 10) {
//            return false;
//        }
//        try {
//            Double.parseDouble(isbn.substring(0, 9));
//            return true;
//        } catch (NumberFormatException nfe) {
//            return false;
//        }
//    }

//    public static  boolean validateIsbn13(String isbn) {
//        if ( isbn == null ) {
//            return false;
//        }
//        isbn = isbn.replaceAll( "-", "" ).trim().replace(" ", "");
//        if (isbn.length() != 13) {
//            return false;
//        }
//        try {
//            Double.parseDouble(isbn.substring(0, 12));
//            return true;
//        }
//        catch (NumberFormatException nfe) {
//            return false;
//        }
//    }

    public static String generateISBNForBookCommon(String isbn){
        if ( isbn == null ) {
            return null;
        }
        isbn = isbn.replaceAll( "-", "" ).replace(" ", "").trim();
       if (isbn.length() == 10) {
            return 978+isbn;
        }
         return isbn;
    }
}
