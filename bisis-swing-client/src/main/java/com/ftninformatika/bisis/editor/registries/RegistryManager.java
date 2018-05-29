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
//        if (reg instanceof RegPrOd)
//            BisisApp.bisisService.addUpdateRegPrOd((RegPrOd) reg).execute().body();
//        else
//            if (reg instanceof RegPrPod)
//            BisisApp.bisisService.addUpdateRegPrPod((RegPrPod)reg).execute();
//        else
//            if (reg instanceof RegKolOdr)
//            BisisApp.bisisService.addRegKolOdr((RegKolOdr)reg).execute();
//        else
//            if (reg instanceof RegZbirke)
//            BisisApp.bisisService.addRegZbirke((RegZbirke)reg).execute();
//        else
//            if (reg instanceof RegUDKSubgroup)
//            BisisApp.bisisService.addRegUDKS((RegUDKSubgroup)reg).execute();
//        else
//            if (reg instanceof RegAutOdr)
//            BisisApp.bisisService.addRegAuthor((RegAutOdr)reg).execute();
        BisisApp.bisisService.addRegistryForType(reg).execute().body();
    }
}
