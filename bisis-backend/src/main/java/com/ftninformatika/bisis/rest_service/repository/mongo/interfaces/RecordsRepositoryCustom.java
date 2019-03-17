package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;

import java.util.List;

public interface RecordsRepositoryCustom {

    public List<Integer> findInvNumHoles(String invFrom, String invTo);
}
