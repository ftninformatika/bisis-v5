package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import com.ftninformatika.bisis.datawarehouse.entity.Item;
import com.ftninformatika.bisis.datawarehouse.entity.Lending;
import com.ftninformatika.bisis.datawarehouse.entity.Membership;
import com.ftninformatika.bisis.datawarehouse.model.SearchDetailsRequest;
import com.ftninformatika.bisis.datawarehouse.model.SearchType;
import com.ftninformatika.bisis.datawarehouse.model.SelectedCoder;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchDetailsService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    public List<Object[]> searchDetails(SearchDetailsRequest searchDetailsRequest) {
        switch (searchDetailsRequest.getType()) {
            case SearchType.ITEM:
                return searchDetailsItem(searchDetailsRequest);
            case SearchType.LENDING:
                return searchDetailsLending(searchDetailsRequest);
            case SearchType.MEMBERSHIP:
                return searchDetailsMembership(searchDetailsRequest);
        }
        return null;
    }

    public Long countDetails(SearchDetailsRequest searchDetailsRequest) {
        switch (searchDetailsRequest.getType()) {
            case SearchType.ITEM:
                return countDetailsItem(searchDetailsRequest);
            case SearchType.LENDING:
                return countDetailsLending(searchDetailsRequest);
            case SearchType.MEMBERSHIP:
                return countDetailsMembership(searchDetailsRequest);
        }
        return null;
    }

    private List<Object[]> searchDetailsItem(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Item> root = cq.from(Item.class);
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Item, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("ctlgDate"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        selectExpressions.add(root.get("record").get("title"));
        selectExpressions.add(root.get("record").get("author"));
        selectExpressions.add(root.get("record").get("publisher"));
        selectExpressions.add(root.get("record").get("publicationYear"));
        selectExpressions.add(root.get("record").get("id"));
        selectExpressions.add(root.get("record").get("rn"));
        selectExpressions.add(root.get("ctlgNo"));
        selectExpressions.add(root.get("issueNo"));
        selectExpressions.add(root.get("price"));
        cq.distinct(true).multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        query.setFirstResult(searchDetailsRequest.getPage());
        query.setMaxResults(searchDetailsRequest.getSize());
        return query.getResultList();
    }

    private Long countDetailsItem(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Item> root = cq.from(Item.class);
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Item, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("ctlgDate"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        cq.select(cb.countDistinct(root.get("recordIdCtlgNoIssueNo"))).where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        return (Long)query.getSingleResult();
    }


    private List<Object[]> searchDetailsMembership(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Membership> root = cq.from(Membership.class);

        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Item, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(root.get(sc.getCoder().getName()).get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        if (searchDetailsRequest.isFirstMembership()){
            whereExpressions.add(cb.isTrue(root.get("firstTime")));
        }
        selectExpressions.add(root.get("member").get("id"));
        selectExpressions.add(root.get("member").get("firstName"));
        selectExpressions.add(root.get("member").get("lastName"));
        selectExpressions.add(root.get("member").get("address"));
        selectExpressions.add(root.get("member").get("city"));
        selectExpressions.add(root.get("date"));
        selectExpressions.add(root.get("fee"));
        cq.distinct(true).multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        query.setFirstResult(searchDetailsRequest.getPage());
        query.setMaxResults(searchDetailsRequest.getSize());
        return query.getResultList();

    }

    private Long countDetailsMembership(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Membership> root = cq.from(Membership.class);
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Item, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(root.get(sc.getCoder().getName()).get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        if (searchDetailsRequest.isFirstMembership()){
            whereExpressions.add(cb.isTrue(root.get("firstTime")));
        }
        cq.select(cb.countDistinct(root)).where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        return (Long)query.getSingleResult();
     }


    private List<Object[]> searchDetailsLending(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Lending> root = cq.from(Lending.class);
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        List<Predicate> lendingAction = new ArrayList<>();

        for(SelectedCoder sc: sortSelectedCoders){
            Join<Lending, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        for (String l: searchDetailsRequest.getLendingAction()){
            lendingAction.add(cb.equal(root.get("lendingAction").get("id"),l));
        }
        whereExpressions.add(cb.or(lendingAction.toArray(new Predicate[0])));

        selectExpressions.add(root.get("record").get("title"));
        selectExpressions.add(root.get("record").get("author"));
        selectExpressions.add(root.get("record").get("publisher"));
        selectExpressions.add(root.get("record").get("publicationYear"));
        selectExpressions.add(root.get("member").get("id"));
        selectExpressions.add(root.get("member").get("firstName"));
        selectExpressions.add(root.get("member").get("lastName"));
        selectExpressions.add(root.get("member").get("address"));
        selectExpressions.add(root.get("member").get("city"));
        selectExpressions.add(root.get("ctlgNo"));
        selectExpressions.add(root.get("date"));

        cq.distinct(true).multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        query.setFirstResult(searchDetailsRequest.getPage());
        query.setMaxResults(searchDetailsRequest.getSize());
        return query.getResultList();

    }


    private Long countDetailsLending(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Lending> root = cq.from(Lending.class);
        List<Predicate> whereExpressions = new ArrayList<>();
        List<Predicate> lendingAction = new ArrayList<>();

        for(SelectedCoder sc: sortSelectedCoders){
            Join<Lending, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        for (String l: searchDetailsRequest.getLendingAction()){
            lendingAction.add(cb.equal(root.get("lendingAction").get("id"),l));
        }
        whereExpressions.add(cb.or(lendingAction.toArray(new Predicate[0])));

        cq.select(cb.countDistinct(root.get("ctlgNoDate"))).where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        return (Long)query.getSingleResult();

    }


}
