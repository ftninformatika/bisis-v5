package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.cards.ReportCore;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.opac2.books.Item;
import com.ftninformatika.bisis.opac2.search.*;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.rest_service.controller.core.CodersController;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.Helper;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author badf00d21  25.7.19.
 */
@Service
public class OpacSearchService {

    @Autowired RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired BookCommonRepository bookCommonRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
    @Autowired LocationRepository locationRepository;
    @Autowired SublocationRepository sublocationRepository;
    @Autowired ItemAvailabilityRepository itemAvailabilityRepository;
    @Autowired ItemStatusRepository itemStatusRepository;
    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
//    TODO- refactor this at some point (don't import controllers in service layer)
    @Autowired CodersController codersController;
    private Logger log = Logger.getLogger(OpacSearchService.class);

    public PageImpl<List<Book>> searchBooks(ResultPageSearchRequest searchRequest, String lib, Integer pageNumber, Integer pageSize) {
        List<Book> retVal = new ArrayList<>();

        if (searchRequest == null || searchRequest.getSearchModel() == null) return null;

        int page = 0;
        int pSize = 10;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;

        BoolQueryBuilder query = ElasticUtility.makeQuery(searchRequest.getSearchModel());

        List<ItemStatus> itemStatusList = itemStatusRepository.getCoders(lib);
        itemStatusList = itemStatusList.stream().filter(ItemStatus::isShowable).collect(Collectors.toList());

        if (searchRequest.getOptions() != null && searchRequest.getOptions().getFilters() != null)
            query = ElasticUtility.filterSearch(query, searchRequest.getOptions().getFilters(), itemStatusList);
        Pageable p = new PageRequest(page, pSize);

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(lib + "library_domain")
                .withTypes("record")
                .withPageable(p);

        if(Helper.resolve(() -> searchRequest.getOptions().getSort().getType()).isPresent()) {
            Sort s = searchRequest.getOptions().getSort();
            if (s.getType() != null)
                searchQuery.withSort(SortBuilders.fieldSort("prefixes." + s.getType().toString()).order(s.getAscending() ? SortOrder.ASC : SortOrder.DESC));
        }

        Iterable<ElasticPrefixEntity> ii = elasticsearchTemplate.queryForPage(searchQuery.build(), ElasticPrefixEntity.class);

        ii.forEach(
                elasticPrefixEntity -> {
                    String recMongoId = elasticPrefixEntity.getId();
                    Optional<Record> r = recordsRepository.findById(recMongoId);
                    if (!r.isPresent()) {
                        log.warn("Can't get record for mongoId: " + recMongoId);
                    } else {
                        Book b = getBookByRec(r.get());
                        retVal.add(b);
                    }
                }
        );
        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    public PageImpl<List<Book>> searchBooksByIds(ResultPageSearchRequest searchRequest, String lib, Integer page, Integer pageSize) {
        List<Book> retVal = new ArrayList<>();

        if (searchRequest == null || searchRequest.getRecordsIds() == null) return null;


        Iterable<ElasticPrefixEntity> eeL = elasticRecordsRepository.findAllById(searchRequest.getRecordsIds());

        eeL.forEach(
                elasticPrefixEntity -> {
                    String recMongoId = elasticPrefixEntity.getId();
                    Optional<Record> r = recordsRepository.findById(recMongoId);
                    if (!r.isPresent()) {
                        log.warn("Can't get record for mongoId: " + recMongoId);
                    } else {
                        Book b = getBookByRec(r.get());
                        retVal.add(b);
                    }
                }
        );

        page = 0;
        int pSize = retVal.size();
        Pageable p = new PageRequest(page, pSize);


        return new PageImpl(retVal, p, pSize);
    }

    public Book getFullBookById(String _id, String lib) {
        LibraryConfiguration libraryConfiguration = libraryConfigurationRepository.getByLibraryName(lib);
        Optional<Record> record =  recordsRepository.findById(_id);
        if (record.isPresent()) {
            Book retVal = getBookByRec(record.get());
            retVal.setItems(getItems(record.get(), lib));
            retVal.setRecord(record.get());
            fillMasterRecordInfo(record.get(), retVal);
            String isbdHtml = ReportCore.makeOne(record.get(), false, libraryConfiguration);
            retVal.setIsbdHtml(isbdHtml);
            return retVal;
        }
        return null;
    }

    void fillMasterRecordInfo(Record r, Book b) {
        if (r == null || b == null)
            return;
        Integer masterRN = null;
        List<Field> _464s = r.getFields("464");
        List<Field> _474s = r.getFields("474");
        for (Field f: _464s) {
            if (f.getSubfieldContent('1') == null || f.getSubfieldContent('1').equals("0"))
                continue;
                String _4641 = f.getSubfieldContent('1').trim();
                if (_4641.split(" ").length > 1)
                    _4641 = _4641.split(" ")[0];
                if (_4641.matches("[0-9]+")) {
                    masterRN = Integer.parseInt(_4641);
                    break;
                }
        }
        if (masterRN == null) {
            for (Field f: _474s) {
                if (f.getSubfieldContent('1') == null || f.getSubfieldContent('1').equals("0"))
                    continue;
                String _4741 = f.getSubfieldContent('1').trim();
                if (_4741.split(" ").length > 1)
                    _4741 = _4741.split(" ")[0];
                if (!_4741.matches("[0-9]+"))
                    continue;
                masterRN = Integer.parseInt(_4741);
                break;

            }
        }
        if (masterRN == null)
            return;
        Record masterRecord = recordsRepository.getByRn(masterRN);
        if (masterRecord == null)
            return;
        b.setMasterRecordId(masterRecord.get_id());
        RecordPreview rp = new RecordPreview();
        rp.init(masterRecord);
        String masterTitle = rp.getTitle();
        if (r.getSubfieldContent("001d") != null && r.getSubfieldContent("001d").equals("2")
            && r.getSubfieldContent("215a") != null) {
            masterTitle += " " + LatCyrUtils.toCyrillic(r.getSubfieldContent("215a").trim());
        }
        b.setMasterRecordTitle(masterTitle);
    }


    Book getBookByRec(Record r) {
        Book b = new Book();
        b.set_id(r.get_id());
        RecordPreview rp = new RecordPreview();
        rp.init(r);
        b.setAuthors(Arrays.asList(rp.getAuthor(r)));
        b.setTitle(rp.getTitle());
        b.setSubtitle(rp.getSubtitle());
        b.setPubType(r.getPubType());
        b.setIsbn(rp.getISSN(r));
        b.setIsbn(r.getSubfieldContent("010a"));
        b.setPublisher(rp.getPublisher());
        b.setPublishYear(rp.getPublishingYear());
        b.setPublishPlace(rp.getPublishingPlace());
        b.setPagesCount(rp.getPages());
        b.setDimensions(rp.getDimensions());
        b.setUdk(rp.getUdk());
        b.setNotes(rp.getNotes(r));
        b.setAvgRating(r.getAvgRating());
        b.setTotalRatings(r.getRecordRatings() != null ? r.getRecordRatings().size() : 0);
        if (r.getCommonBookUid() != null) {
            BookCommon bc = bookCommonRepository.findByUid(r.getCommonBookUid());
            if (bc != null) {
                b.setDescription(bc.getDescription());
                b.setImageUrl(bc.getImageUrl());
                b.setCommonBookUID(bc.getUid());
            }
        }
        b.setOtherAuthors(rp.getOtherAuthors());
        return b;
    }

    List<Item> getItems(Record r, String lib) {
        List<Item> retVal = new ArrayList<>();
        Map<String, Location> locationMap = locationRepository.getCoders(lib).stream().collect(Collectors.toMap(Location::getCoder_id, l -> l));
        Map<String, Sublocation> sublocationMap = sublocationRepository.getCoders(lib).stream().collect(Collectors.toMap(Sublocation::getCoder_id, sl -> sl));
        Map<String, ItemStatus> itemStatusMap = itemStatusRepository.getCoders(lib).stream().collect(Collectors.toMap(ItemStatus::getCoder_id, sl -> sl));
        for (String key: locationMap.keySet()) {
            Location l = locationMap.get(key);
            l.setDescription(LatCyrUtils.toCyrillic(l.getDescription()));
        }
        for (String key: sublocationMap.keySet()) {
            Sublocation sl = sublocationMap.get(key);
            sl.setDescription(LatCyrUtils.toCyrillic(sl.getDescription()));
        }
        if (r == null || ((r.getPrimerci() == null || r.getPrimerci().size() == 0) &&
                (r.getGodine() == null || r.getGodine().size() == 0)))
            return null;
        if (r.getPrimerci().size() > 0) {
            for (Primerak p: r.getPrimerci()) {
                Item i = new Item();
                i.setInvNum(p.getInvBroj());
                ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(i.getInvNum());
                if (ia == null) continue;
                String itemStatus = ia.getBorrowed() ? "BORROWED" : "FREE";
                ItemStatus is = itemStatusMap.get(p.getStatus());
                if (is != null && !is.isLendable()) {
                    itemStatus = "NOT_LENDABLE";
                    if (!is.isShowable()) itemStatus = "NOT_SHOWABLE";
                }
                i.setSignature(Signature.format(p));
                i.setStatus(itemStatus);
                i.setSerial(false);
                Sublocation sl = sublocationMap.get(p.getSigPodlokacija());
                if (sl != null) {
                    i.setLocation(sl.getDescription());
                    i.setLocCode(p.getSigPodlokacija());
                    i.setGoogleMapLocationURL(sl.getGoogleMapLocationURL());
                }
                else {
                    Location l = locationMap.get(p.getInvBroj().substring(0,2));
                    if (l == null) continue;
                    i.setLocCode(l.getCoder_id());
                    i.setLocation(l.getDescription());
                    i.setGoogleMapLocationURL(l.getGoogleMapLocationURL());
                }
                retVal.add(i);
            }
        }
        else if (r.getGodine().size() > 0) {
            for (Godina p: r.getGodine()) {
                Item i = new Item();
                i.setInvNum(p.getInvBroj());
                ItemAvailability ia = itemAvailabilityRepository.getByCtlgNo(i.getInvNum());
                if (ia == null) continue;
                String itemStatus = "NOT_LENDABLE";
                i.setSignature(Signature.format(p));
                i.setStatus(itemStatus);
                i.setVolume(p.getGodiste());
                i.setYear(p.getGodina());
                i.setNumber(p.getBroj());
                i.setSerial(true);
                Sublocation sl = sublocationMap.get(p.getSigPodlokacija());
                if (sl != null) {
                    i.setLocation(sl.getDescription());
                    i.setLocCode(p.getSigPodlokacija());
                    i.setGoogleMapLocationURL(sl.getGoogleMapLocationURL());
                }
                else {
                    Location l = locationMap.get(p.getInvBroj().substring(0,2));
                    if (l == null) continue;
                    i.setLocCode(l.getCoder_id());
                    i.setLocation(l.getDescription());
                    i.setGoogleMapLocationURL(l.getGoogleMapLocationURL());
                }
                retVal.add(i);
            }
        }

        if (retVal.size() > 0) {
            retVal.sort(Comparator.comparing(Item::getInvNum));
            return retVal;
        }
        else return null;
    }



    public Filters getFilters(ResultPageSearchRequest filterRequest, String library) {
        if (filterRequest == null || filterRequest.getSearchModel() == null)
            return null;
        Filters retVal = null;

        BoolQueryBuilder query = ElasticUtility.makeQuery(filterRequest.getSearchModel());
        FiltersReq filtersReq = null;

        List<ItemStatus> itemStatusList = itemStatusRepository.getCoders(library);
        itemStatusList = itemStatusList.stream().filter(ItemStatus::isShowable).collect(Collectors.toList());

        if (filterRequest.getOptions() != null && filterRequest.getOptions().getFilters() != null) {
            query = ElasticUtility.filterSearch(query, filterRequest.getOptions().getFilters(), itemStatusList);
            filtersReq = filterRequest.getOptions().getFilters();
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(library + "library_domain")
                .withTypes("record")
                .withPageable(PageRequest.of(0, 10))
                .build();
        CloseableIterator<ElasticPrefixEntity> searchResults = elasticsearchTemplate
                .stream(searchQuery, ElasticPrefixEntity.class);
        retVal = extractFiltersFromResults(searchResults, filtersReq, library);
        return retVal;
    }

    private Filters extractFiltersFromResults(CloseableIterator<ElasticPrefixEntity> results, FiltersReq filtersReq, String lib) {
        Filters filters = new Filters();
        List<String> prefixes = Arrays.asList("authors_raw", "PY", "DT", "LA", "OD_showable", "SL_showable", "subjects_raw");
        Map<String, Location> locationMap = locationRepository.getCoders(lib).stream().collect(Collectors.toMap(Location::getCoder_id, l -> l));
        Map<String, Sublocation> sublocationMap = sublocationRepository.getCoders(lib).stream().collect(Collectors.toMap(Sublocation::getCoder_id, sl -> sl));

        List<ItemStatus> itemStatusList = itemStatusRepository.getCoders(lib);
        itemStatusList = itemStatusList.stream().filter(ItemStatus::isShowable).collect(Collectors.toList());
        String activeStatusesRegex = "";
        if (itemStatusList != null && itemStatusList.size() > 0) {
            activeStatusesRegex = "(" + itemStatusList.stream().map(ItemStatus::getCoder_id).collect(Collectors.joining("|")) + ").*";
        }

        for (String key: locationMap.keySet()) {
            Location l = locationMap.get(key);
            l.setDescription(LatCyrUtils.toCyrillic(l.getDescription()));
        }
        for (String key: sublocationMap.keySet()) {
            Sublocation sl = sublocationMap.get(key);
            sl.setDescription(LatCyrUtils.toCyrillic(sl.getDescription()));
        }
        Map<String, Integer> subLocationCount = new HashMap<>();
        for (String slKey: sublocationMap.keySet()) {
            subLocationCount.put(slKey, 0);
        }
        while (results.hasNext()) {
            ElasticPrefixEntity ee = results.next();
            if (ee.getPrefixes() == null)
                continue;
            for (String prefix: prefixes) {
                if (ee.getPrefixes().get(prefix) == null || ee.getPrefixes().get(prefix).size() == 0) {
                    continue;
                }
                Set<String> authorsUnique = new HashSet<>();
                switch (prefix) {
                    case "authors_raw":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = ee.getPrefixes().get(prefix).get(i);
                            String normalizedVal = getAuthorNormalizedStr(val);
                            if (normalizedVal == null) continue;
                            if (authorsUnique.contains(normalizedVal))
                                continue;
                            if (filters.getAuthorByValue(val) == null) {
                                boolean checked = (filtersReq != null && filtersReq.getAuthors() != null && filtersReq.getAuthors().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(val, val, checked, 1);
                                filters.getAuthors().add(new Filter(filterItem, null));
                                authorsUnique.add(normalizedVal);
                            } else {
                                filters.getAuthorByValue(val).getFilter().setCount(filters.getAuthorByValue(val).getFilter().getCount() + 1);
                                authorsUnique.add(normalizedVal);
                            }
                        }
                        break;
                    case "PY":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = ee.getPrefixes().get(prefix).get(i);
                            if (val == null || val.equals("")) continue;
                            String valTrimmed = val.trim();
                            if (filters.getPubYearByValue(val) == null) {
                                boolean checked = (filtersReq != null && filtersReq.getPubYears() != null && filtersReq.getPubYears().stream().anyMatch(e -> e.getItem().getValue().trim().equals(valTrimmed)));
                                FilterItem filterItem = new FilterItem(valTrimmed, valTrimmed, checked, 1);
                                filters.getPubYears().add(new Filter(filterItem, null));
                            } else {
                                filters.getPubYearByValue(val).getFilter().setCount(filters.getPubYearByValue(val).getFilter().getCount() + 1);
                            }
                        }
                        break;
                    case "DT":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = ee.getPrefixes().get(prefix).get(i);
                            if (filters.getPubTypesByValue(val) == null) {
                                String lbl = getPubTypeLabel(val);
                                if (lbl == null) continue;
                                boolean checked = (filtersReq != null && filtersReq.getPubTypes() != null && filtersReq.getPubTypes().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(lbl, val, checked, 1);
                                filters.getPubTypes().add(new Filter(filterItem, null));
                            } else {
                                filters.getPubTypesByValue(val).getFilter().setCount(filters.getPubTypesByValue(val).getFilter().getCount() + 1);
                            }
                        }
                        break;
                    case "LA":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = mergeCoderVal(ee.getPrefixes().get(prefix).get(i));
                            if (filters.getLanguagesByValue(val) == null) {
                                String lbl = getLanguageLabel(val);
                                if (lbl == null) continue;
                                boolean checked = (filtersReq != null && filtersReq.getLanguages() != null && filtersReq.getLanguages().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(lbl, val, checked, 1);
                                filters.getLanguages().add(new Filter(filterItem, null));
                            } else {
                                filters.getLanguagesByValue(val).getFilter().setCount(filters.getLanguagesByValue(val).getFilter().getCount() + 1);
                            }
                        }
                        break;
                    case "OD_showable":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String fullVal = ee.getPrefixes().get(prefix).get(i);
                            if (!fullVal.matches(activeStatusesRegex)) continue;
                            String val = fullVal.substring(1);
                            if (filters.getLocationByValue(val) == null) {
                                Location l = locationMap.get(val);
                                if (l == null) continue;
                                boolean checked = (filtersReq != null && filtersReq.getLocations() != null && filtersReq.getLocations().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(l.getDescription(), val, checked, 1);
                                filters.getLocations().add(new Filter(filterItem, new ArrayList<>()));
                            } else {
                                filters.getLocationByValue(val).getFilter().setCount(filters.getLocationByValue(val).getFilter().getCount() + 1);
                            }
                        }
                        break;
                    case "SL_showable": {
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String fullVal = ee.getPrefixes().get(prefix).get(i);
                            if (!fullVal.matches(activeStatusesRegex)) continue;
                            String val = fullVal.substring(1);
                            if (val != null && val.length() > 1 && subLocationCount.get(val) != null)
                                subLocationCount.put(val, subLocationCount.get(val) + 1);
                        }
                    } break;
                    case "subjects_raw": {
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = getNormalizedSubject(ee.getPrefixes().get(prefix).get(i));
                            if (filters.getSubjectByValue(val) == null) {
                                String lbl = val;
                                if (lbl == null) continue;
                                boolean checked = (filtersReq != null && filtersReq.getSubjects() != null && filtersReq.getSubjects().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(lbl, val, checked, 1);
                                filters.getSubjects().add(new Filter(filterItem, null));
                            } else {
                                filters.getSubjectByValue(val).getFilter().setCount(filters.getSubjectByValue(val).getFilter().getCount() + 1);
                            }
                        }
                    } break;
                }
            }
        }
        for (String slKey: subLocationCount.keySet()) {
            String loc = null;
            int count = subLocationCount.get(slKey);
//            TODO- change this to read from some config prop if it is higher hierarchy with sub locations, like BGB
            if (slKey.length() == 4 && slKey.matches("[0-9]+"))
                loc = slKey.substring(0, 2);
            else continue;
            if (filters.getLocationByValue(loc) != null && count > 0)
                filters.getLocationByValue(loc).getChildren().add(new FilterItem(sublocationMap.get(slKey).getDescription(), slKey, false, count));
        }
        filters.sortFilters();
        return filters;
    }

