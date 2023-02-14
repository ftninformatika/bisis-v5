package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Location;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface LocationRepository extends CoderRepository<Location> {


    List<Location> findByLibrary(String library);

    Location getByDescriptionAndLibrary(String desc, String lib);

}
