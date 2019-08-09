package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.opac2.opac2.*;
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
import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
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

    public PageImpl<List<Book>> searchBooks(ResultPageSearchRequest searchRequest, Integer pageNumber, Integer pageSize) {
        List<Book> retVal = new ArrayList<>();

        if (searchRequest != null && searchRequest.getSearchModel() == null) return null;

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
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(query, p);

        ii.forEach(
                elasticPrefixEntity -> {
                    String recMongoId = elasticPrefixEntity.getId();
                    Optional<Record> r = recordsRepository.findById(recMongoId);
                    if (!r.isPresent()) {
                        log.warn("Can't get record for mongoId: " + recMongoId);
                    } else {
                        Book b = new Book();
                        b.set_id(r.get().get_id());
//                        TODO- refactor this later, put all extracting metadata from fields on one place
                        RecordPreview rp = new RecordPreview();
                        rp.init(r.get());
                        b.getAuthors().add(rp.getAuthor());
                        b.setTitle(rp.getTitle());
                        b.setSubtitle(rp.getSubtitle());
                        b.setPubType(r.get().getPubType());
                        b.setIsbn(rp.getISSN(r.get()));
                        b.setIsbn(r.get().getSubfieldContent("010a"));
                        b.setPublisher(rp.getPublisher());
                        b.setPublishYear(rp.getPublishingYear());
                        b.setPublishPlace(rp.getPublishingPlace());
                        b.setPagesCount(rp.getPages());
                        b.setDimensions(rp.getDimensions());
                        if (r.get().getCommonBookUid() != null) {
                            BookCommon bc = bookCommonRepository.findByUid(r.get().getCommonBookUid());
                            if (bc != null) {
                                b.setDescription(bc.getDescription());
                                b.setImageUrl(bc.getImageUrl());
                            }
                        }
                        retVal.add(b);
                    }
                }
        );
        return new PageImpl(retVal, p, ((Page<ElasticPrefixEntity>) ii).getTotalElements());
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
        List<String> prefixes = Arrays.asList("authors_raw", "PY", "DT", "LA", "OD");
        Map<String, Location> locationMap = locationRepository.getCoders(lib).stream().collect(Collectors.toMap(Location::getCoder_id, l -> l));
        Map<String, Sublocation> sublocationMap = sublocationRepository.getCoders(lib).stream().collect(Collectors.toMap(Sublocation::getCoder_id, sl -> sl));
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
                                boolean checked = (filtersReq != null && filtersReq.getAuthors() != null && filtersReq.getAuthors().contains(val));
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
                            val = val.trim();
                            if (filters.getPubYearByValue(val) == null) {
                                boolean checked = (filtersReq != null && filtersReq.getPubYears() != null && filtersReq.getPubYears().contains(val));
                                FilterItem filterItem = new FilterItem(val, val, checked, 1);
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
                                boolean checked = (filtersReq != null && filtersReq.getPubTypes() != null && filtersReq.getPubTypes().contains(val));
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
                                boolean checked = (filtersReq != null && filtersReq.getLanguages() != null && filtersReq.getLanguages().contains(val));
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
                                boolean checked = (filtersReq != null && filtersReq.getLocations() != null && filtersReq.getLocations().contains(val));
                                FilterItem filterItem = new FilterItem(l.getDescription(), val, checked, 1);
                                List<String> subVals = ee.getPrefixes().get("SL");
                                filters.getLocations().add(new Filter(filterItem, new ArrayList<>()));
                                if (subVals != null && subVals.size() > 0) {
                                    for (String subVal : subVals) {
                                        if (filters.getSublocationByValue(subVal) == null) {
                                            Sublocation sl = sublocationMap.get(subVal);
                                            if (sl == null) continue;
                                            boolean checked1 = (filtersReq != null && filtersReq.getSubLocations() != null && filtersReq.getSubLocations().contains(val));
                                            FilterItem subFilterItem = new FilterItem(sl.getDescription(), subVal, checked, 1);
                                            filters.getLocationByValue(val).getChildren().add(subFilterItem);
                                        } else {
                                            filters.getSublocationByValue(subVal).setCount(filters.getSublocationByValue(subVal).getCount() + 1);
                                        }
                                    }

                                }
                            } else {
                                filters.getLocationByValue(val).getFilter().setCount(filters.getLocationByValue(val).getFilter().getCount() + 1);
                            }
                        }
                        break;
                }
            }
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
