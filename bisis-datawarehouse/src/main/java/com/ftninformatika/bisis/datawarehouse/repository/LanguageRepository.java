package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Language;
import org.springframework.stereotype.Repository;

@Repository("languageJPARepository")
public interface LanguageRepository extends CoderRepository<Language> {
}
