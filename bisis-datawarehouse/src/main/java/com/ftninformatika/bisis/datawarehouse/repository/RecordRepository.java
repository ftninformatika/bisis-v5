package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("recordJPARepository")
public interface RecordRepository extends JpaRepository<Record,String> {

    @Transactional
    @Modifying
    @Query("delete from Record")
    public void recordDelete();
}
