package com.ftninformatika.bisis.circ.manager;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.warnings.Counters;
import com.ftninformatika.bisis.circ.warnings.WarningsFrame;
import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.models.circ.WarningType;
import com.ftninformatika.bisis.models.circ.pojo.CircLocation;
import com.ftninformatika.bisis.models.circ.pojo.Warning;
import com.ftninformatika.bisis.models.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.mongodb.repository.query.QueryUtils;


public class WarningsManager {

	public WarningsManager(){

	}
	
  public void loadCombos(WarningsFrame warnings) throws Exception{
      List data = (BisisApp.appConfig.getCodersHelper()
              .getCircLocations().values().stream()
              .map(i -> {
                  com.ftninformatika.bisis.models.circ.pojo.CircLocation l = new com.ftninformatika.bisis.models.circ.pojo.CircLocation();
                  l.setDescription(i.getDescription());
                  l.setLocationCode(i.getLocationCode());
                  return l;

              })
              .collect(Collectors.toList()));
      Utils.loadCombo(warnings.getCmbBranch(), data);

      data = BisisApp.appConfig.getCodersHelper()
              .getWarningTypes().values().stream()
              .map(i -> i.getDescription())
              .collect(Collectors.toList());

	Utils.loadCombo(warnings.getCmbType(), data);
  }
  
  public Counters getCounters(WarningType warn_type){
  	//TODO ucitati counters za warn_type
//  	List data = BisisApp.appConfig.getCodersHelper()
//            .getWarningCounters().values().stream()
//            .map(i -> i)
//            .collect(Collectors.toList());
//    return new Counters(data, warn_type);
    return null;
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
//    GetWarnUsersCommand getUsers = new GetWarnUsersCommand(startDate, endDate, loc);
//    getUsers = (GetWarnUsersCommand)service.executeCommand(getUsers);
//    if (getUsers == null)
//    	return null;
//    return getUsers.getList();
    //TODO kreirati servis
    return null;
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
  
  public boolean saveWarnings(List<Warning> warnings, Counters counters){
//  	SaveWarningsCommand save = new SaveWarningsCommand(warnings, counters.getList());
//    save = (SaveWarningsCommand)service.executeCommand(save);
//    return save.isSaved();

    //TODO kreirati servis
    return false;
  }
  
  public List<Object[]> getHistory(Date startDate, Date endDate, WarningType wtype){
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
//  	GetWarnHistoryCommand getHistory = new GetWarnHistoryCommand(startDate, endDate, wtype);
//  	getHistory = (GetWarnHistoryCommand)service.executeCommand(getHistory);
//  	if (getHistory == null)
//  		return null;
//    return getHistory.getList();
    //TODO kreirati servis
    return null;
  }
  
  public boolean saveWarnTypes(WarningType wtype){
//  	SaveObjectCommand save = new SaveObjectCommand(wtype);
//    save = (SaveObjectCommand)service.executeCommand(save);
//    return save.isSaved();
    //TODO kreirati servis
    return false;
  }

}
