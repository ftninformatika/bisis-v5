package com.ftninformatika.bisis.opac.dto;

import com.ftninformatika.bisis.circ.Member;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ftninformatika.utils.date.DateUtils.extractBirthDateFromJMBG;

@Getter
@Setter
public class AudioBookRequestDTO {
    private String bookId;
    private String userIdentifier;
    private AudioBookMemberDataDTO memberData;
    private String libraryName;

    public void setMemberData(Member member) {
        AudioBookMemberDataDTO memberData = new AudioBookMemberDataDTO();
        memberData.setCountry(member.getCountry()); // TODO: provetiti ovo, na nekim clanovima nije popunjeno a bisis je samo srb (mozda zakucati)
        memberData.setFirstName(member.getFirstName());
        memberData.setLastName(member.getLastName());
        memberData.setGender(member.getGender()); //TODO: proveriti ocekivani format

        // TODO: indikator za slepe

        // JMBG bi trebao da bude popunjen za sve korisnike, dok rodjendan mozda nije
        Date birthday = extractBirthDateFromJMBG(member.getJmbg());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        memberData.setDateOfBirth(sdf.format(birthday));
        this.memberData = memberData;
    }
}