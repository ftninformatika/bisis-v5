package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.opac2.opac2.Filters;
import com.ftninformatika.bisis.opac2.opac2.LocationFilterWrapper;
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
//        if (filterRequest == null || filterRequest.getSearchModel() == null)
//            return null;
        Filters retVal = new Filters();
        List<Location> locations = codersController.getLocations(library);
        List<LocationFilterWrapper> locationFilters = new ArrayList<>();
        if (locations != null && locations.size() > 0) {
            for(Location l: locations) {
                LocationFilterWrapper lf = new LocationFilterWrapper();
                lf.setLocation(l);
                lf.setSublocations(codersController.getSublocationsByLocation(l.getCoder_id(), library));
                locationFilters.add(lf);
            }
            retVal.setLocations(locationFilters);
        }

        return retVal;
    }
}
