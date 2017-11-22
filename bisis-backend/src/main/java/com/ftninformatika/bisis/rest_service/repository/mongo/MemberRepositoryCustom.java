package com.ftninformatika.bisis.rest_service.repository.mongo;


import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.search.SearchModelMember;

import java.util.List;

/**
 * Created by dboberic on 22/11/2017.
 */
public interface MemberRepositoryCustom {
    public List<Member> getMembersFilteredByLending(SearchModelMember searchModel, List userIds);
}
