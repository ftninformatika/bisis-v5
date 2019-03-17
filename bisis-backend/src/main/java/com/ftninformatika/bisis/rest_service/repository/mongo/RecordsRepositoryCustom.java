package com.ftninformatika.bisis.rest_service.repository.mongo;

import java.util.List;

public interface RecordsRepositoryCustom {

    public List<Integer> findInvNumHoles(String invFrom, String invTo);
}
