package com.ftninformatika.bisis.rest_service.repository.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Created by Petar on 7/5/2017.
 */
@RepositoryRestResource(collectionResourceRel = "elastic_records", path = "elastic_records")
@Lazy
public interface ElasticRecordsRepository extends ElasticsearchRepository<ElasticPrefixEntity, String>, PagingAndSortingRepository<ElasticPrefixEntity, String> {

}
