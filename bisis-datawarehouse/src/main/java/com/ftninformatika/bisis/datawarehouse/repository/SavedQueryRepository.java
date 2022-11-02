package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.SavedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("savedQueryJPARepository")
public interface SavedQueryRepository extends JpaRepository<SavedQuery,Integer> {

    Optional<List<SavedQuery>> findByLibrary(String library);
}
