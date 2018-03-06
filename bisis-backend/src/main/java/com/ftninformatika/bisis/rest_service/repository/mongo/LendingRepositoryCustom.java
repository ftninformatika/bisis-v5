package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.pojo.Report;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dboberic on 17/11/2017.
 */
public interface LendingRepositoryCustom {
    public List<String> getLendingsCtlgNo(Date startL, Date endL, Date startR, Date endR, String location);

    public List<String> getLendingsUserId(String ctlgNo, String librarianLend, String librarianReturn, String location,
                                          Date lendDateStart, Date lendDateEnd, Date returnDateStart, Date returnDateEnd,
                                          Date deadlineStart, Date deadlineEnd);

    public List<Lending> getLenignsWithAnyActivityOnDate(Date start,Date end, String location);

    public List<Object> getGroupByForLendingsBetweenDate(Date start, Date end, String location, String groupByField, String countFieldName, String sortByField, String byLendReturnResume, Integer listSize);

    public Map<String, Report> getLibrarianStatistic(Date start, Date end, String location);

    public Integer getLendMemberCountDistinctByDate(Date start, Date end, String location);
    public Integer getReturnMemberCountDistinctByDate(Date start, Date end, String location);

    public Integer getLendingCountBy(String dateField, Date start, Date end, String location, String library, boolean distinct);

    public List<Lending> getResumedLendings(Date start, Date end, String locaiton);
    public List<Lending> getCtlgnoUsrId(Date start, Date end, String location);
}