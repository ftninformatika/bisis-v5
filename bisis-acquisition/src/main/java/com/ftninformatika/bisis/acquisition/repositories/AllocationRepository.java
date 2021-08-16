package com.ftninformatika.bisis.acquisition.repositories;

import com.ftninformatika.bisis.acquisition.model.Allocation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AllocationRepository extends MongoRepository<Allocation,String> {
}
