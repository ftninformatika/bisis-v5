package com.ftninformatika.bisis.ecard;

import com.ftninformatika.utils.string.LatCyrUtils;
import lombok.*;
import net.devbase.jfreesteel.EidInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElCardInfo {

    private String messageKey;
    private boolean success = false;
    private boolean latnLocale = true;

    private String firstName;
    private String lastName;
    private String jmbg;
    private String docNo;
    private String parentName;
    private Date birthday;

    @Getter(AccessLevel.PRIVATE)
    private EidInfo info;

    public void set() {
        setFirstName();
        setLastName();
        setJmbg();
        setDocNo();
        setBirthDay();
        setParentName();
    }

    public void setFirstName() {
        firstName = latnLocale ? LatCyrUtils.toLatin(info.getGivenName())
                : LatCyrUtils.toCyrillic(info.getGivenName());
    }

    public void setLastName() {
        lastName =  latnLocale ? LatCyrUtils.toLatin(info.getSurname())
                : LatCyrUtils.toCyrillic(info.getSurname());
    }

    public void setDocNo() {
        docNo = info.getDocRegNo();
    }

    public void setJmbg() {
        jmbg =  info.getPersonalNumber();
    }

    public void setParentName() {
        parentName = latnLocale ? LatCyrUtils.toLatin(info.getParentGivenName())
                : LatCyrUtils.toCyrillic(info.getParentGivenName());
    }

    public String getAddress() {
        String address = info.getStreet() + ", " + info.getHouseNumber();
        return latnLocale ? LatCyrUtils.toLatin(address)
                : LatCyrUtils.toCyrillic(address);
    }

    public String getCity() {
        return latnLocale ? LatCyrUtils.toLatin(info.getPlace())
                : LatCyrUtils.toCyrillic(info.getPlace());
    }

    public void setBirthDay() {
        try {
            birthday = new SimpleDateFormat("dd.MM.yyyy").parse(info.getDateOfBirth());
        } catch (ParseException e) {
            birthday = null;
        }
    }

    public String getDocCity() {
        return latnLocale ? LatCyrUtils.toLatin(info.getCommunity())
                : LatCyrUtils.toCyrillic(info.getCommunity());
    }

    public String getCountry() {
        return latnLocale ? LatCyrUtils.toLatin(info.getState())
                : LatCyrUtils.toCyrillic(info.getState());
    }

    public String getGender() {
        return info.getSex();
    }
}
