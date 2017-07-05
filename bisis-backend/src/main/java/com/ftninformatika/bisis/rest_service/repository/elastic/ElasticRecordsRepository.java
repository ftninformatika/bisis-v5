package com.ftninformatika.bisis.rest_service.repository.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Petar on 7/5/2017.
 */
@RepositoryRestResource(collectionResourceRel = "elastic_records", path = "elastic_records")
public interface ElasticRecordsRepository extends ElasticsearchRepository<ElasticPrefixEntity, String> {
}
