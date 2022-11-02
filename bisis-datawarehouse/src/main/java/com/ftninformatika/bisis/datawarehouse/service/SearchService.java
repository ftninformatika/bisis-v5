package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import com.ftninformatika.bisis.datawarehouse.entity.Item;
import com.ftninformatika.bisis.datawarehouse.entity.Lending;
import com.ftninformatika.bisis.datawarehouse.entity.Membership;
import com.ftninformatika.bisis.datawarehouse.model.SearchRequest;
import com.ftninformatika.bisis.datawarehouse.model.SearchType;
import com.ftninformatika.bisis.datawarehouse.model.SelectedCoder;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    public List<Object[]> search(SearchRequest searchRequest){
        switch (searchRequest.getType()) {
            case SearchType.ITEM:
                return searchItem(searchRequest);
            case SearchType.LENDING:
                return searchLending(searchRequest);
            case SearchType.MEMBERSHIP:
                return searchMembership(searchRequest);
        }
        return null;
    }

    private List<Object[]> searchItem(SearchRequest searchRequest){
        List<SelectedCoder> selectedCoders = searchRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Item> root = cq.from(Item.class);
        List<Expression<?>> groupByExpressions = new ArrayList<>();
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Item, Coder> join = root.join(sc.getCoder().getName());
            selectExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("id"));
            whereExpressions.add(cb.in(root.get(sc.getCoder().getName()).get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        whereExpressions.add(cb.between(root.get("ctlgDate"), searchRequest.getStartDate(), searchRequest.getEndDate()));
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));

        selectExpressions.add(cb.countDistinct(root.get("record")));
        selectExpressions.add(cb.countDistinct(root.get("ctlgNo")));
        selectExpressions.add(cb.countDistinct(root.get("issueNo")));
        selectExpressions.add(cb.sumAsDouble(root.get("price")));
        cq.multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0])).
                groupBy(groupByExpressions);
        return em.createQuery(cq).getResultList();
    }

    private List<Object[]> searchLending(SearchRequest searchRequest){
        List<SelectedCoder> selectedCoders = searchRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Lending> root = cq.from(Lending.class);
        List<Expression<?>> groupByExpressions = new ArrayList<>();
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        List<Predicate> lendingAction = new ArrayList<>();

        for(SelectedCoder sc: sortSelectedCoders){
            Join<Lending, Coder> join = root.join(sc.getCoder().getName());
            selectExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("id"));
            whereExpressions.add(cb.in(root.get(sc.getCoder().getName()).get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        whereExpressions.add(cb.between(root.get("date"), searchRequest.getStartDate(), searchRequest.getEndDate()));
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        for (String l: searchRequest.getLendingAction()){
            lendingAction.add(cb.equal(root.get("lendingAction").get("id"),l));
        }

        whereExpressions.add(cb.or(lendingAction.toArray(new Predicate[0])));

        selectExpressions.add(cb.countDistinct(root.get("member")));
        selectExpressions.add(cb.countDistinct(root.get("ctlgNoDate")));
        selectExpressions.add(cb.countDistinct(root.get("ctlgNo")));
        cq.multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0])).
                groupBy(groupByExpressions);
        return em.createQuery(cq).getResultList();
    }

    private List<Object[]> searchMembership(SearchRequest searchRequest){
        List<SelectedCoder> selectedCoders = searchRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Membership> root = cq.from(Membership.class);
        List<Expression<?>> groupByExpressions = new ArrayList<>();
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Membership, Coder> join = root.join(sc.getCoder().getName());
            selectExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("description"));
            groupByExpressions.add(join.get("id"));
            whereExpressions.add(cb.in(root.get(sc.getCoder().getName()).get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        whereExpressions.add(cb.between(root.get("date"), searchRequest.getStartDate(), searchRequest.getEndDate()));
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        if (searchRequest.isFirstMembership()){
            whereExpressions.add(cb.isTrue(root.get("firstTime")));
        }

        selectExpressions.add(cb.countDistinct(root.get("member")));
        selectExpressions.add(cb.countDistinct(root.get("membershipId")));
        selectExpressions.add(cb.sumAsDouble(root.get("fee")));
        cq.multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0])).
                groupBy(groupByExpressions);
        return em.createQuery(cq).getResultList();
    }
}
