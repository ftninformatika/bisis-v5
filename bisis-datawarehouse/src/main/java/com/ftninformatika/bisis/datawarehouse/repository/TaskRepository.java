package com.ftninformatika.bisis.datawarehouse.repository;

import com.ftninformatika.bisis.datawarehouse.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("taskJPARepository")
public interface TaskRepository extends JpaRepository<Task,Integer> {
}
