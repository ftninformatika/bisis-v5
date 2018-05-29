package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.registry.*;

import java.io.IOException;

public class RegistryManager {

    public static GenericRegistry addRegistry(RegistryItem item, int registryType) throws IOException {
        GenericRegistry retVal = new GenericRegistry();

        if (registryType == Registries.AUTORI || registryType == Registries.UDK) {
            retVal.setCode(registryType);
            retVal.setField1(item.getText1());
            retVal.setField2(item.getText2());
        }
        else {
            retVal.setCode(registryType);
            retVal.setField1(item.getText1());
        }

        retVal = BisisApp.bisisService.addRegistryForType(retVal).execute().body();

        return retVal;
    }

    public static void deleteRegistry(GenericRegistry reg) throws IOException {
        BisisApp.bisisService.deleteRegistryForType(reg).execute().body();
    }

    public static void updateRegistry(GenericRegistry reg) throws IOException {
        BisisApp.bisisService.addRegistryForType(reg).execute().body();
    }
}
