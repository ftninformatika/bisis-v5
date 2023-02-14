package com.ftninformatika.bisis.core.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.coders.definition.CoderDefinition;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Getter
public class CoderDefinitionConfig {
    List<CoderDefinition> circulationCoders;
    List<CoderDefinition> recordCoders;
    List<CoderDefinition> allCoders;

    public CoderDefinitionConfig(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file1 = ResourceUtils.getFile("classpath:coders/circulationCoders.json");
            File file2 = ResourceUtils.getFile("classpath:coders/recordCoders.json");

            circulationCoders = mapper.readValue(file1, new TypeReference<List<CoderDefinition>>(){});
            recordCoders = mapper.readValue(file2, new TypeReference<List<CoderDefinition>>(){});
            allCoders =circulationCoders;
            allCoders.addAll(recordCoders);
        }catch (Exception e){

        }
    }
    public List<CoderDefinition> getCircCoderDefinitions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:coders/circulationCoders.json");
        List<CoderDefinition> map = mapper.readValue(file, new TypeReference<List<CoderDefinition>>(){});
        return map;
    }
    @Bean
    public List<CoderDefinition> getRecordCoderDefinitions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:coders/recordCoders.json");
        List<CoderDefinition> map = mapper.readValue(file, new TypeReference<List<CoderDefinition>>(){});
        return map;
    }

    @Bean
    public List<CoderDefinition> getCoderDefinitions()throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        File file1 = ResourceUtils.getFile("classpath:coders/circulationCoders.json");
        File file2 = ResourceUtils.getFile("classpath:coders/recordCoders.json");

        List<CoderDefinition> coders = mapper.readValue(file1, new TypeReference<List<CoderDefinition>>(){});
        List<CoderDefinition> recordCoders = mapper.readValue(file2, new TypeReference<List<CoderDefinition>>(){});
        coders.addAll(recordCoders);
        return  coders;

    }
}
