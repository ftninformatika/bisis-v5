package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.opac2.search.*;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.controller.CodersController;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.Helper;
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

        if (searchRequest.getOptions() != null && searchRequest.getOptions().getFilters() != null)
            query = ElasticUtility.filterSearch(query, searchRequest.getOptions().getFilters());
        Pageable p = new PageRequest(page, pSize);

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(lib + "library_domain")
                .withTypes("record")
                .withPageable(p);

        if(Helper.resolve(() -> searchRequest.getOptions().getSort().getType()).isPresent()) {
            Sort s = searchRequest.getOptions().getSort();
            if (s.getType() != null)
                searchQuery.withSort(SortBuilders.fieldSort("prefixes." + s.getType().toString()).order(s.getAscending() == true ? SortOrder.ASC : SortOrder.DESC));
        }

        Iterable<ElasticPrefixEntity> ii = elasticsearchTemplate.queryForPage(searchQuery.build(), ElasticPrefixEntity.class);

        ii.forEach(
                elasticPrefixEntity -> {
                    String recMongoId = elasticPrefixEntity.getId();
                    Optional<Record> r = recordsRepository.findById(recMongoId);
                    if (!r.isPresent()) {
                        log.warn("Can't get record for mongoId: " + recMongoId);
                    } else {
                        Book b = getBookByRec(r.get(), elasticPrefixEntity);
                        retVal.add(b);
                    }
                }
        );
        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
    }

    private Book getBookByRec(Record r, ElasticPrefixEntity elasticPrefixEntity) {
        Book b = new Book();
        b.set_id(r.get_id());
        RecordPreview rp = new RecordPreview();
        rp.init(r);
        b.setAuthors(getAuthors(elasticPrefixEntity));
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
        if (r.getCommonBookUid() != null) {
            BookCommon bc = bookCommonRepository.findByUid(r.getCommonBookUid());
            if (bc != null) {
                b.setDescription(bc.getDescription());
                b.setImageUrl(bc.getImageUrl());
            }
        }
        return b;
    }

    private List<String> getAuthors(ElasticPrefixEntity e) {
        List<String> retVal = new ArrayList<>();
        List<String> vals = e.getPrefixes().get("authors_raw");
        if (vals == null || vals.size() == 0) return retVal;
        for (int i = 0; i < vals.size(); i = i + 2) {
            if (!vals.get(i).trim().equals(""))
                retVal.add(vals.get(i));
        }
        return retVal;
    }

    public Filters getFilters(ResultPageSearchRequest filterRequest, String library) {
        if (filterRequest == null || filterRequest.getSearchModel() == null)
            return null;
        Filters retVal = null;

        BoolQueryBuilder query = ElasticUtility.makeQuery(filterRequest.getSearchModel());
        FiltersReq filtersReq = null;

        if (filterRequest.getOptions() != null && filterRequest.getOptions().getFilters() != null) {
            query = ElasticUtility.filterSearch(query, filterRequest.getOptions().getFilters());
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
        List<String> prefixes = Arrays.asList("authors_raw", "PY", "DT", "LA", "OD", "SL");
        Map<String, Location> locationMap = locationRepository.getCoders(lib).stream().collect(Collectors.toMap(Location::getCoder_id, l -> l));
        Map<String, Sublocation> sublocationMap = sublocationRepository.getCoders(lib).stream().collect(Collectors.toMap(Sublocation::getCoder_id, sl -> sl));
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
                switch (prefix) {
                    case "authors_raw":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i += 2) {
//                Every second, because of the indexing logic (Ivo Andric, Andric Ivo)
                            String val = ee.getPrefixes().get(prefix).get(i);
                            if (val == null || val.equals("")) continue;
                            if (filters.getAuthorByValue(val) == null) {
                                boolean checked = (filtersReq != null && filtersReq.getAuthors() != null && filtersReq.getAuthors().stream().anyMatch(e -> e.getItem().getValue().equals(val)));
                                FilterItem filterItem = new FilterItem(val, val, checked, 1);
                                filters.getAuthors().add(new Filter(filterItem, null));
                            } else {
                                filters.getAuthorByValue(val).getFilter().setCount(filters.getAuthorByValue(val).getFilter().getCount() + 1);
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
                            String val = ee.getPrefixes().get(prefix).get(i);
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
                    case "OD":
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = ee.getPrefixes().get(prefix).get(i);
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
                    case "SL": {
                        for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
                            String val = ee.getPrefixes().get(prefix).get(i);
                            if (subLocationCount.get(val) != null)
                                subLocationCount.put(val, subLocationCount.get(val) + 1);
                        }
                    } break;
                }
            }
        }
        for (String slKey: subLocationCount.keySet()) {
            String loc = null;
            int count = subLocationCount.get(slKey);
//            TODO- change this to read from some config prop if it is higher hierarchy with sub locations, like BGB
            if (slKey.length() == 4)
                loc = slKey.substring(0, 2);
            else continue;
            if (filters.getLocationByValue(loc) != null || count > 0)
                filters.getLocationByValue(loc).getChildren().add(new FilterItem(sublocationMap.get(slKey).getDescription(), slKey, false, count));
        }
        filters.sortFilters();
        return filters;
    }

    private String getPubTypeLabel(String val) {
        String lbl = null;
        switch (val) {
            case "a": lbl = "Аналитика"; break;
            case "c": lbl = "Збирка- нумерисана";break;
            case "d": lbl = "Изведени радови";break;
            case "e": lbl = "Збирка- ненумерисана";break;
            case "m": lbl = "Монографија";break;
            case "s": lbl = "Серијска";break;
            case "r": lbl = "Разгледнице";break;
            case "z": lbl = "Збирни запис";break;
        }
        return lbl;
    }

    private String getLanguageLabel(String val) {
        String lbl = null;
        switch (val) {
            case "scc": lbl = "Српски ћирилица"; break;
            case "srp": lbl = "Српски"; break;
            case "scr": lbl = "Српски латиница"; break;
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
}
