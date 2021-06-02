package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.records.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RescartaMetadataExportService {

    private final RecordsRepository recordsRepository;

    @Autowired
    public RescartaMetadataExportService(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public Record getRecordByRn(int rn) {
        return this.recordsRepository.getByRn(rn);
    }
}
