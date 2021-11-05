refaktoringpackage com.ftninformatika.bisis.core.controllers;

import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.LocationRepository;
import com.ftninformatika.bisis.core.repositories.SubLocationRepository;
import com.ftninformatika.bisis.library_configuration.EnumLocationLevel;
import com.ftninformatika.bisis.library_configuration.LibConfigDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 6.9.2018..
 */
@RestController
@RequestMapping("/library_configuration")
public class LibraryConfigurationController {
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired
    SubLocationRepository subLocationRepository;
    @Autowired
    LocationRepository locationRepository;

    @RequestMapping(path = "findAllByLibraryNameNotLike")
    public List<LibraryConfiguration> getConfigs(String libName){

        return libraryConfigurationRepository.findAllByLibraryNameNotLike(libName);
    }

    @GetMapping(path = "forLib")
    public ResponseEntity<LibraryConfiguration> getLibraryConfiguration(@RequestParam("libName") String libName){

        LibraryConfiguration lc = libraryConfigurationRepository.getByLibraryName(libName);
        if (lc != null)
            return ResponseEntity.ok(lc);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("allConfigsBrief")
    public ResponseEntity<List<LibConfigDTO>> getAllConfigsBrief() {
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findAll();
        if (libraryConfigurations == null || libraryConfigurations.size() == 0)
            return ResponseEntity.noContent().build();
        List<LibConfigDTO> retVal = libraryConfigurations.stream()
                .map(lc -> new LibConfigDTO(lc.getLibraryName(), lc.getLibraryFullName(), lc.getShortName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retVal);
    }

    @GetMapping("mobileSupported")
    public ResponseEntity<List<LibConfigDTO>> getConfigsMobileSupported() {
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileAppIsTrue();
        if (libraryConfigurations == null || libraryConfigurations.size() == 0)
            return ResponseEntity.noContent().build();
        List<LibConfigDTO> retVal = new ArrayList<LibConfigDTO>();
        List<Sublocation> sublocations;
         for (LibraryConfiguration lc: libraryConfigurations){
             if (lc.getLocationLevel()==null){
                 lc.setLocationLevel(1);
             }
             if (lc.getLocationLevel() == 1) {
                 sublocations = locationRepository.getCoders(lc.getLibraryName()).stream()
                 .map(l->new Sublocation(l.get_id(),l.getLibrary(),l.getCoder_id(), LatCyrUtils.toCyrillic(l.getDescription()))).collect(Collectors.toList());
             }else{
                 sublocations = subLocationRepository.getCoders(lc.getLibraryName()).stream()
                         .map(l->new Sublocation(l.get_id(),l.getLibrary(),l.getCoder_id(), LatCyrUtils.toCyrillic(l.getDescription()))).collect(Collectors.toList());

             }
             retVal.add(new LibConfigDTO(lc.getLibraryName(),lc.getLibraryFullName(),lc.getShortName(),lc.getLocationLevel(),sublocations));

         }
        return ResponseEntity.ok(retVal);
    }

    @RequestMapping(path = "getLocationLevel")
    public Integer getLocationLevel(@RequestHeader("Library") String library){
        if (library == null) {
            return null;
        }
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        if (config.getLocationLevel() == null || EnumLocationLevel.LOCATION.getLevel() == config.getLocationLevel()) {
            return EnumLocationLevel.LOCATION.getLevel();
        } else {
            return EnumLocationLevel.SUB_LOCATION.getLevel();
        }
    }
}
