package com.ftninformatika.bisis.rest_service.repository.mongo;


import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.search.SearchModelMember;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dboberic on 22/11/2017.
 */
public interface MemberRepositoryCustom {
    public List<Member> getMembersFilteredByLending(SearchModelMember searchModel, List userIds);
    public List<Member> getSignedMembers(Date startDate, Date endDate, String location,String sortBy);
    public List<Member> getSignedCorporateMembers(Date startDate, Date endDate,String institution, String location);
    public List<Report> groupMemberByField(Date startDate, Date endDate, String location, String groupByField);
    public Map<String, Integer> getLibrarianSignedCount(Date start, Date end, String location);
    public List<Member> getBlockedMembers(String location);
    public Long getFreeSigningMembersCount(Date start, Date end, String location);
    public Integer getUserSignedCount(Date start, Date end, String location);
    public Report getPictureBooksReport(Date start, Date end, String location);
}
