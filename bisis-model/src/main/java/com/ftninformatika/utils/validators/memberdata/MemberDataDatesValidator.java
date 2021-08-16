package com.ftninformatika.utils.validators.memberdata;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Duplicate;
import com.ftninformatika.bisis.circ.pojo.PictureBook;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.utils.date.DateUtils;

public class MemberDataDatesValidator {

    public static MemberDateError validateMemberDataDates(MemberData memberData) {
        if (memberData == null)
            return MemberDateError.NO_ERROR;
        if (memberData.getMember() != null) {
            Member m = memberData.getMember();
            if (!DateUtils.isValidDate(m.getBirthday()))
                return MemberDateError.BIRTHDAY_DATE_ERROR;
            if (m.getCorporateMember() != null && !DateUtils.isValidDate(m.getCorporateMember().getSignDate()))
                return MemberDateError.CORPORATE_MEMBER_SIGN_DATE_ERROR;
            if (m.getSignings() != null && m.getSignings().size() > 0){
                for(Signing signing: m.getSignings()) {
                    if (!DateUtils.isValidDate(signing.getSignDate()))
                        return MemberDateError.SIGNINGS_SIGN_DATE_ERROR;
                    if (!DateUtils.isValidDate(signing.getUntilDate()))
                        return MemberDateError.SIGNINGS_UNTIL_DATE_ERROR;
                }
            }
            if (m.getDuplicates() != null && m.getDuplicates().size() > 0) {
                for (Duplicate duplicate: m.getDuplicates())
                    if (!DateUtils.isValidDate(duplicate.getDupDate()))
                        return MemberDateError.DUPLICATE_DATE_ERROR;
            }
            if (m.getPicturebooks() != null && m.getPicturebooks().size() > 0) {
                for (PictureBook p: m.getPicturebooks())
                    if (!DateUtils.isValidDate(p.getLendDate()))
                        return MemberDateError.PICTUREBOOK_DATE_ERROR;
            }
        }
        if (memberData.getLendings() != null && memberData.getLendings().size() > 0) {
            for (Lending lending: memberData.getLendings()) {
                if (!DateUtils.isValidDate(lending.getLendDate()))
                    return MemberDateError.LENDING_LEND_DATE_ERROR;
                if (!DateUtils.isValidDate(lending.getResumeDate()))
                    return MemberDateError.LENDING_RESUME_DATE_ERROR;
                if (!DateUtils.isValidDate(lending.getResumeDate()))
                    return MemberDateError.LENDING_RESUME_DATE_ERROR;
                if (!DateUtils.isValidDate(lending.getReturnDate()))
                    return MemberDateError.LENDING_RETURN_DATE_ERROR;
                if (!DateUtils.isValidDate(lending.getDeadline()))
                    return MemberDateError.LENDING_DEADLINE_DATE_ERROR;
            }
        }
        return MemberDateError.NO_ERROR;
    }

}
