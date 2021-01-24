package com.ftninformatika.bisis.nabavka.repositories;

import com.ftninformatika.bisis.nabavka.model.Allocation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AllocationRepository extends MongoRepository<Allocation,String> {
}
