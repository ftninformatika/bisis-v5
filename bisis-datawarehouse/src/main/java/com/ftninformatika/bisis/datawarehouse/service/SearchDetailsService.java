package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.datawarehouse.entity.*;
import com.ftninformatika.bisis.datawarehouse.model.SearchDetailsRequest;
import com.ftninformatika.bisis.datawarehouse.model.SearchType;
import com.ftninformatika.bisis.datawarehouse.model.SelectedCoder;
import com.ftninformatika.utils.LibraryPrefixProvider;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchDetailsService {

    @PersistenceContext
    EntityManager em;

    @Value("${reports.swapDir}")
    private String swapDir;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;




    public List<Object[]> searchDetails(SearchDetailsRequest searchDetailsRequest) {
        switch (searchDetailsRequest.getType()) {
            case SearchType.ITEM:
                return searchDetailsItem(searchDetailsRequest);
            case SearchType.TASK:
                return searchDetailsTask(searchDetailsRequest);
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
            case SearchType.TASK:
                return countDetailsTask(searchDetailsRequest);
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
        selectExpressions.add(root.get("signature"));
        selectExpressions.add(root.get("ctlgNo"));
        selectExpressions.add(root.get("issueNo"));
        selectExpressions.add(root.get("price"));
        cq.distinct(true).multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        if(searchDetailsRequest.getPage()!=-1){
            query.setFirstResult(searchDetailsRequest.getPage());
            query.setMaxResults(searchDetailsRequest.getSize());
        }

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
    private List<Object[]> searchDetailsTask(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Task> root = cq.from(Task.class);
        List<Selection<?>> selectExpressions = new ArrayList<>();
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Task, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        selectExpressions.add(root.get("record").get("title"));
        selectExpressions.add(root.get("record").get("author"));
        selectExpressions.add(root.get("record").get("publisher"));
        selectExpressions.add(root.get("record").get("publicationYear"));
        selectExpressions.add(root.get("record").get("id"));
        selectExpressions.add(root.get("record").get("rn"));
        selectExpressions.add(root.get("action").get("description"));
        selectExpressions.add(root.get("amount"));
        cq.distinct(true).multiselect(selectExpressions).
                where(whereExpressions.toArray(new Predicate[0]));
        Query query = em.createQuery(cq);
        if(searchDetailsRequest.getPage()!=-1){
            query.setFirstResult(searchDetailsRequest.getPage());
            query.setMaxResults(searchDetailsRequest.getSize());
        }

        return query.getResultList();
    }
    private Long countDetailsTask(SearchDetailsRequest searchDetailsRequest) {
        List<SelectedCoder> selectedCoders = searchDetailsRequest.getCoders();
        List<SelectedCoder> sortSelectedCoders = selectedCoders.stream().sorted(Comparator.comparing(SelectedCoder::getIndex)).collect(Collectors.toList());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> root = cq.from(Task.class);
        List<Predicate> whereExpressions = new ArrayList<>();
        for(SelectedCoder sc: sortSelectedCoders){
            Join<Task, Coder> join = root.join(sc.getCoder().getName());
            whereExpressions.add(cb.in(join.get("id")).value(sc.getCoderValues().stream().map(Coder::getId).collect(Collectors.toList())));
        }
        if (!searchDetailsRequest.isAllData()) {
            whereExpressions.add(cb.between(root.get("date"), searchDetailsRequest.getStartDate(), searchDetailsRequest.getEndDate()));
        }
        whereExpressions.add(cb.equal(root.get("library"),libraryPrefixProvider.getLibPrefix()));
        cq.select(cb.countDistinct(root.get("taskId"))).where(whereExpressions.toArray(new Predicate[0]));
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
        if(searchDetailsRequest.getPage()!=-1) {
            query.setFirstResult(searchDetailsRequest.getPage());
            query.setMaxResults(searchDetailsRequest.getSize());
        }
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
        if(searchDetailsRequest.getPage()!=-1) {
            query.setFirstResult(searchDetailsRequest.getPage());
            query.setMaxResults(searchDetailsRequest.getSize());
        }
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
    public JasperPrint exportDetails(SearchDetailsRequest searchDetailsRequest){
        try {
            JRSwapFileVirtualizer virtualizer = new JRSwapFileVirtualizer(200, new JRSwapFile(swapDir, 4096, 1024), true);
            List<Object[]> searchDetailsResult = searchDetails(searchDetailsRequest);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(searchDetailsResult);
            InputStream inputStream = null;
            if (searchDetailsRequest.getType().equals(SearchType.ITEM)) {
                inputStream = SearchDetailsService.class.getResourceAsStream("/jaspers/detailsItemReportSheet.jasper");
            }else if (searchDetailsRequest.getType().equals(SearchType.LENDING)) {
                inputStream = SearchDetailsService.class.getResourceAsStream("/jaspers/detailsLendingReportSheet.jasper");
            } else if (searchDetailsRequest.getType().equals(SearchType.MEMBERSHIP)){
                inputStream =  SearchDetailsService.class.getResourceAsStream("/jaspers/detailsMemberReportSheet.jasper");
            }
            else if (searchDetailsRequest.getType().equals(SearchType.TASK)){
                inputStream =  SearchDetailsService.class.getResourceAsStream("/jaspers/detailsObradaReportSheet.jasper");
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
            String libraryFullName = libraryConfigurationRepository.getByLibraryName(libraryPrefixProvider.getLibPrefix()).getLibraryFullName();
            params.put("library", libraryFullName);
            params.put("query",searchDetailsRequest.toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
            inputStream.close();
            return jasperPrint;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
