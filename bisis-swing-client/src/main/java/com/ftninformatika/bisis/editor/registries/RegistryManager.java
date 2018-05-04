package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.registry.*;

import java.io.IOException;

public class RegistryManager {

    public static Registry addRegistry(RegistryItem item, int registryType) throws IOException {
        Registry retVal = null;
        switch (registryType) {
            case Registries.ODREDNICE: {
                RegPrOd regPrOd = new RegPrOd();
                regPrOd.setPojam(item.getText1());
                retVal = BisisApp.bisisService.addUpdateRegPrOd(regPrOd).execute().body();
            } return retVal;
            case Registries.PODODREDNICE: {
                RegPrPod regPrPod = new RegPrPod();
                regPrPod.setPojam(item.getText1());
                retVal = BisisApp.bisisService.addUpdateRegPrPod(regPrPod).execute().body();
            }; return retVal;
            case Registries.KOLEKTIVNI: {
                RegKolOdr obj = new RegKolOdr();
                obj.setKolektivac(item.getText1());
                retVal = BisisApp.bisisService.addRegKolOdr(obj).execute().body();
            }; return retVal;
            case Registries.ZBIRKE: {
                RegZbirke obj = new RegZbirke();
                obj.setNaziv(item.getText1());
                retVal = BisisApp.bisisService.addRegZbirke(obj).execute().body();
            }; return retVal;
            case Registries.UDK: {
                RegUDKSubgroup obj = new RegUDKSubgroup();
                obj.setGrupa(item.getText1());
                obj.setOpis(item.getText2());
                retVal = BisisApp.bisisService.addRegUDKS(obj).execute().body();
            }; return retVal;
            case Registries.AUTORI: {
                RegAutOdr obj = new RegAutOdr();
                obj.setAutor(item.getText1());
                obj.setOriginal(item.getText2());
                retVal = BisisApp.bisisService.addRegAuthor(obj).execute().body();
            }; return retVal;
        }
        return retVal;
    }

    public static void deleteRegistry(Registry reg) throws IOException {
        if (reg instanceof RegPrOd)
            BisisApp.bisisService.deleteRegProD((RegPrOd) reg).execute().body();
        if (reg instanceof RegPrPod)
            BisisApp.bisisService.deleteRegPrPod((RegPrPod) reg).execute().body();
        if (reg instanceof RegKolOdr)
            BisisApp.bisisService.deleteRegKolOdr((RegKolOdr) reg).execute().body();
        if (reg instanceof RegZbirke)
            BisisApp.bisisService.deleteRegZbirke((RegZbirke) reg).execute().body();
        if (reg instanceof RegUDKSubgroup)
            BisisApp.bisisService.deleteRegUDKS((RegUDKSubgroup) reg).execute().body();
        if (reg instanceof RegAutOdr)
            BisisApp.bisisService.deleteRegAuthor((RegAutOdr) reg).execute().body();
    }

    public static void updateRegistry(Registry reg) throws IOException {
        if (reg instanceof RegPrOd)
            BisisApp.bisisService.addUpdateRegPrOd((RegPrOd) reg).execute().body();
        if (reg instanceof RegPrPod)
            BisisApp.bisisService.addUpdateRegPrPod((RegPrPod)reg).execute();
        if (reg instanceof RegKolOdr)
            BisisApp.bisisService.addRegKolOdr((RegKolOdr)reg).execute();
        if (reg instanceof RegZbirke)
            BisisApp.bisisService.addRegZbirke((RegZbirke)reg).execute();
        if (reg instanceof RegUDKSubgroup)
            BisisApp.bisisService.addRegUDKS((RegUDKSubgroup)reg).execute();
        if (reg instanceof RegAutOdr)
            BisisApp.bisisService.addRegAuthor((RegAutOdr)reg).execute();
    }
}
