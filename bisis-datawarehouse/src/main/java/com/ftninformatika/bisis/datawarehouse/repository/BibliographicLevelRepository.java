package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.BibliographicLevel;
import org.springframework.stereotype.Repository;

@Repository("bibliographicLevelJPARepository")
public interface BibliographicLevelRepository extends CoderRepository<BibliographicLevel> {
}
