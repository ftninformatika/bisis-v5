package com.ftninformatika.bisis.rest_service.repository.mongo;

import java.util.Date;
import java.util.List;

/**
 * Created by dboberic on 17/11/2017.
 */
public interface LendingRepositoryCustom {
    public List getLendingsCtlgNo(Date startL, Date endL, Date startR, Date endR, String location);

    }
