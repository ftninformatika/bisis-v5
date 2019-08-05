package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.opac2.opac2.Filter;
import com.ftninformatika.bisis.opac2.opac2.FilterItem;
import com.ftninformatika.bisis.opac2.opac2.Filters;
import com.ftninformatika.bisis.opac2.opac2.ResultPageFilterRequest;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.controller.CodersController;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.util.elastic.ElasticUtility;
import org.apache.log4j.Logger;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author badf00d21  25.7.19.
 */
@Service
public class OpacSearchService {


    @Autowired RecordsRepository recordsRepository;
    @Autowired ElasticRecordsRepository elasticRecordsRepository;
    @Autowired BookCommonRepository bookCommonRepository;
    @Autowired ElasticsearchTemplate elasticsearchTemplate;
//    TODO- refactor this at some point (don't import controllers in service layer)
    @Autowired CodersController codersController;
    private Logger log = Logger.getLogger(OpacSearchService.class);

    public PageImpl<List<Book>> searchBooks(SearchModel searchModel, Integer pageNumber, Integer pageSize) {
        List<Book> retVal = new ArrayList<>();

        int page = 0;
        int pSize = 10;

        if (pageNumber != null)
            page = pageNumber;
        if (pageSize != null)
            pSize = pageSize;

        Pageable p = new PageRequest(page, pSize);
        Iterable<ElasticPrefixEntity> ii = elasticRecordsRepository.search(ElasticUtility.makeQuery(searchModel), p);

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

    public Filters getFilters(ResultPageFilterRequest filterRequest, String library) {
        if (filterRequest == null || filterRequest.getSearchModel() == null)
            return null;
        Filters retVal = null;

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(ElasticUtility.makeQuery(filterRequest.getSearchModel()))
                .withIndices(library + "library_domain") // TODO- hardcoded
                .withTypes("record")
                .withPageable(PageRequest.of(0, 10))
                .build();

        CloseableIterator<ElasticPrefixEntity> searchResults = elasticsearchTemplate
                .stream(searchQuery, ElasticPrefixEntity.class);
        retVal = extractFiltersFromResults(searchResults);
        return retVal;
    }

    private Filters extractFiltersFromResults(CloseableIterator<ElasticPrefixEntity> results) {
        Filters retVal = new Filters();
        while (results.hasNext()) {
            ElasticPrefixEntity ee = results.next();
            extractFiltersData(retVal, ee, "authors_raw", false);
            extractFiltersData(retVal, ee, "PY", false);
            extractFiltersData(retVal, ee, "DT", false);
            extractFiltersData(retVal, ee, "LA", false);
        }
        return retVal;
    }

    private void extractFiltersData(Filters filters, ElasticPrefixEntity ee, String prefix, boolean isLocation) {
        if (ee.getPrefixes() == null || ee.getPrefixes().get(prefix) == null
                || ee.getPrefixes().get(prefix).size() == 0)
            return;
        List<String> values = ee.getPrefixes().get(prefix);
        switch (prefix) {
            case "authors_raw": for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i += 2) {
//                Every second, because of the indexing logic (Ivo Andric, Andric Ivo)
                String val = ee.getPrefixes().get(prefix).get(i);
                if (filters.getAuthorByValue(val) == null) {
                    FilterItem filterItem = new FilterItem();
                    filterItem.setCount(1);
                    filterItem.setLabel(val);
                    filterItem.setValue(val);
                    filters.getAuthors().add(new Filter(filterItem, null));
                } else {
                    filters.getAuthorByValue(val).getFilter().setCount(filters.getAuthorByValue(val).getFilter().getCount() + 1);
                }
            } break;
            case "PY": for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
//                Every second, because of the indexing logic (Ivo Andric, Andric Ivo)
                String val = ee.getPrefixes().get(prefix).get(i);
                if (filters.getPubYearByValue(val) == null) {
                    FilterItem filterItem = new FilterItem();
                    filterItem.setCount(1);
                    filterItem.setLabel(val);
                    filterItem.setValue(val);
                    filters.getPubYears().add(new Filter(filterItem, null));
                } else {
                    filters.getPubYearByValue(val).getFilter().setCount(filters.getPubYearByValue(val).getFilter().getCount() + 1);
                }
            } break;
            case "DT": for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
//                Every second, because of the indexing logic (Ivo Andric, Andric Ivo)
                String val = ee.getPrefixes().get(prefix).get(i);
                if (filters.getPubTypesByValue(val) == null) {
                    FilterItem filterItem = new FilterItem();
                    filterItem.setCount(1);
                    String lbl = "Монографија";
                    if (val.equals("a")) lbl = "Аналитички чланак";
                    if (val.equals("c")) lbl = "Књижевна збирка- нумерисана";
                    if (val.equals("d")) lbl = "Изведени радови";
                    if (val.equals("e")) lbl = "Књижевна збирка- ненумерисана";
                    if (val.equals("m")) lbl = "Монографска публикација";
                    if (val.equals("s")) lbl = "Серијска публикација";
                    if (val.equals("r")) lbl = "Разгледнице";
                    if (val.equals("z")) lbl = "Збирни запис";
                    filterItem.setLabel(lbl);
                    filterItem.setValue(val);
                    filters.getPubTypes().add(new Filter(filterItem, null));
                } else {
                    filters.getPubTypesByValue(val).getFilter().setCount(filters.getPubTypesByValue(val).getFilter().getCount() + 1);
                }
            } break;
            case "LA": for (int i = 0; i < ee.getPrefixes().get(prefix).size(); i++) {
//                Every second, because of the indexing logic (Ivo Andric, Andric Ivo)
                String val = ee.getPrefixes().get(prefix).get(i);
                if (filters.getLanguagesByValue(val) == null) {
                    FilterItem filterItem = new FilterItem();
                    filterItem.setCount(1);
                    String lbl;
                    if (val.equals("scc")) lbl = "Српски ћирилица";
                    else if (val.equals("srp")) lbl = "Српски";
                    else if (val.equals("scr")) lbl = "Српски латиница";
                    else if (val.equals("hrv")) lbl = "Хрватски";
                    else if (val.equals("eng")) lbl = "Енглески";
                    else if (val.equals("ger")) lbl = "Немачки";
                    else if (val.equals("slv")) lbl = "Словеначки";
                    else if (val.equals("bos")) lbl = "Босански";
                    else if (val.equals("rus")) lbl = "Руски";
                    else if (val.equals("fra")) lbl = "Француски";
                    else if (val.equals("cze")) lbl = "Чешки";
                    else if (val.equals("hun")) lbl = "Мађарски";
                    else if (val.equals("rum")) lbl = "Румунски";
                    else if (val.equals("alb")) lbl = "Албански";
                    else if (val.equals("ita")) lbl = "Италијански";
                    else if (val.equals("pol")) lbl = "Пољски";
                    else if (val.equals("slo")) lbl = "Словачки";
                    else if (val.equals("bgr")) lbl = "Бугарски";
                    else if (val.equals("tur")) lbl = "Турски";
                    else if (val.equals("spa")) lbl = "Шпански";
                    else if (val.equals("rom")) lbl = "Ромски";
                    else if (val.equals("lat")) lbl = "Латински";
                    else if (val.equals("mul")) lbl = "Вишејезични";
                    else continue;
                    filterItem.setLabel(lbl);
                    filterItem.setValue(val);
                    filters.getLanguages().add(new Filter(filterItem, null));
                } else {
                    filters.getLanguagesByValue(val).getFilter().setCount(filters.getLanguagesByValue(val).getFilter().getCount() + 1);
                }
            } break;
        }

    }
}
