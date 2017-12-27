package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.CorporateMember;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.utils.date.DateUtils;
import ma.glasnost.orika.MapEntry;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/circ_report")
public class CircReportContoller {

    @Autowired MemberRepository memberRepository;

    @Autowired LendingRepository lendingRepository;

    @Autowired RecordsRepository recordsRepository;

    @Autowired MembershipTypeRepository membershipTypeRepository;

    @Autowired CorporateMemberRepository corporateMemberRepository;

    @Autowired LocationRepository locationRepository;

    @Autowired LibrarianRepository librarianRepository;

    @Autowired ElasticRecordsRepository elasticRecordsRepository;



    @RequestMapping(value = "get_zb_statistic_report")
    public List<Report> getZbStatisticReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        Long signedCount = memberRepository.getUserSignedCount(start,end, location);
        Long lendCount = lendingRepository.getLendCount(start,end,location);
        Long returnCount = lendingRepository.getReturnCount(start, end, location);
        Long activeUsersCount = lendingRepository.getActiveVisitorsCount(start, end, location);



        return retVal;
    }

        /**
         * aktivni korisnici su oni koji su:
         * 1. u datom periodu su zaduzili knjigu i nisu je vratili ili su je vratili posle datuma zaduzenja(to moze
         * biti i sutradan jer se aktivni korisnici racunaju na jedan dan)
         *
         * 2. u datom periodu su produzili zaduzenje i nisu vratili knjigu ili su knjigu produzili posle
         * datuma zaduzenja (najranije sutradan)
         *
         */
    /*ActiveVisitorsReportCommand*/
    @RequestMapping(value = "get_active_visitors_report")
    public List<Report> getActiveVisitorsReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Object> lendings = lendingRepository.getActiveVisitors(start, end, location);
        Map<String, Integer> countUsrCat = new HashMap<>();
        for (Object o: lendings){
            if(o instanceof LinkedHashMap){
                String userId = ((LinkedHashMap<String, String>)o).get("userId");
                Integer count = ((LinkedHashMap<String, Integer>)o).get("count");
                String ctg = memberRepository.getMemberByUserId(userId).getUserCategory().getDescription();
                if(countUsrCat.containsKey(ctg)){
                    countUsrCat.put(ctg, countUsrCat.get(ctg) + count);
                }
                else {
                    countUsrCat.put(ctg, count);
                }
            }
        }
        countUsrCat.keySet().forEach(
                k -> {
                    Report r = new Report();
                    r.setProperty1(k);
                    r.setProperty21(countUsrCat.get(k));
                    retVal.add(r);
                }
        );

        return retVal;
    }

        /**
         * najcitanije knjige po UDK
         */
    /*BestBookUDKReportCommand, FilterManager.bestBookUDK()*/
    @RequestMapping(value = "get_best_book_udk")
    public List<Report> getBestBookUdk(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("udk")String udk, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Object> finalResults = new ArrayList<>();
        HashMap<String, Integer> retMap = new HashMap<>();
        QueryBuilder pp = null;
        if(udk != null && !udk.equals("")) {
            pp = QueryBuilders.matchPhrasePrefixQuery("prefixes.DC", udk);

            Iterable<ElasticPrefixEntity> ee = elasticRecordsRepository.search(pp);
            //svi ctlgNo koji su iznajmljeni u zadatom periodu - iz ove kolekcije brojimo
            List<String> lendResultClgNos = lendingRepository.getLendingsCtlgNo(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), null, null, location);
            //zbog brzine contains() metode - iz ove kolekcije proveravamo
            Set<String> setCtlgNos = new HashSet<>(lendResultClgNos);
            Collections.sort(lendResultClgNos);
            ee.forEach(
                    e -> {
                        if (e.getPrefixes().get("IN") != null) {
                            for (String in : e.getPrefixes().get("IN")) {
                                if(setCtlgNos.contains(in) && !retMap.containsKey(in)){
                                    retMap.put(e.getId(), Collections.frequency(lendResultClgNos, in));
                                }
                            }
                        }
                    }
            );
        }
        if(retMap.size() > 0){
            //osakatimo mapu na 20 najcitanijih
            finalResults = retMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(20).collect(Collectors.toList());
            for (Object o: finalResults){
                if(o instanceof HashMap.Entry){
                    Report r = new Report();
                    Record rec = recordsRepository.findOne(((HashMap.Entry)o).getKey().toString());
                    RecordPreview pr = new RecordPreview();
                    pr.init(rec);
                    r.setProperty21(Long.parseLong((((HashMap.Entry)o).getValue().toString())));
                    r.setProperty1(pr.getTitle());
                    r.setProperty2(pr.getAuthor());
                    retVal.add(r);

                }
            }
        }
        return retVal;
    }

    /** broj korisnika po polu koji su se uclanili u datom periodu
     */
    /*GenderReportCommand*/
    @RequestMapping(value = "get_gender_report")
    public List<Report> getGenderReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
       return memberRepository.groupMemberByField(DateUtils.getYesterday(DateUtils.getEndOfDay(start)),
               DateUtils.getEndOfDay(end), location, "gender");
    }

        /**
         *  ukupan broj korisnika koji su se uclanili od pocetka godine
         *  ukupan broj korisnika koji su se uclanili u tom periodu
         */
    /*UsersNumberReportCommand*/
    @RequestMapping(value = "get_users_number_report")
    public Long getUsersNumberReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        return memberRepository.getUserSignedCount(start, end, location);
    }

    /**broj korisnika koji su se uclanili u datom periodu ali ne placaju clanarinu
     */
    /*FreeSigningReportCommand*/
    @RequestMapping(value = "get_free_signing_report")
    public Long getFreeSigningReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        return memberRepository.getFreeSigningMembersCount(start, end, location);
    }

    /** broj korisnika iz odredjene vrste uclanjenja koji su se uclanili u datom periodu
     */
    /*MemberTypeReportCommand*/
    @RequestMapping(value = "get_mmbr_type_struct_report")
    public List<Report> getMmbrTypeStructReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location){
        return memberRepository.groupMemberByField(DateUtils.getYesterday(DateUtils.getEndOfDay(start)),
                        DateUtils.getEndOfDay(end), location, "membershipType.description");
    }

        /**broj korisnika koji pripadaju odredjenoj kategoriji i uclanili su se u datom periodu
         */
    /*CategoriaReportCommand*/
     @RequestMapping(value = "get_categoria_report")
    public List<Report> getCategoriaReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location){
        return memberRepository.groupMemberByField(DateUtils.getYesterday(DateUtils.getEndOfDay(start)),
                DateUtils.getEndOfDay(end), location, "userCategory.description");
    }

        /**
         * izdate i vracene po jeziku
         */
    /*LendReturnLanguageReportCommand*///TODO-optimizovati, ne valja ovako nikako
    @RequestMapping(value = "get_lend_return_language_report")
    public List<Report> getLendReturnLanguageReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        List<Lending> lendings = lendingRepository.getLenignsWithAnyActivityOnDate(DateUtils.getYesterday(DateUtils.getEndOfDay(start)), DateUtils.getEndOfDay(end), location);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        lendings.forEach(
                l -> {
                    Report r = new Report();
                    if (l.getCtlgNo() != null)
                        r.setProperty1(l.getCtlgNo());
                    if (l.getLendDate() != null)
                        r.setProperty2(formatter.format(l.getLendDate()));
                    if (l.getReturnDate() != null)
                        r.setProperty3(formatter.format(l.getReturnDate()));
                    if (l.getResumeDate() != null)
                        r.setProperty4(formatter.format(l.getResumeDate()));
                    retVal.add(r);
                }
        );

        return retVal;
    }
        /**
         * blokirane korisnike
         */
    /*BlockedReportCommand*/
    @RequestMapping(value = "get_blocked_report")
    public List<Report> getBlockedReport(@RequestParam(name = "location", required = false)String location){
        List<Report> retVal = new ArrayList<>();

        List<Member> members = memberRepository.getBlockedMembers(location);
        members.forEach(
                m -> {
                    Report r = new Report();
                    if (m.getUserId() != null)
                        r.setProperty1(m.getUserId());
                    if(m.getFirstName() != null)
                        r.setProperty2(m.getFirstName());
                    if(m.getLastName() != null)
                        r.setProperty3(m.getLastName());
                    if(m.getBlockReason() != null)
                        r.setProperty4(m.getBlockReason());
                    retVal.add(r);
                }
        );

        return retVal;
    }


    /**
     * broj uclanjenih korisnika po bibliotekarima
     * broj zaduzenja po bibliotekarima
     * broj produzenja po bibliotekarima
     * broj razduzenja po bibliotekarima
     *
     */
    /*LibrarianStatistic1Command LibrarianStatistic2Command LibrarianStatistic4Command LibrarianStatistic3Command*/
    @RequestMapping(value = "get_librarian_statistic_report")
    public List<Report> getLibrarianStatisticReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        Map<String, Report> reportMap = lendingRepository.getLibrarianStatistic(start, end, location);


        Map<String, Integer> signedMap = memberRepository.getLibrarianSignedCount(DateUtils.getYesterday(DateUtils.getEndOfDay(start)), DateUtils.getEndOfDay(end), location);


        for (Map.Entry<String, Integer> entry : signedMap.entrySet()){
            if(reportMap.containsKey(entry.getKey())){
                reportMap.get(entry.getKey()).setProperty5(String.valueOf(entry.getValue()));
            }
            else {
                Report r = new Report();
                r.setProperty1(entry.getKey());
                r.setProperty5(String.valueOf(entry.getValue()));
            }
        }

        for (Map.Entry<String, Report> entry : reportMap.entrySet()){
            LibrarianDTO m = librarianRepository.getByUsername(entry.getKey() + "@" + lib);
            Report r = new Report();
            String fullName = "";
            if (m != null) {
                if (m.getIme() != null)
                    fullName = fullName + m.getIme();
                if (m.getPrezime() != null)
                    fullName = fullName + " " + m.getPrezime();

                r.setProperty1(fullName);
            }
            else
                r.setProperty1(entry.getKey());
            r.setProperty2(entry.getValue().getProperty2());
            r.setProperty3(entry.getValue().getProperty3());
            r.setProperty4(entry.getValue().getProperty4());
            r.setProperty5(entry.getValue().getProperty5());

            retVal.add(r);
        }


        return retVal;
    }

        /**
         * najcitanije knjige
         */
    /*BestBookReportCommand*/
    @RequestMapping(value = "get_best_book_report")
    public List<Report> getBestBookReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        List<Object> l = lendingRepository.getGroupByForLendingsBetweenDate(start, end, location,
                                                                "ctlgNo", "lendedCount", "lendedCount", "lendDate", 20);
        if (l != null){
            for(Object o: l){
                if(o instanceof LinkedHashMap){
                    Report r = new Report();
                    r.setProperty1(((LinkedHashMap) o).get("ctlgNo").toString());
                    r.setProperty2(((LinkedHashMap) o).get("lendedCount").toString());
                    retVal.add(r);
                }
            }
        }

        return retVal;
    }

        /**
         * citaoci koji su zaduzili najvise knjiga
         */
    /*BestReaderReportCommand*/
    @RequestMapping(value = "get_best_reader_report")
    public List<Report> getBestReaderReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        List<Object> l = lendingRepository.getGroupByForLendingsBetweenDate(start, end, location,
                                                                "userId", "booksRed", "booksRed", "lendDate", 20);
        if (l != null) {
            for (Object o : l) {
                if (o instanceof LinkedHashMap) {
                    Report r = new Report();
                    Member m = memberRepository.getMemberByUserId(((LinkedHashMap) o).get("userId").toString());
                    r.setProperty1(m.getUserId());
                    r.setProperty2(m.getFirstName());
                    r.setProperty3(m.getLastName());
                    r.setProperty4(((LinkedHashMap) o).get("booksRed").toString());
                    retVal.add(r);
                }
            }
        }

        return retVal;
    }

        /**
         * podaci o individualnim clanovima koji pripadaju kolektivnim clanovima
         */
    /*MemberByGroupReportCommand*/
    @RequestMapping(value = "get_member_by_group_report")
    public List<Member> getMemberByGroupReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "institution")String institution, @RequestParam(name = "location", required = false)String location) {
        List<Member> retVal = null;
        retVal = memberRepository.getSignedCorporateMembers(DateUtils.getYesterday(DateUtils.getEndOfDay(start)), DateUtils.getEndOfDay(end), institution, location);

        return retVal;
    }

        /**
         * spisak kolektivnih clanova
         */
    /*GroupsReportCommand*/
    @RequestMapping( value = "get_group_report")
    public List<CorporateMember> getGroupReport(@RequestHeader("Library") String lib, @RequestParam(name = "location", required = false)String location/*coder_id*/) {
        List<CorporateMember> retVal = null;


        retVal = corporateMemberRepository.getCoders(lib); //sifarnik - uzimamo iz RequestHeader-a biblioteku
        if (location != null && !location.equals("")) {
            String locationCode = locationRepository.getByDescriptionAndLibrary(location, lib).getCoder_id(); //prosledjuje se description lokacije, pa da iscupamo kod
                                                                                                              //pretpostavka da ne postoji vise lokacija sa identicnim imenom unutar
                                                                                                              //jedne biblioteke
            retVal = retVal.stream()
                    .filter(i -> i.getUserId() != null && i.getUserId().length() > 3 && i.getUserId().substring(0, 2).equals(locationCode))
                    .collect(Collectors.toList());
        }

        return retVal;
    }

    /**
     * podaci o uclanjenim korisnicima na dati datum
     */
    /*MemberBookReportCommand*//*SubMemberBookReportCommand*/
    @RequestMapping( value = "get_member_book_report")
    public List<Report> getMemberBookReport (@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Member> members = null;

        members = memberRepository.getSignedMembers(DateUtils.getYesterday(DateUtils.getEndOfDay(start)), DateUtils.getEndOfDay(end), location, "lastName");
        members.forEach(
                m -> {
                    Report r = new Report();
                    r.setProperty1(m.getUserId());
                    r.setProperty2(m.getFirstName());
                    r.setProperty3(m.getLastName());
                    r.setProperty4(m.getAddress());
                    r.setProperty5(m.getZip());
                    r.setProperty6(m.getCity());
                    r.setProperty7(m.getDocNo());
                    r.setProperty8(m.getDocCity());
                    r.setProperty9(m.getJmbg());
                    r.setProperty10(m.getMembershipType().getDescription());
                    r.setProperty12(m.getSignings().get(0).getCost());
                    retVal.add(r);
                }
        );
        return retVal;
    }

    /** podaci o korisnicima koji su tog dana zaduzili knjigu, produzili ili vratili knjigu
     *
     */
    /*VisitorsReportCommand*/
    @RequestMapping( value = "get_visitors_report")
    public List<Report> getVisitorsReport(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location){
        List<Report> retVal = new ArrayList<>();
        List<Lending> lendings = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        lendings = lendingRepository.getLenignsWithAnyActivityOnDate(date, date, location);

        List<String> userIds = lendings.stream().map(l -> l.getUserId()).collect(Collectors.toList());
        Map<String, Member> members = memberRepository.findByUserIdIn(userIds).stream().collect(Collectors.toMap(Member::getUserId, member -> member));

        lendings.forEach(
                l -> {
                    //Member m = memberRepository.getMemberByUserId(l.getUserId()); ovako je radilo jako sporo i response time je bio preveliki pa je pucalo za pojedine datume
                    Member m = members.get(l.getUserId());
                    Report r = new Report();
                    r.setProperty1(m.getUserId());
                    r.setProperty2(m.getFirstName());
                    r.setProperty3(m.getLastName());
                    r.setProperty4(m.getAddress());
                    r.setProperty5(m.getCity());
                    r.setProperty6(m.getZip());
                    r.setProperty7(m.getDocNo());
                    r.setProperty8(m.getDocCity());
                    r.setProperty9(m.getJmbg());
                    r.setProperty10(l.getLocation());
                    if (l.getReturnDate() != null)
                        r.setProperty11(formatter.format(l.getReturnDate()));
                    if (l.getLendDate() != null)
                        r.setProperty13(formatter.format(l.getLendDate()));
                    r.setProperty14(l.getCtlgNo());
                    retVal.add(r);
                }
        );
        return retVal;
    }


    /**
     * podaci o korisniku koji su se uclanili datog dana po vrsti uclanjenja
     */
    /*MmbrTypeReportCommand*/
    @RequestMapping( value = "get_mmbr_type_report")
    public List<Report> getMmbrTypeReport( @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Member> members = null;

        members = memberRepository.getSignedMembers(DateUtils.getYesterday(DateUtils.getEndOfDay(date)), DateUtils.getEndOfDay(date), location, "membershipType.description");
        for (Member m: members){
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getZip());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            r.setProperty10(m.getLibrarianForSigningDate(date));
            r.setProperty11(m.getRecieptForSigingDate(date));
            r.setProperty12(m.getCostForSigningDate(date));
            r.setProperty13(m.getMembershipType().getDescription());
            retVal.add(r);
        }
        return retVal;
    }

        /**
         * podatke o clanu za prosledjen datum uclanjenja i ogranak(opciono) sortirano po bibliotekarima
         */
    /*LibrarianReportCommand*/
    @RequestMapping( value = "get_librarian_report")
    public List<Report> getLibrarianReport( @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location){
        List<Report> retVal = new ArrayList<>();
        List<Member> members = null;


        members = memberRepository.getSignedMembers(DateUtils.getYesterday(DateUtils.getEndOfDay(date)), DateUtils.getEndOfDay(date), location, "userId");
        members.sort(Comparator.comparing(m -> m.getSignings().get(0).getLibrarian()));//sortira po bibliotekaru

        for (Member m: members){
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getZip());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            r.setProperty10(m.getLibrarianForSigningDate(date));
            r.setProperty11(m.getRecieptForSigingDate(date));
            r.setProperty12(m.getCostForSigningDate(date));
            r.setProperty13(m.getMembershipType().getDescription());
            retVal.add(r);
        }

        return retVal;
    }

    /**
     * id- jeve korisnika koji su zaduzivali knjigu sa prosledjenim ctlgno u prosledjenom periodu
     * datum zaduzenja, datum razduzenja
     */
    /*BookHistoryReportCommand*//*BookHistoryReportCommand*/
    @RequestMapping( value = "get_book_history_report")
    public List<Report> getBookHistoryReport( @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(value = "ctlgno") String ctlgNo, @RequestParam(name = "location", required = false)String location/*desc*/){
        List<Report> retVal = new ArrayList<>();
        List<Lending> lendings = null;
        if (location != null && location != "")
            lendings = lendingRepository.findByLendDateBetweenAndCtlgNoAndLocation(start, end, ctlgNo, location);
        else
            lendings = lendingRepository.findByLendDateBetweenAndCtlgNo(start, end, ctlgNo);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Lending l: lendings){
            Report r = new Report();
            r.setProperty1(l.getUserId());
            if(l.getLendDate() != null)
                r.setProperty2(formatter.format(l.getLendDate()));
            if(l.getReturnDate() != null)
                r.setProperty3(formatter.format(l.getReturnDate()));
            retVal.add(r);
        }

        return retVal;
    }

    /**
     * ukupan broj korisnika koji su se uclanili od pocetka godine
     * ukupan broj korisnika koji su se uclanili u tom periodu
     */
     /*UsersNumberReportCommand*//*Statistic1ReportCommand*/

    @RequestMapping(value = "/get_number_of_members_by_period", method = RequestMethod.GET)
    public int getNumberOfMembersByPeriod(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        int num = 0;
        if ((location == null) || (location.equals(""))) {
            num = memberRepository.getNumberOfMembersByPeriod(start, end, location);
        } else {
            num = memberRepository.getNumberOfMembersByPeriod(start, end);
        }
        return num;
    }

    /**
     * podaci o korisniku koji su se uclanili datog dana po kategorijama
     */
