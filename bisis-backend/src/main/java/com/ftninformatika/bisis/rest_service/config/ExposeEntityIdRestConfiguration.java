package com.ftninformatika.bisis.rest_service.config;

import com.ftninformatika.bisis.librarian.Librarian;
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
        config.exposeIdsFor(Librarian.class);
    }
}
