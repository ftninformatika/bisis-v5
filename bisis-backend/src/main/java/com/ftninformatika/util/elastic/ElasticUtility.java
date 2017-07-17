package com.ftninformatika.util.elastic;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;

import java.util.ArrayList;

/**
 * Created by Petar on 7/17/2017.
 */
public class ElasticUtility {

    public static ArrayList<String> getIdsFromElasticIterable(Iterable<ElasticPrefixEntity> elasticResponse){
        ArrayList<String> retVal = new ArrayList<>();
        for (ElasticPrefixEntity ee: elasticResponse ){
            retVal.add(ee.getId());
        }
        return retVal;
    }



}
