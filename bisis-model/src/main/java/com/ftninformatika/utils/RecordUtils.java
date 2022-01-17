package com.ftninformatika.utils;

import com.ftninformatika.bisis.records.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petar on 1/26/2018.
 */
public class RecordUtils {

    /**
     *
     * @param freshRecord - editovan zapis poslat na bekend
     * @param storedRecord - trenutna verzija istog zapisa
     * @return - listu objekata ItemAvailability koji su novoinventarisani
     */
    public static List<ItemAvailability> getItemAvailabilityNewDelta(Record freshRecord, Record storedRecord){
        List<ItemAvailability> retVal = new ArrayList<>();
        if (freshRecord.getPrimerci() != null && storedRecord.getPrimerci() != null && freshRecord.getPrimerci().size() > 0){
            for (Primerak p: freshRecord.getPrimerci())
                if (!storedRecord.getPrimerci().stream().map(e -> e.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj())){
                    ItemAvailability ia = generateItem(p, freshRecord);
                    retVal.add(ia);
                }
        }

        if (freshRecord.getGodine() != null && storedRecord.getGodine() != null && freshRecord.getGodine().size() > 0){
            for (Godina p: freshRecord.getGodine())
                if (!storedRecord.getGodine().stream().map(e -> e.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj())){
                    ItemAvailability ia = generateItem(p, freshRecord);
                    retVal.add(ia);
                }
        }

        if (freshRecord.getAllSveske() != null && storedRecord.getAllSveske() != null && freshRecord.getAllSveske().size() > 0){
            for (Sveska p: freshRecord.getAllSveske())
                if (!storedRecord.getAllSveske().stream().map(e -> e.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj())){
                    ItemAvailability ia = generateItem(p, freshRecord);
                    retVal.add(ia);
                }
        }

        return retVal;
    }


    private static ItemAvailability generateItem(Primerak p, Record r) {
        ItemAvailability ia = new ItemAvailability();
        ia.setCtlgNo(p.getInvBroj());
        ia.setBorrowed(false);
        ia.setRecordID(String.valueOf(r.getRecordID()));
        ia.setLibDepartment(p.getOdeljenje());
        ia.setRn(r.getRN());
        return ia;
    }

    private static ItemAvailability generateItem(Godina g, Record r) {
        ItemAvailability ia = new ItemAvailability();
        ia.setCtlgNo(g.getInvBroj());
        ia.setBorrowed(false);
        ia.setRecordID(String.valueOf(r.getRecordID()));
        ia.setLibDepartment(g.getOdeljenje());
        ia.setRn(r.getRN());
        return ia;
    }

    private static ItemAvailability generateItem(Sveska s, Record r) {
        ItemAvailability ia = new ItemAvailability();
        ia.setCtlgNo(s.getInvBroj());
        ia.setBorrowed(false);
        ia.setRecordID(String.valueOf(r.getRecordID()));
        ia.setLibDepartment(s.getParent().getOdeljenje());
        ia.setRn(r.getRN());
        return ia;
    }

        /**
         *
         * @param freshRecord - editovan zapis poslat na bekend
         * @param storedRecord - trenutna verzija istog zapisa
         * @return listu inventarnih brojeva koji su obrisani
         */
    public static List<String> getDeletedInvNumsDelta(Record freshRecord, Record storedRecord){
        List<String> retVal = new ArrayList<>();

        if (storedRecord.getPrimerci().size() > 0){
            for (Primerak p: storedRecord.getPrimerci())
                if (!freshRecord.getPrimerci().stream().map(Primerak::getInvBroj).collect(Collectors.toList()).contains(p.getInvBroj()))
                    retVal.add(p.getInvBroj());
        }


        if (storedRecord.getGodine().size() > 0){
            for (Godina g: storedRecord.getGodine())
                if (!freshRecord.getGodine().stream().map(Godina::getInvBroj).collect(Collectors.toList()).contains(g.getInvBroj()))
                    retVal.add(g.getInvBroj());
        }

        if (storedRecord.getAllSveske().size() > 0){
            for (Sveska s: storedRecord.getAllSveske())
                if (!freshRecord.getAllSveske().stream().map(Sveska::getInvBroj).collect(Collectors.toList()).contains(s.getInvBroj()))
                    retVal.add(s.getInvBroj());
        }

        return retVal;
    }

    /**
     * Neki isbn su uneti u formatu [10] a neki u formatu [13]
     * pretragu vrsimo za obe varijante jer referenciraju isti zapis
     */
    public static List<String> generateIsbnPair(String isbn) {
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

    private static boolean validateIsbn(String isbn) {
        return  validateIsbn10(isbn) || validateIsbn13(isbn);
    }

    private static boolean validateIsbn10(String isbn) {
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

    private static boolean validateIsbn13(String isbn) {
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
