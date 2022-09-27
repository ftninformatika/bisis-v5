package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Country;
import org.springframework.stereotype.Repository;

@Repository("countryJPARepository")
public interface CountryRepository extends CoderRepository<Country> {
}
