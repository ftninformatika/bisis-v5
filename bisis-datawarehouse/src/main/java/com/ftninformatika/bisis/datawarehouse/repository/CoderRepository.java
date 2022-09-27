package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CoderRepository<T extends Coder> extends JpaRepository<T,String> {

    public List<T> findByLibraryIsNullOrLibrary(String library);
}
