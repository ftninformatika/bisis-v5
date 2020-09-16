package com.ftninformatika.bisis.rest_service.config;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.registry.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by Petar on 7/26/2017.
 */
@Configuration
public class ExposeEntityIdRestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
        config.exposeIdsFor(LibrarianDB.class)
              .exposeIdsFor(Record.class)
              .exposeIdsFor(Member.class)
              .exposeIdsFor(Membership.class)
              .exposeIdsFor(CircLocation.class)
              .exposeIdsFor(ItemAvailability.class)
              .exposeIdsFor(Lending.class)
              .exposeIdsFor(ProcessTypeDB.class)
              .exposeIdsFor(ElasticPrefixEntity.class)
              .exposeIdsFor(CircConfig.class)
              .exposeIdsFor(Organization.class)
              .exposeIdsFor(GenericRegistry.class);
    }
}
