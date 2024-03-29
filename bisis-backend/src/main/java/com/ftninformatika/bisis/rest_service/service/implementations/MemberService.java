package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.core.repositories.ItemAvailabilityRepository;
import com.ftninformatika.bisis.core.repositories.LendingRepository;
import com.ftninformatika.bisis.core.repositories.LibrarianRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.ecard.ElCardInfo;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.utils.date.DateUtils;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author badf00d21  17.10.19.
 */
@Service
public class MemberService {
    @Autowired MemberRepository memberRep;
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired
    LendingRepository lendingRepository;
    @Autowired
    ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired OrganizationRepository organizationRepository;
    @Autowired WarningCounterRepository warningCounterRepository;
    @Autowired MongoClient mongoClient;
    @Autowired OpacSearchService opacSearchService;
    @Autowired
    RecordsRepository recordsRepository;

    public List<Report> getOnlyActiveLendingsReport(String memberNo) {
        return getMemberLendingHistoryReport(memberNo, true);
    }

    public List<Report> getReturnedLendingsReport(String memberNo) {
        return getMemberLendingHistoryReport(memberNo, false);
    }

    public List<Report> getMemberLendingHistoryReport(String memberNo, boolean activeLendings) {
        List<Report> retVal = new ArrayList<>();
        if (memberNo == null)
            return retVal;
        List<Lending> lendings = lendingRepository.findByUserId(memberNo);
        if (lendings == null || lendings.size() == 0)
            return retVal;

        lendings = lendings.stream().filter(l -> (l.getReturnDate() == null) == activeLendings).collect(Collectors.toList());

        lendings.sort(Comparator.comparing(Lending::getLendDate).reversed());
        Record r;
        Book book;
        SimpleDateFormat sdf =new SimpleDateFormat("dd.MM.yyyy");

        for(Lending l:lendings){
            r = recordsRepository.getRecordByPrimerakInvNum(l.getCtlgNo());
            if (r == null)
                continue;
            book = opacSearchService.getBookByRec(r);

            Report report = new Report();
            String returnDate="";
            if (l.getReturnDate()!=null){
                returnDate = sdf.format(l.getReturnDate());
            }
            report.setProperty1(sdf.format(l.getLendDate()));
            report.setProperty2(returnDate);
            report.setProperty3("".join(", ",book.getAuthors()));
            report.setProperty4(book.getTitle());
            report.setProperty5(l.getCtlgNo());
            report.setProperty6(r.get_id());
            report.setProperty7(l.getDeadline() == null ? null : sdf.format(l.getDeadline()));
            report.setProperty8(l.getResumeDate() == null ? null : sdf.format(l.getResumeDate()));
            report.setProperty9(l.get_id());
            report.setProperty10(l.getLocation());
            retVal.add(report);
        }

        return retVal;
    }

    public Member getMebeByEcardCriteria(ElCardInfo memberInfo) {
        List<Member> searchedMembers = memberRep.findByJmbg(memberInfo.getJmbg());
        if (searchedMembers != null && searchedMembers.size() == 1) {
            return   searchedMembers.get(0);
        }
        searchedMembers = memberRep.findByDocNo(memberInfo.getDocNo());
        if (searchedMembers != null && searchedMembers.size() == 1) {
            return searchedMembers.get(0);
        }
        Date birthdayEnd = DateUtils.getEndOfDay(memberInfo.getBirthday());
        searchedMembers = memberRep.findByFirstNameAndLastNameAndBirthdayIc(
                memberInfo.getFirstName(), memberInfo.getLastName(), memberInfo.getBirthday(), birthdayEnd);
        if (searchedMembers != null && searchedMembers.size() == 1) {
            return searchedMembers.get(0);
        }
        searchedMembers = memberRep.findByFirstNameAndLastNameAndParentNameIc(
                memberInfo.getFirstName(), memberInfo.getLastName(), memberInfo.getParentName());
        if (searchedMembers != null && searchedMembers.size() == 1) {
            return searchedMembers.get(0);
        }
        return null;
    }

    public Member getAssignedMember(String ctlgNo){
        return memberRep.getMemberByReservationCtlgNo(ctlgNo, ReservationStatus.ASSIGNED_BOOK.name());
    }
}
