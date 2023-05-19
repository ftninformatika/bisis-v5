package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Binding;
import org.springframework.data.mongodb.repository.Query;

public interface BindingRepository extends CoderRepository<Binding> {

    @Query("{'$or': [{'library':?0},{'coder_id':?1}]}")
    Binding getCoder(String library, String code);
}
