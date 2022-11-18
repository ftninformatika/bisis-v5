package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("lendingJPARepository")
public interface LendingRepository extends JpaRepository<Lending,Integer> {

     @Transactional
     void deleteByLibrary(String library);

}