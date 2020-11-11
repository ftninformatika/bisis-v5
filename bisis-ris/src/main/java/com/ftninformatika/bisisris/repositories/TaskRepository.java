package com.ftninformatika.bisisris.repositories;

import com.ftninformatika.bisisris.models.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {

    public List<Task> getTaskByLibrarianAndDateOfServiceAfter(String librarian, Date dateOfService, Sort sort);
}
