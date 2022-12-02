package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("itemJPARepository")
public interface ItemRepository extends JpaRepository<Item,Integer>{
     List<Item> findByCtlgNoAndLibrary(String ctlgno, String library);
}
