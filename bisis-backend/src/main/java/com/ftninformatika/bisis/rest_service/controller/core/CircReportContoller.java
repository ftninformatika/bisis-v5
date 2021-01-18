package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.circ.CorporateMember;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.UserCategRepository;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.date.DateUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/circ_report")
public class CircReportContoller {

    @Autowired MemberRepository memberRepository;
    @Autowired LendingRepository lendingRepository;
    @Autowired RecordsRepository recordsRepository;
    @Autowired CorporateMemberRepository corporateMemberRepository;
    @Autowired LocationRepository locationRepository;
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired UserCategRepository userCategRepository;
    @Autowired ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    private Logger log = Logger.getLogger(CircReportContoller.class);

    /**
     *
     * ukupan broj korisnika uclanjenih od pocetka godine do sada
     * ili prosledjenog datuma
     */
    @RequestMapping(value = "get_total_signed_from_start_of_year")
    public Integer getTotalSignedMembersFromStartOfYear( @RequestParam("location")String location
                                                        ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned
                                                        ,@RequestParam(value = "untilDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date untilDate) {
        Date today = DateUtils.getEndOfDay(new Date());
        if (untilDate != null) today = DateUtils.getEndOfDay(untilDate);
        Date yearStart = DateUtils.getYearStartFromDate(today);
        return memberRepository.getUserSignedCount(yearStart, today,  location, firstTimeSigned);
    }

    /**
     * Posetioci - svi razliciti korisnici koji su u datom periodu se
     * uclanili, zaduzivali, produzavali ili razduzivali
     *
     * Struktura posetilaca po: kategoriji, tipu clanstva i polu
     *
     */
    @RequestMapping(value = "get_visitor_structure_report")
    public Map<String, Map<String, Integer>> getVisitorStructureReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        Map<String, Map<String, Integer>> retVal = new HashMap<>();

        List<String> visitorsUserIds = memberRepository.getVisitorsUserIds(start, end, location, lib);
        List<Member> members = visitorsUserIds.stream().map(
                m -> memberRepository.getMemberByUserId(m)
        )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Integer> vrstaUclanjenja = new HashMap<>();
        Map<String, Integer> pol = new HashMap<>();
        Map<String, Integer> kategorija = new HashMap<>();

        members.stream().forEach(
                m -> {
                    String clanstvo = "Nepoznato";
                    if(m.getMembershipType() != null && m.getMembershipType().getDescription() != null)
                        clanstvo = m.getMembershipType().getDescription();
                    String mz = "Nepoznato";
                    if (m.getGender() != null)
                        mz = m.getGender();
                    String kat = "Nepoznato";
                    if (m.getUserCategory() != null && m.getUserCategory().getDescription() != null)
                        kat = m.getUserCategory().getDescription();

                    if(vrstaUclanjenja.containsKey(clanstvo))
                        vrstaUclanjenja.put(clanstvo, vrstaUclanjenja.get(clanstvo) + 1);
                    else
                        vrstaUclanjenja.put(clanstvo, 1);

                    if(pol.containsKey(mz))
                        pol.put(mz, pol.get(mz) + 1);
                    else
                        pol.put(mz, 1);

                    if(kategorija.containsKey(kat))
                        kategorija.put(kat, kategorija.get(kat) + 1);
                    else
                        kategorija.put(kat, 1);
                }
        );
        retVal.put("vrstaClanstva", vrstaUclanjenja);
        retVal.put("pol", pol);
        retVal.put("kategorija", kategorija);

        return retVal;
    }


