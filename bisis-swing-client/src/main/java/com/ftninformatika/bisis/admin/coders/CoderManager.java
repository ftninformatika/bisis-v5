package com.ftninformatika.bisis.admin.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Place;

import java.io.IOException;
import java.util.ArrayList;

public class CoderManager {

        public static ArrayList<Object> insertCoder(ArrayList<Object> row, String coderName) throws IOException {
            ArrayList<Object> retVal = null;

            switch (coderName) {
                case "places": {
                    Place p = new Place();
                    p.set_id(null);
                    p.setLibrary(BisisApp.appConfig.getLibrary());
                    p.setZip(String.valueOf(row.get(1)));
                    p.setCity(String.valueOf(row.get(2)));
                    retVal = BisisApp.bisisService.insertEditPlace(p).execute().body();
                }
            }
            return retVal;
        }

        public static ArrayList<Object> updateCoder(ArrayList<Object> row, String coderName) throws IOException {
            ArrayList<Object> retVal = null;

            switch (coderName) {
                case "places": {
                    Place p = new Place();
                    p.set_id(String.valueOf(row.get(0)));
                    p.setLibrary(BisisApp.appConfig.getLibrary());
                    p.setZip(String.valueOf(row.get(1)));
                    p.setCity(String.valueOf(row.get(2)));
                    retVal = BisisApp.bisisService.insertEditPlace(p).execute().body();
                }
            }
            return retVal;
        }

        public static Boolean deleteCoder(String _id, String coderName) throws IOException {
            Boolean retVal = false;

            switch (coderName) {
                case "places": {
                    retVal = BisisApp.bisisService.deletePlace(_id).execute().body();
                }
            }
            return retVal;
        }

}
