package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Category;
import org.springframework.stereotype.Repository;

@Repository("categoryJPARepository")
public interface CategoryRepository extends CoderRepository<Category> {
}
