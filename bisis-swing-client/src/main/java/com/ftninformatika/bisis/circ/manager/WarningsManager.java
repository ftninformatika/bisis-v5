package com.ftninformatika.bisis.circ.manager;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.warnings.Counters;
import com.ftninformatika.bisis.circ.warnings.WarningsFrame;
import com.ftninformatika.bisis.circ.WarningType;
import com.ftninformatika.bisis.circ.pojo.CircLocation;
import com.ftninformatika.bisis.circ.wrappers.*;
import com.ftninformatika.bisis.circ.wrappers.WarningsData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.PathDate;


public class WarningsManager {

	public WarningsManager(){

	}
	
  public void loadCombos(WarningsFrame warnings) throws Exception{
      List data = (BisisApp.appConfig.getCodersHelper()
              .getCircLocations().stream()
              .map(i -> {
                  com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                  l.setDescription(i.getDescription());
                  l.setLocationCode(i.getLocationCode());
                  return l;

              })
              .collect(Collectors.toList()));
      Utils.loadCombo(warnings.getCmbBranch(), data);

      data = BisisApp.appConfig.getCodersHelper()
              .getWarningTypes().stream()
              .map(i -> i)
              .collect(Collectors.toList());

	Utils.loadCombo(warnings.getCmbType(), data);
  }
  
  public Counters getCounters(WarningType warn_type){
  	List data = BisisApp.appConfig.getCodersHelper()
            .getWarningCounters().stream()
            .map(i -> i)
            .collect(Collectors.toList());
    return new Counters(data, warn_type);
  }

  public void refreshWarningCounters() {
      BisisApp.appConfig.getCodersHelper().refreshWarningCounters();
  }
  
  public List<MemberData> getUsers(Date startDate, Date endDate, CircLocation loc){
    if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }

    List<MemberData> members = null;
    try {
      members = BisisApp.bisisService.getWarnMembers(new PathDate(startDate), new PathDate(endDate), loc.getDescription()).execute().body();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return members;
  }
  
  public Record getRecord(String ctlgno){
    Record record = null;
    try {
      record = BisisApp.bisisService.getRecordByCtlgNo(ctlgno).execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return record;
  }
  
  public boolean saveWarnings(List<Lending> warnings, Counters counters){
    boolean saved = false;
    try {
      WarningsData warningsData = new WarningsData(warnings, counters.getList());
      saved = BisisApp.bisisService.addWarnings(warningsData).execute().body();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return saved;
  }
  
  public List<MemberData> getHistory(Date startDate, Date endDate, WarningType wtype, String location){
  	if (startDate == null) {
      endDate = Utils.setMaxDate(endDate);
      startDate = Utils.setMinDate(endDate);
    } else if (endDate == null) {
      endDate = Utils.setMaxDate(startDate);
      startDate = Utils.setMinDate(startDate);
    } else {
      startDate = Utils.setMinDate(startDate);
      endDate = Utils.setMaxDate(endDate);
    }

      List<MemberData> members = null;
      try {
          members = BisisApp.bisisService.getWarnHistory(new PathDate(startDate), new PathDate(endDate), wtype.getDescription(), location).execute().body();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return members;
  }
  
  public WarningType saveWarnTypes(WarningType wtype){
      WarningType savedWarningType = null;
      try {
          savedWarningType = BisisApp.bisisService.addWarningType(wtype).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return savedWarningType;
  }

}
