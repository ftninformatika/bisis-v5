package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.circ.CircLocation;

import java.util.List;

public interface CircLocationRepository extends CoderRepository<CircLocation>{


    public List<CircLocation> findByLocationCodeAndLibrary(String location, String lib);
    CircLocation getByDescriptionAndLibrary(String desc, String lib);

}
