package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("countriesJPARepository")
public interface CountryRepository extends CoderRepository<Country> {

    List<Country> findByIdIn(List<String> countries);
}