    private String getAuthorNormalizedStr(String val) {
        if (val == null || val.equals("")) return null;
        char[] charArray = val
                .replace(",", "")
                .replaceAll("-", "")
                .replaceAll("\\.", "")
                .replaceAll(" ", "")
                .toLowerCase()
                .trim()
                .toCharArray();
        Arrays.sort(charArray);
        String normalizedVal = new String(charArray);
        return normalizedVal;
    }

    private String getNormalizedSubject(String val) {
        if (val == null || val.trim().equals("") || val.trim().length() < 4)
            return null;
        val = val.replace("\"", "");
        return val;
    }

    private String getPubTypeLabel(String val) {
        String lbl = null;
        switch (val) {
            case "a": lbl = "Чланци"; break;
            case "c": lbl = "Збирке- нумерисана";break;
            case "d": lbl = "Изведени радови";break;
            case "e": lbl = "Збирке- ненумерисана";break;
            case "m": lbl = "Монографске";break;
            case "s": lbl = "Серијске";break;
            case "r": lbl = "Разгледнице";break;
            case "z": lbl = "Збирни записи";break;
        }
        return lbl;
    }

    private String getLanguageLabel(String val) {
        String lbl = null;
        switch (val) {
//            case "scc": lbl = "Српски ћирилица"; break;
            case "srp": lbl = "Српски"; break;
//            case "scr": lbl = "Српски латиница"; break;
            case "hrv": lbl = "Хрватски"; break;
            case "eng": lbl = "Енглески"; break;
            case "ger": lbl = "Немачки"; break;
            case "slv": lbl = "Словеначки"; break;
            case "bos": lbl = "Босански"; break;
            case "rus": lbl = "Руски"; break;
            case "fra": lbl = "Француски"; break;
            case "cze": lbl = "Чешки"; break;
            case "hun": lbl = "Мађарски"; break;
            case "rum": lbl = "Румунски"; break;
            case "alb": lbl = "Албански"; break;
            case "ita": lbl = "Италијански"; break;
            case "pol": lbl = "Пољски"; break;
            case "slo": lbl = "Словачки"; break;
            case "bgr": lbl = "Бугарски"; break;
            case "tur": lbl = "Турски"; break;
            case "spa": lbl = "Шпански"; break;
            case "rom": lbl = "Ромски"; break;
            case "lat": lbl = "Латински"; break;
            case "mul": lbl = "Вишејезични"; break;
        }
        return lbl;
    }

    public String mergeCoderVal(String val) {
        switch (val) {
            case "scr": return "srp";
            case "scc": return "srp";
            default: return val;
        }
    }
}
