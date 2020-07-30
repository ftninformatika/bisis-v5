package com.ftninformatika.bisis.ecard;

import com.ftninformatika.utils.string.LatCyrUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.devbase.jfreesteel.EidInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ElCardInfo {

    private String messageKey;
    private boolean success = false;
    private boolean latnLocale = true;

    @Getter(AccessLevel.PRIVATE)
    private EidInfo info = null;

    public String getFirstName() {
        return latnLocale ? LatCyrUtils.toLatin(info.getGivenName())
                : LatCyrUtils.toCyrillic(info.getGivenName());
    }

    public String getLastName() {
        return latnLocale ? LatCyrUtils.toLatin(info.getSurname())
                : LatCyrUtils.toCyrillic(info.getSurname());
    }

    public String getDocNo() {
        return info.getDocRegNo();
    }

    public String getJmbg() {
        return info.getPersonalNumber();
    }

    public String getParentName() {
        return latnLocale ? LatCyrUtils.toLatin(info.getParentGivenName())
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

    public Date getBirthDay() {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(info.getDateOfBirth());
        } catch (ParseException e) {
            return null;
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