    /**
     * Zbir strukture posetilaca na dnevnim nivoima
     * Broji jedinstveno samo za svaki dan
     */
    @RequestMapping(value = "get_visitor_structure_report_sum_daily")
    public Map<String, Map<String, Integer>> getVisitorStructureSumDailyReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        Map<String, Map<String, Integer>> retVal = new HashMap<>();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        for (Date date = startCalendar.getTime(); startCalendar.before(endCalendar); startCalendar.add(Calendar.DATE, 1), date = startCalendar.getTime()) {
            Map<String, Map<String, Integer>> dailyMap = getVisitorStructureReport(lib, date, date, location);
            for (String key : dailyMap.keySet()) {
                if (!retVal.containsKey(key)) {
                    retVal.put(key, dailyMap.get(key));
                }
                else {
                    dailyMap.get(key).forEach((k, v) -> retVal.get(key).merge(k, v, Integer::sum));
                }
            }
        }
        return retVal;
    }


        /**
         * slikovnice
         */
    /*PictureBooksReportCommand*/
    @RequestMapping(value = "get_picturebooks_report")
    public Report getPicturebooksReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        return memberRepository.getPictureBooksReport(start, end, location);
    }


    /**
     * podaci o broju knjiga sortirane po UDK grupam koje su zaduzili korisnici iz odredjenih kategorija
     * u datom periodu
     */
    /*CtgrUdkReportCommand*//*CtgrUdkGroupReportCommand*/
    @RequestMapping(value = "get_ctgr_udk_report")
    public Map<String, Report> getCtgrUdkReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        Map<String, Report> retVal = new HashMap<>();
        List<Lending> lendings = lendingRepository.getLendings(start, end, null, null, location);
        for (Lending l: lendings){
            String usrCateg = memberRepository.getMemberByUserId(l.getUserId()).getUserCategory().getDescription();
            Record record = recordsRepository.getRecordByPrimerakInvNum(l.getCtlgNo());
            if (record != null) {
                List<String> udks = record.getSubfieldsContent("675a");
                if (udks != null && !udks.isEmpty()) {
                    try {
                    List<String> udkgroups = udks.stream().map(i -> i.substring(0,1)).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
                    for (String udkgroup: udkgroups) {
                        if (!retVal.containsKey(usrCateg)) {
                            retVal.put(usrCateg, new Report());
                        }
                        Report r = retVal.get(usrCateg);

                            switch (Integer.parseInt(udkgroup)) {
                                case 0:
                                    r.setProperty10(increaseValue(r.getProperty10()));
                                    break;
                                case 1:
                                    r.setProperty1(increaseValue(r.getProperty1()));
                                    break;
                                case 2:
                                    r.setProperty2(increaseValue(r.getProperty2()));
                                    break;
                                case 3:
                                    r.setProperty3(increaseValue(r.getProperty3()));
                                    break;
                                case 4:
                                    r.setProperty4(increaseValue(r.getProperty4()));
                                    break;
                                case 5:
                                    r.setProperty5(increaseValue(r.getProperty5()));
                                    break;
                                case 6:
                                    r.setProperty6(increaseValue(r.getProperty6()));
                                    break;
                                case 7:
                                    r.setProperty7(increaseValue(r.getProperty7()));
                                    break;
                                case 8:
                                    r.setProperty8(increaseValue(r.getProperty8()));
                                    break;
                                case 9:
                                    r.setProperty9(increaseValue(r.getProperty9()));
                                    break;
                            }
                            //e.printStackTrace();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return retVal;
    }

    private String increaseValue(String s) {
        int i = 0;
        if (s != null) {
            i = Integer.parseInt(s);
        }
        i++;
        return String.valueOf(i);
    }

    @RequestMapping(value = "get_lend_return_udk_report")
    public Map<String, Report> getLendReturnUdkReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        Map<String, Report> retVal = new HashMap<>();
        start = DateUtils.getStartOfDay(start);
        end = DateUtils.getEndOfDay(end);
        List<String> lendingsCtlgNos = lendingRepository.getLendingsCtlgNo(start, end, null, null, location);
        List<String> returnCtlgNos = lendingRepository.getLendingsCtlgNo( null, null, start, end, location);
        Set<String> lendingsCtlgNosSet = new HashSet<>(lendingsCtlgNos);
        Set<String> retCtlgNosSet = new HashSet<>(returnCtlgNos);
        lendingsCtlgNos.sort(Comparator.naturalOrder());
        returnCtlgNos.sort(Comparator.naturalOrder());

        Map<String, Set<String>> udkRnsLended = new HashMap<>();
        Report reportLendedPrimerci = new Report();
        processLendReturnUdk(reportLendedPrimerci, lendingsCtlgNos, lendingsCtlgNosSet, udkRnsLended);
        Report reportLendNaslovi = fillLendReturnTitlesReport(udkRnsLended);

        Map<String, Set<String>> udkRnsReturned = new HashMap<>();
        Report reportReturnedPrimerci = new Report();
        processLendReturnUdk(reportReturnedPrimerci, returnCtlgNos, retCtlgNosSet, udkRnsReturned);
        Report reportReturnNaslovi = fillLendReturnTitlesReport(udkRnsReturned);

        reportLendedPrimerci.setProperty13(String.valueOf(getTotalLendReturnReport(reportLendedPrimerci)));
        reportLendNaslovi.setProperty13(String.valueOf(getTotalLendReturnReport(reportLendNaslovi)));
        reportReturnedPrimerci.setProperty13(String.valueOf(getTotalLendReturnReport(reportReturnedPrimerci)));
        reportReturnNaslovi.setProperty13(String.valueOf(getTotalLendReturnReport(reportReturnNaslovi)));

        retVal.put("izN", reportLendNaslovi);
        retVal.put("vrN", reportReturnNaslovi);
        retVal.put("iz", reportLendedPrimerci);
        retVal.put("vr", reportReturnedPrimerci);

        return retVal;
    }

    private int getTotalLendReturnReport(Report r) {
        int total = 0;
        total += parseInt(r.getProperty10());
        total += parseInt(r.getProperty1());
        total += parseInt(r.getProperty2());
        total += parseInt(r.getProperty3());
        total += parseInt(r.getProperty5());
        total += parseInt(r.getProperty6());
        total += parseInt(r.getProperty7());
        total += parseInt(r.getProperty8());
        total += parseInt(r.getProperty11());
        total += parseInt(r.getProperty9());
        return total;
    }

    private Report fillLendReturnTitlesReport(Map<String, Set<String>> mapSetRns) {
        Report retVal = new Report();
        retVal.setProperty10(String.valueOf(mapSetRns.get("0") != null ? mapSetRns.get("0").size() : 0));
        retVal.setProperty1(String.valueOf(mapSetRns.get("1") != null ? mapSetRns.get("1").size() : 0));
        retVal.setProperty2(String.valueOf(mapSetRns.get("2") != null ? mapSetRns.get("2").size() : 0));
        retVal.setProperty3(String.valueOf(mapSetRns.get("3") != null ? mapSetRns.get("3").size() : 0));
        retVal.setProperty5(String.valueOf(mapSetRns.get("5") != null ? mapSetRns.get("5").size() : 0));
        retVal.setProperty6(String.valueOf(mapSetRns.get("6") != null ? mapSetRns.get("6").size() : 0));
        retVal.setProperty7(String.valueOf(mapSetRns.get("7") != null ? mapSetRns.get("7").size() : 0));
        retVal.setProperty8(String.valueOf(mapSetRns.get("8d") != null ? mapSetRns.get("8d").size() : 0));
        retVal.setProperty11(String.valueOf(mapSetRns.get("8s") != null ? mapSetRns.get("8s").size() : 0));
        retVal.setProperty9(String.valueOf(mapSetRns.get("9") != null ? mapSetRns.get("9").size() : 0));
        return retVal;
    }


    private void processLendReturnUdk(Report report,List<String> ctgNos, Set<String> ctlgNosSet, Map<String, Set<String>> rnMap) {
        for (String inv: ctlgNosSet) {
            Record r = new Record();
            try {
                r = recordsRepository.getRecordByPrimerakInvNum(inv);
            }
            catch (Exception e){
                List<Record> records = recordsRepository.getRecordsByPrimerakInvNum(inv);
                log.info("Izvuceni zapisi: ");
                log.info(records.toString());
                if (records != null && records.size() >= 1)
                    r = records.get(0);
                e.printStackTrace();
            }
            if (r == null)
                continue;
            String rn = r.getSubfieldContent("001e");
            String udk = r.getSubfieldContent("675a");
            if (report == null || rn == null || udk == null)
                continue;
            if (udk.startsWith("0")) {
                if (rnMap.containsKey("0")) {
                    rnMap.get("0").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("0", set);
                }
                int primeraka = parseInt(report.getProperty10()) + Collections.frequency(ctgNos, inv);
                report.setProperty10(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("1")) {
                if (rnMap.containsKey("1")) {
                    rnMap.get("1").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("1", set);
                }
                int primeraka = parseInt(report.getProperty1()) + Collections.frequency(ctgNos, inv);
                report.setProperty1(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("2")) {
                if (rnMap.containsKey("2")) {
                    rnMap.get("2").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("2", set);
                }
                int primeraka = parseInt(report.getProperty2()) + Collections.frequency(ctgNos, inv);
                report.setProperty2(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("3")) {
                if (rnMap.containsKey("3")) {
                    rnMap.get("3").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("3", set);
                }
                int primeraka = parseInt(report.getProperty3()) + Collections.frequency(ctgNos, inv);
                report.setProperty3(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("5")) {
                if (rnMap.containsKey("5")) {
                    rnMap.get("5").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("5", set);
                }
                int primeraka = parseInt(report.getProperty5()) + Collections.frequency(ctgNos, inv);
                report.setProperty5(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("6")) {
                if (rnMap.containsKey("6")) {
                    rnMap.get("6").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("6", set);
                }
                int primeraka = parseInt(report.getProperty6()) + Collections.frequency(ctgNos, inv);
                report.setProperty6(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("7")) {
                if (rnMap.containsKey("7")) {
                    rnMap.get("7").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("7", set);
                }
                int primeraka = parseInt(report.getProperty7()) + Collections.frequency(ctgNos, inv);
                report.setProperty7(String.valueOf(primeraka));
                continue;
            }
            // 8 - domaca
            if (udk.startsWith("821.163.41")) {
                if (rnMap.containsKey("8d")) {
                    rnMap.get("8d").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("8d", set);
                }
                int primeraka = parseInt(report.getProperty8()) + Collections.frequency(ctgNos, inv);
                report.setProperty8(String.valueOf(primeraka));
                continue;
            }
            // 8 - strana
            if (udk.startsWith("8") && !udk.startsWith("821.163.41")) {
                if (rnMap.containsKey("8s")) {
                    rnMap.get("8s").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("8s", set);
                }
                int primeraka = parseInt(report.getProperty11()) + Collections.frequency(ctgNos, inv);
                report.setProperty11(String.valueOf(primeraka));
                continue;
            }
            if (udk.startsWith("9")) {
                if (rnMap.containsKey("9")) {
                    rnMap.get("9").add(rn);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(rn);
                    rnMap.put("9", set);
                }
                int primeraka = parseInt(report.getProperty9()) + Collections.frequency(ctgNos, inv);
                report.setProperty9(String.valueOf(primeraka));
                continue;
            }
        }
    }

    private int parseInt(String num) {
        if (num == null)
            return 0;
        return Integer.parseInt(num);
    }

    /**
     * zbirna statistika za period
     */
    @RequestMapping(value = "get_zb_statistic_report")
    public Report getZbStatisticReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(name = "location", required = false)String location) {
        Report r = new Report();
        Integer brUpisanihKorisnika = memberRepository.getUserSignedCount(start, end, location, false); //upisani clanovi Statistic1ReportCommand
        Integer brZaduzenihKorisnika = lendingRepository.getLendMemberCountDistinctByDate(start, end, location); //izdato clanova Statistic2ReportCommand
        Integer brRazduzenihKorisnika = lendingRepository.getReturnMemberCountDistinctByDate(start, end, location); //vraceno clanova Statistic3ReportCommand
        //aktivne vise ne brojimno
        //pasivne vise ne brojimo
        //ukupan broj korisnika je aktivni + pasivni
        Integer brZaduzenihKnjiga = lendingRepository.getLendingCountBy("lendDate", start, end, location, lib, false);//Statistic7ReportCommand
        Integer brRazduzenihKnjiga = lendingRepository.getLendingCountBy("returnDate", start, end, location, lib, false);//Statistic8ReportCommand
        Integer brProduzenihKnjiga = lendingRepository.getLendingCountBy("resumeDate", start, end, location, lib, false);//Statistic9ReportCommand
        Integer brUslZaduzenihKnjiga = lendingRepository.getLendingCountBy("lendDate", start, end, location, lib, true);//Statistic10ReportCommand
        Integer brUslRazduzenihKnjiga = lendingRepository.getLendingCountBy("returnDate", start, end, location, lib, true);//Statistic11ReportCommand
        Integer brUslProduzenihKnjiga = lendingRepository.getLendingCountBy("resumeDate", start, end, location, lib, true);//Statistic12ReportCommand

        r.setProperty1(String.valueOf(brUpisanihKorisnika));
        r.setProperty2(String.valueOf(brZaduzenihKorisnika));
        r.setProperty3(String.valueOf(brRazduzenihKorisnika));
        r.setProperty4(String.valueOf(brZaduzenihKnjiga));
        r.setProperty5(String.valueOf(brRazduzenihKnjiga));
        r.setProperty6(String.valueOf(brProduzenihKnjiga));
        r.setProperty7(String.valueOf(brUslZaduzenihKnjiga));
        r.setProperty8(String.valueOf(brUslRazduzenihKnjiga));
        r.setProperty9(String.valueOf(brUslProduzenihKnjiga));

        return r;
    }

    /** broj korisnika po polu koji su se uclanili u datom periodu
     */
    /*GenderReportCommand*/
    @RequestMapping(value = "get_gender_report")
    public List<Report> getGenderReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start
                                       ,@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end
                                       ,@RequestParam(name = "location", required = false)String location
                                       ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned) {
       return memberRepository.groupMemberByField(DateUtils.getStartOfDay(start),
               DateUtils.getEndOfDay(end), location, "gender", firstTimeSigned);
    }

    /**
     *  ukupan broj korisnika koji su se uclanili od pocetka godine
     *  ukupan broj korisnika koji su se uclanili u tom periodu
     */
    /*UsersNumberReportCommand*/
    @RequestMapping(value = "get_users_number_report")
    public Integer getUsersNumberReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start
                                       ,@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end
                                       ,@RequestParam(name = "location", required = false)String location
                                       ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned) {
        return memberRepository.getUserSignedCount(start, end, location, firstTimeSigned);
    }

    /**broj korisnika koji su se uclanili u datom periodu ali ne placaju clanarinu
     */
    /*FreeSigningReportCommand*/
    @RequestMapping(value = "get_free_signing_report")
    public Long getFreeSigningReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start
                                    ,@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end
                                    ,@RequestParam(name = "location", required = false)String location
                                    ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned) {
        return memberRepository.getFreeSigningMembersCount(start, end, location, firstTimeSigned);
    }

    /** broj korisnika iz odredjene vrste uclanjenja koji su se uclanili u datom periodu
     */
    /*MemberTypeReportCommand*/
    @RequestMapping(value = "get_mmbr_type_struct_report")
    public List<Report> getMmbrTypeStructReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start
                                               ,@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end
                                               ,@RequestParam(name = "location", required = false)String location
                                               ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned){
        return memberRepository.groupMemberByField(DateUtils.getStartOfDay(start),
                        DateUtils.getEndOfDay(end), location, "membershipType.description", firstTimeSigned);
    }

    /**broj korisnika koji pripadaju odredjenoj kategoriji i uclanili su se u datom periodu
     */
    /*CategoriaReportCommand*/
     @RequestMapping(value = "get_categoria_report")
    public List<Report> getCategoriaReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start
                                          ,@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end
                                          ,@RequestParam(name = "location", required = false)String location
                                          ,@RequestParam(value = "firstTimeSigned", required = false) boolean firstTimeSigned){
        return memberRepository.groupMemberByField(DateUtils.getStartOfDay(start),
                DateUtils.getEndOfDay(end), location, "userCategory.description", firstTimeSigned);
    }

    /**
     * izdate i vracene po jeziku
     */
    /*LendReturnLanguageReportCommand*/
    @RequestMapping(value = "get_lend_return_language_report")
    public Map<String, Report> getLendReturnLanguageReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        Map<String, Report> retVal = new HashMap<>();
        BoolQueryBuilder lQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder rQuery = QueryBuilders.boolQuery();
        List<String> lendCtlgnos = lendingRepository.getLendingsCtlgNo(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end)
                                                                                            , null, null, location);
        List<String> retCtlgnos = lendingRepository.getLendingsCtlgNo(null, null,
                                                                        DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), location);

        Iterable<ElasticPrefixEntity> lRec = elasticRecordsRepository.search(lQuery.filter(QueryBuilders.termsQuery("prefixes.IN", lendCtlgnos)));
        Iterable<ElasticPrefixEntity> rRec = elasticRecordsRepository.search(rQuery.filter(QueryBuilders.termsQuery("prefixes.IN", retCtlgnos)));

        for (String ctl: lendCtlgnos){

            ElasticPrefixEntity ep = ElasticUtility.getEPEFromCtlgno(ctl, lRec);
            if (ep == null){
                System.out.println("Lend problem ctlgno: " + ctl);
                continue;
            }
            if (ep.getPrefixes().get("101a") != null && ep.getPrefixes().get("101a").size() > 0){
                for (String lan: ep.getPrefixes().get("101a")){
                    if (retVal.containsKey(lan)){
                        Report r = retVal.get(lan);
                        r.setProperty1((Integer.valueOf(r.getProperty1()) + 1 ) + "");
                        retVal.put(lan, r);
                    }
                    else {
                        Report r = new Report();
                        r.setProperty1("1");
                        retVal.put(lan, r);
                    }
                    //da vrati prvi jezik
                    break;
                }
            }
        }

        for (String ctl: retCtlgnos){
            ElasticPrefixEntity ep = ElasticUtility.getEPEFromCtlgno(ctl , rRec);
            if (ep == null){
                System.out.println("Return problem ctlgno: " + ctl);
                continue;
            }
            if (ep.getPrefixes().get("101a") != null && ep.getPrefixes().get("101a").size() > 0){
                for (String lan: ep.getPrefixes().get("101a")){
                    if (retVal.containsKey(lan)){
                        Report r = retVal.get(lan);
                        if(r.getProperty2() == null)
                            r.setProperty2("1");
                        else
                            r.setProperty2((Integer.valueOf(r.getProperty2()) + 1) + "");
                        retVal.put(lan, r);
                    }
                    else {
                        Report r = new Report();
                        r.setProperty2("1");
                        retVal.put(lan, r);
                    }
                    break;
                }
            }
        }

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
        Map<String, Integer> signedMap = memberRepository.getLibrarianSignedCount(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), location);

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
            LibrarianDB m = librarianRepository.getByUsername(entry.getKey() + "@" + lib);
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
    public List<Report> getBestBookReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location) {
        return getBestBookUdkReport(lib, start, end, null, location);
    }

    @RequestMapping(value = "get_best_book_udk")
    public List<Report> getBestBookUdkReport(@RequestHeader("Library") String lib, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("udk")String udk, @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();

        List<Object> l = lendingRepository.getGroupByForLendingsBetweenDate(start, end, location, "ctlgNo", "lendedCount", "lendedCount", "lendDate", null);
        //ctlgno:broj
        Map<String, Integer> allLendings = new HashMap<>();
        if (l != null){
            for(Object o: l){
                if(o instanceof LinkedHashMap){
                    String ctlgno = ((LinkedHashMap) o).get("ctlgNo").toString();
                    int count = Integer.parseInt(((LinkedHashMap) o).get("lendedCount").toString());
                    allLendings.put(ctlgno, count);
                }
            }
        }

        //mongoId(record):broj
        Map<String, Integer> resultMap = new HashMap<>();

        BoolQueryBuilder pp = QueryBuilders.boolQuery();
        QueryBuilder query;
        if (udk != null) {
            QueryBuilder pf = ElasticUtility.buildQbForField(udk, "DC");
            pp.must(pf);
            pp.filter(QueryBuilders.termsQuery("prefixes.IN",allLendings.keySet()));
            query = pp;
        } else {
            query = QueryBuilders.termsQuery("prefixes.IN", allLendings.keySet());
        }

        Pageable pageable = PageRequest.of(0, 5000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build();

        CloseableIterator<ElasticPrefixEntity> streamEe = elasticsearchTemplate.stream(searchQuery, ElasticPrefixEntity.class);
        while (streamEe.hasNext()) {
            ElasticPrefixEntity ep = streamEe.next();
            if (ep.getPrefixes().get("IN") != null && ep.getPrefixes().get("IN").size() > 0) {
                for (String in : ep.getPrefixes().get("IN")) {
                    if (allLendings.keySet().contains(in)) {
                        if (resultMap.containsKey(ep.getId())) {
                            resultMap.put(ep.getId(), resultMap.get(ep.getId()) + allLendings.get(in));
                        } else {
                            resultMap.put(ep.getId(), allLendings.get(in));
                        }
                    }
                }
            }
        }
        List<Map.Entry<String, Integer>> sortedResults  = resultMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        sortedResults.forEach(
                entry -> {
                    Report r = new Report();
                    Record rec = recordsRepository.findById(entry.getKey()).get();
                    RecordPreview pr = new RecordPreview();
                    pr.init(rec);
                    r.setProperty1(pr.getTitle());
                    r.setProperty2(pr.getAuthor());
                    r.setProperty3(String.valueOf(entry.getValue()));
                    retVal.add(r);
                }
        );

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
        retVal = memberRepository.getSignedCorporateMembers(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), institution, location);

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

        members = memberRepository.getSignedMembers(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), location, "lastName");
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
                    if (m.getMembershipType() != null) r.setProperty10(m.getMembershipType().getDescription());
                    else r.setProperty10(null);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        lendings = lendingRepository.getLenignsWithAnyActivityOnDate(date, date, location);

        List<String> userIds = lendings.stream().map(l -> l.getUserId()).collect(Collectors.toList());
        Map<String, Member> members = memberRepository.findByUserIdIn(userIds).stream().collect(Collectors.toMap(Member::getUserId, member -> member));

        lendings.forEach(
                l -> {
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
        //sortirano po userId
        retVal.sort(Comparator.comparing(Report::getProperty1));
        return retVal;
    }


    /**
     * podaci o korisniku koji su se uclanili datog dana po vrsti uclanjenja
     */
    /*MmbrTypeReportCommand*/
    @RequestMapping( value = "get_mmbr_type_report")
    public List<Report> getMmbrTypeReport( @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Member> members = memberRepository.getSignedMembers(DateUtils.getStartOfDay(date), DateUtils.getEndOfDay(date), location, "membershipType.description");
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
        List<Member> members = memberRepository.getSignedMembers(DateUtils.getStartOfDay(date), DateUtils.getEndOfDay(date), location, "userId");
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
            lendings = lendingRepository.findByLendDateBetweenAndCtlgNoAndLocation(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), ctlgNo, location);
        else
            lendings = lendingRepository.findByLendDateBetweenAndCtlgNo(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), ctlgNo);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
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

    /**Podaci o korisniku koji su se uclanili datog dana po kategorijama*/
    /*UserCategReportCommand*/
    @RequestMapping(value = "/get_members_with_categories", method = RequestMethod.GET)
    public List<Report> getMembersWithCategory(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = memberRepository.getSignedMembers(DateUtils.getStartOfDay(start), DateUtils.getEndOfDay(end), location, "userCategory.description");
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

    /**
    uclanjeni korisnici sa tipom uclanjenja
     */
    /*MmbrTypeReportCommand*/
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

    /**
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

    /**uclanjeni korisnici preko nekog korporativnog clana*/
    /*MemberByGroupReportCommand*/
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

    /**Broj uclanjenih korisnika grupisanih po tipu*/
    /*MemberTypeReportCommand*/
    @RequestMapping(value = "/group_by_membership_type", method = RequestMethod.GET)
    public List<Report> groupByMembershipType(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location){
        List<Report> reports = memberRepository.groupMemberByField(start, end, location, "membershipType.description", false);
        return reports;
    }

    /**istorija zaduzenja clana */
    /*LendingHistoryReportCommand*/
    @RequestMapping(value = "/get_lending_history", method = RequestMethod.GET)
    public List<Report> getLendingHistory(@RequestParam("memberNo") String memberNo, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date end, @RequestParam("location") String location){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        if (location==null ||location.equals("")){
            lendings = lendingRepository.findLendingsByUserIdAndLendDateBetween(memberNo,start,end);
        }else{
            lendings = lendingRepository.findLendingsByUserIdAndLendDateBetweenAndLocation(memberNo,start,end,location);
        }
        lendings.sort(Comparator.comparing(d -> d.getLendDate()));
        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("dd.MM.yyyy");

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

    /**istorija zaduzenja clana */
    @RequestMapping(value = "/get_lending_history_full", method = RequestMethod.GET)
    public List<Report> getLendingHistoryFull(@RequestParam("memberNo") String memberNo){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        lendings = lendingRepository.findByUserId(memberNo);
        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("dd.MM.yyyy");

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

    /**SubMemberBookReportCommand*/
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
