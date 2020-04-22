package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.util.elastic.ElasticUtility;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author badf00d21  18.7.19.
 */
@Service
public class OpacAutocompleteService {

    @Autowired ElasticsearchTemplate elasticsearchTemplate;

    public List<PrefixValue> getAutocompleteResults(String query) {
        List<PrefixValue> retVal = new ArrayList<>();
        Set<String> distinctValSet = new HashSet<>();
        if (query == null || query.length() < 3)
            return retVal;

        query = LatCyrUtils.toLatinUnaccented(query);

        for (String prefix : ElasticUtility.AUTOCOMPLETE_PREFIXES) {
            Pageable pageable = PageRequest.of(0, 5000);
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(ElasticUtility.makeExpandQuery(prefix, query))
                    .withPageable(pageable)
                    .build();
            CloseableIterator<ElasticPrefixEntity> streamEs = elasticsearchTemplate.stream(searchQuery, ElasticPrefixEntity.class);
            while (streamEs.hasNext()) {
                ElasticPrefixEntity e = streamEs.next();
                if (e.getPrefixes().get(prefix) == null)
                    continue;
                for (int i = 0; i < e.getPrefixes().get(prefix).size(); i++) {
                    try {
                        String val = e.getPrefixes().get(prefix).get(i);
                        if (val.startsWith(query) && !distinctValSet.contains(val)) {
                            retVal.add(new PrefixValue(prefix, e.getPrefixes().get(prefix + ElasticUtility.RAW_SUFFIX).get(i)));
                            distinctValSet.add(val);
                        }
                    } catch (Exception e1) {
                        continue;
                    }
                }
            }
        }
        return retVal;
    }

}
