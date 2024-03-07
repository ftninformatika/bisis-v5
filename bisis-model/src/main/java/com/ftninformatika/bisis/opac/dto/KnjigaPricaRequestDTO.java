package com.ftninformatika.bisis.opac.dto;

import com.ftninformatika.bisis.circ.Member;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class KnjigaPricaRequestDTO {
    private String bookId;
    private String userIdentifier;
    private KnjigaPricaMemberData memberData;
    private String libraryName;

    public void setMemberData(Member member) {
        KnjigaPricaMemberData memberData = new KnjigaPricaMemberData();
        memberData.setCountry(member.getCountry()); // TODO: provetiti ovo, na nekim clanovima nije popunjeno a bisis je samo srb (mozda zakucati)
        memberData.setFirstName(member.getFirstName());
        memberData.setLastName(member.getLastName());
        memberData.setGender(member.getGender()); //TODO: proveriti ocekivani format

        // JMBG bi trebao da bude popunjen za sve korisnike, dok rodjendan mozda nije
        Date birthday = extractBirthDateFromJMBG(member.getJmbg());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        memberData.setDateOfBirth(sdf.format(birthday));
        this.memberData = memberData;
    }

    // TODO: move this to util
    public static Date extractBirthDateFromJMBG(String jmbg) {
        // Provera dužine JMBG broja
        if (jmbg.length() != 13) {
            throw new IllegalArgumentException("JMBG mora imati 13 cifara");
        }

        // Ekstrakcija dana, meseca i godine iz JMBG broja
        int day = Integer.parseInt(jmbg.substring(0, 2));
        int month = Integer.parseInt(jmbg.substring(2, 4));
        int yearPart = Integer.parseInt(jmbg.substring(4, 7));
        int centuryPart = Integer.parseInt(jmbg.substring(7, 9));

        // Određivanje godine rođenja
        int year;
        if (centuryPart < 10) {
            year = 2000 + yearPart;
        } else {
            year = 1000 + yearPart;
        }

        // Kreiranje datuma rođenja
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
}