package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Librarian;
import org.springframework.stereotype.Repository;

@Repository("librarianJPARepository")
public interface LibrarianRepository extends CoderRepository<Librarian>{
}
