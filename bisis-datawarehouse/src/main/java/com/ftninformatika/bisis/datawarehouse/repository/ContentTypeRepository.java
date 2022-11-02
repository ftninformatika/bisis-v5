package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.ContentType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contentTypeJPARepository")
public interface ContentTypeRepository extends CoderRepository<ContentType> {

    List<ContentType> findByIdIn(List<String> contentTypes);
}