/*UserCategReportCommand*/

    @RequestMapping(value = "/get_members_with_categories", method = RequestMethod.GET)
    public List<Report> getMembersWithCategory(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = memberRepository.getSignedMembers(DateUtils.getYesterday(DateUtils.getEndOfDay(start)), DateUtils.getEndOfDay(end), location, "userCategory.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getUserCategory().getDescription());
            r.setProperty5(m.getAddress());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getZip());
            r.setProperty8(m.getDocNo());
            r.setProperty9(m.getDocCity());
            r.setProperty10(m.getJmbg());
            r.setProperty11(m.getSignings().get(0).getLibrarian());
            r.setProperty13(m.getSignings().get(0).getReceipt());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty12(0.0);
            else {
                r.setProperty12(m.getSignings().get(0).getCost());
            }
            reports.add(r);
        }
        return reports;
    }

    /*
    uclanjeni korisnici sa tipom uclanjenja*/ /*MmbrTypeReportCommand*/

    @RequestMapping(value = "/get_members_with_member_type", method = RequestMethod.GET)
    public List<Report> getMembersWithMemberType(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = memberRepository.getSignedMembers(start, end, location, "membershipType.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getMembershipType().getDescription());
            r.setProperty2(m.getUserId());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }

    /*
    uclanjeni korisnici sortitani po prezimenu
     */
    /*MemberBookReportCommand*/

    @RequestMapping(value = "/get_signed_members", method = RequestMethod.GET)
    public List<Report> getSignedMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = memberRepository.getSignedMembers(start, end, location, "lastName");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getCity());
            r.setProperty6(m.getZip());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }

    /*uclanjeni korisnici preko nekog korporativnog clana*//*MemberByGroupReportCommand*/

    @RequestMapping(value = "/get_signed_corporateMembers", method = RequestMethod.GET)
    public List<Report> getSignedCorporateMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location, @RequestParam("company") String company) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = memberRepository.getSignedCorporateMembers(start, end, location, company);
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            reports.add(r);
        }
        return reports;
    }
