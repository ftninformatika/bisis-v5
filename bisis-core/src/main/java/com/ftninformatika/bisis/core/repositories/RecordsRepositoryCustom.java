package com.ftninformatika.bisis.core.repositories;

import java.util.List;

public interface RecordsRepositoryCustom {

    public List<Integer> findInvNumHoles(String invFrom, String invTo);
}
