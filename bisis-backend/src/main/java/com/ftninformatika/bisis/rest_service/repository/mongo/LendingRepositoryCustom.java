package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.circ.Lending;

import java.util.Date;
import java.util.List;

/**
 * Created by dboberic on 17/11/2017.
 */
public interface LendingRepositoryCustom {
    public List<String> getLendingsCtlgNo(Date startL, Date endL, Date startR, Date endR, String location);

    public List<String> getLendingsUserId(String ctlgNo, String librarianLend, String librarianReturn, String location,
                                   Date lendDateStart, Date lendDateEnd, Date returnDateStart, Date returnDateEnd,
                                   Date deadlineStart, Date deadlineEnd);

    public List<Lending> getLenignsWithAnyActivityOnDate(Date dateOfActivity, String location);
}