/*broj uclanjenih korisnika grupisanih po tipu*/ /*MemberTypeReportCommand*/

    @RequestMapping(value = "/group_by_membership_type", method = RequestMethod.GET)
    public List<Report> groupByMembershipType(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location){
        List<Report> reports = memberRepository.groupMemberByField(start,end,location, "membershipType.description");
        return reports;
    }

    /*istorija zaduzenja clana *//*LendingHistoryReportCommand*/

    @RequestMapping(value = "/get_lending_history", method = RequestMethod.GET)
    public List<Report> getLendingHistory(@RequestParam("memberNo") String memberNo, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date end, @RequestParam("location") String location){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        if (location==null ||location.equals("")){
            lendings = lendingRepository.findLendingsByUserIdAndLendDateBetween(memberNo,start,end);
        }else{
            lendings = lendingRepository.findLendingsByUserIdAndLendDateBetweenAndLocation(memberNo,start,end,location);
        }
        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

       for(Lending l:lendings){
            r = recordsRepository.getRecordByPrimerakInvNum(l.getCtlgNo());
            rp.init(r);
            Report report = new Report();
            String returnDate="";
            if (l.getReturnDate()!=null){
                returnDate = sdf.format(l.getReturnDate());
            }
            report.setProperty1(sdf.format(l.getLendDate()));
            report.setProperty2(returnDate);
            report.setProperty3(rp.getAuthor());
            report.setProperty4(rp.getTitle());
            report.setProperty5(l.getCtlgNo());
            reports.add(report);
       }

        return reports;
    }

       /*istorija zaduzenja clana */

    @RequestMapping(value = "/get_lending_history_full", method = RequestMethod.GET)
    public List<Report> getLendingHistoryFull(@RequestParam("memberNo") String memberNo){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        lendings = lendingRepository.findByUserId(memberNo);

        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

        for(Lending l:lendings){
            r = recordsRepository.getRecordByPrimerakInvNum(l.getCtlgNo());
            rp.init(r);
            Report report = new Report();
            String returnDate="";
            if (l.getReturnDate()!=null){
                returnDate = sdf.format(l.getReturnDate());
            }
            report.setProperty1(sdf.format(l.getLendDate()));
            report.setProperty2(returnDate);
            report.setProperty3(rp.getAuthor());
            report.setProperty4(rp.getTitle());
            report.setProperty5(l.getCtlgNo());
            reports.add(report);
        }

        return reports;
    }
/*SubMemberBookReportCommand*/
    @RequestMapping(value = "/get_cost_for_user", method = RequestMethod.GET)
    public List<Report> getCostForUser(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports=new ArrayList<Report>();
        List<Member> members = memberRepository.getSignedMembers(start, end, location, "membershipType.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getMembershipType().getDescription());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }
    }
