package com.ftninformatika.bisis.circ.manager;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.commands.GetUserCommand;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.view.*;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.circ.Lending;
import com.ftninformatika.bisis.models.circ.Membership;
import com.ftninformatika.bisis.models.circ.pojo.Duplicate;
import com.ftninformatika.bisis.models.circ.pojo.PictureBook;
import com.ftninformatika.bisis.models.circ.pojo.Signing;
import com.ftninformatika.bisis.models.circ.pojo.Warning;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class UserManager {
	
  private Member userModel;
  private CorporateMember groupModel;
  private String chargedUser;
  private String chargeBook = "";
  private List warnings = null;
  private String env = null;
  private String validator = null;
  //private Service service;
 // private Service serviceArchive;
  private List children = null;
  private List removedchildren = null;
	
	public UserManager(){		
		/*service = Cirkulacija.getApp().getService();
		serviceArchive = Cirkulacija.getApp().getServiceArchive();*/
		children = new ArrayList();
		removedchildren = new ArrayList();
	}
	
	public String saveUser(Member user){
		/*if (user.getDirty() || !children.isEmpty() || !removedchildren.isEmpty()){
			SaveUserCommand saveUser = new SaveUserCommand();
			saveUser.setUserID(user.getMmbrship().getUserID());
			if (user.getDirty()){
				GetSysIDCommand sysidcomm = new GetSysIDCommand(user.getMmbrship().getUserID());
				sysidcomm = (GetSysIDCommand)service.executeCommand(sysidcomm);
				if (sysidcomm == null)
					return "Gre\u0161ka u konekciji s bazom podataka!";
				Integer sysid = sysidcomm.getSysID();
				if (userModel == null){
					if (sysid != null)
						return "Broj korisnika vec postoji!";
					userModel = new Users();		
				} else {
				  if (sysid != null && sysid.intValue() != userModel.getSysId())
					  return "Broj korisnika vec postoji!";
				}
				userModel = toObjectModel(user, userModel);
			}
			if (BisisApp.getINIFile().getBoolean("pincode", "enabled") && (userModel.getPass() == null || userModel.getPass().equals(""))){
				Integer sysid = new Integer(0);
				String pin = Utils.generatePin();
//				while (sysid != null){
//					pin = Utils.generatePin();
//					GetPinCommand pincomm = new GetPinCommand(pin);
//					pincomm = (GetPinCommand)service.executeCommand(pincomm);
//					if (pincomm == null)
//						return "Gre\u0161ka u konekciji s bazom podataka!";
//					sysid = pincomm.getSysID();
//				}
				userModel.setPass(pin);
				user.getUserData().setPinCode(pin);
			}
			saveUser.setUser(userModel);
			if (!children.isEmpty()){
				saveUser.setChildren(children);
				saveUser.setBooks(Cirkulacija.getApp().getRecordsManager().getList());
			}
			if (!removedchildren.isEmpty()){
				saveUser.setRemovedChildren(removedchildren);
			}
			saveUser = (SaveUserCommand)service.executeCommand(saveUser);
			if (saveUser == null)
				return "Gre\u0161ka u konekciji s bazom podataka!";
			String message = "";
			if (saveUser.isSaved()){
				GetUserCommand getUser = new GetUserCommand(userModel.getUserId());
				userModel = ((GetUserCommand)service.executeCommand(getUser)).getUser();
				loadUser(user, userModel);
				//userModel = saveUser.getUser();
				//refreshInfo(user, userModel);
				message = "ok";
			} else {
				if (saveUser.getStaleID() != null){
					GetObjectCommand getObject = new GetObjectCommand(saveUser.getStaleID(), saveUser.getStaleName());
					Object book = ((GetObjectCommand)service.executeCommand(getObject)).getObject();
					if (book instanceof Primerci){
				        message = "Status primerka " + ((Primerci)book).getInvBroj() + ": " 
				          + ((Primerci)book).getStatusPrimerka().getStatusOpis();
				      } else {
				        message = "Status primerka " + ((Sveske)book).getInvBr() + ": " 
				          + ((Sveske)book).getStatusPrimerka().getStatusOpis();
				      }
				} else {
					message = saveUser.getMessage();
				}
				GetUserCommand getUser = new GetUserCommand(userModel.getUserId());
				userModel = ((GetUserCommand)service.executeCommand(getUser)).getUser();
				loadUser(user, userModel);
			}
			Cirkulacija.getApp().getRecordsManager().getList().clear();
			children.clear();
			removedchildren.clear();
			return message;
		} else {
			return "ok";
		}*/return null;
	}
	
	public void releaseUser(){
		userModel = null;
		chargeBook = "";
		Cirkulacija.getApp().getMainFrame().setRequestedPanel(0);
	}
  
  public boolean gotUser(){
    return userModel != null;
  }
  
  public String saveGroup(Group group){
    /*if (groupModel == null){
      groupModel = new Groups();  
    }
    groupModel = toObjectModel(group, groupModel);
    SaveObjectCommand saveGroup = new SaveObjectCommand(groupModel);
    saveGroup = (SaveObjectCommand)service.executeCommand(saveGroup);
    if (saveGroup == null)
		return "Gre\u0161ka u konekciji s bazom podataka!";
    
    if (saveGroup.isSaved()){
    	return "ok";
    } else {
    	return saveGroup.getMessage();
    }*/
    return null;
  }
  
  public void releaseGroup(){
    groupModel = null;
  }
  
  public void initialiseUser(User user){
    userModel = new Member();
    user.getMmbrship().setTableModel(new HashSet(userModel.getSignings()));
    user.getLending().setTableModel(new HashSet(userModel.getLendings()));
    user.getUserData().setDupTableModel(new HashSet(userModel.getDuplicates()));
  }
  
  public void showUser(Member user, String userID){
//  	StopWatch clock = new StopWatch();
//    clock.start();
	/*GetUserCommand getUser = new GetUserCommand(userID);
	getUser = (GetUserCommand)service.executeCommand(getUser);
	if (getUser != null){
		userModel = getUser.getUser();
	    if (userModel != null){
	    	children.clear();
	    	removedchildren.clear();
	    	Cirkulacija.getApp().getRecordsManager().getList().clear();
	    	loadUser(user, userModel);
	    }
	}*/
//    clock.stop();
//    System.out.println("clock:"+clock.getTime());
  }
  
  public int showChargedUser(User user){
    return /*getUser(user, null, chargedUser);*/ -1;
  }
  
  public void chargeUser(String ctlgno){
    if (userModel == null){
      chargeBook = ctlgno;
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(3);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    } else {
      //Cirkulacija.getApp().getMainFrame().getUserPanel().getLending().lendBook(ctlgno);
      Cirkulacija.getApp().getMainFrame().previousTwoPanels();
      //Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
    }
  }
  
  public String archiveUser(Member user){
	  /*GetAllUserDataCommand getUserData = new GetAllUserDataCommand(user.getMmbrship().getUserID());
	  getUserData = (GetAllUserDataCommand)service.executeCommand(getUserData);
		if (getUserData != null){
			Users userdata = getUserData.getUser();
			ArchiveUserCommand archiveUser = new ArchiveUserCommand();
			archiveUser.setUserID(user.getMmbrship().getUserID());
			archiveUser.setUser(userdata);
			List<Object> list = new ArrayList<Object>();
			list.addAll(userdata.getSignings());
			list.addAll(userdata.getAlllendings());
			list.addAll(userdata.getDuplicates());
			list.addAll(userdata.getPicturebooks());
			for (Object lending: userdata.getAlllendings()){
				list.addAll(((Lending)lending).getWarningses());
			}
			archiveUser.setChildren(list);
			archiveUser = (ArchiveUserCommand)serviceArchive.executeCommand(archiveUser);
			if (archiveUser == null){
				return "Gre\u0161ka u konekciji s bazom podataka!";
			}
			if (archiveUser.isSaved()){
				DeleteObjectCommand deleteUser = new DeleteObjectCommand(userModel);
				deleteUser = (DeleteObjectCommand)service.executeCommand(deleteUser);
				if (deleteUser.isSaved()){
					return "ok";
				}else{
					return deleteUser.getMessage();
				}
				
			} else {
				return archiveUser.getMessage();
			}
		} else {
			return "Gre\u0161ka u konekciji s bazom podataka!";
		}*/ return null;
  }
	
  public int getUser(User user, Group group, String userID){
    int found = 0;
      Member getUser = null;
      List<Lending> lendings = new ArrayList<>();
      try {
          getUser = BisisApp.bisisService.getMemberById(userID).execute().body();
          lendings = BisisApp.bisisService.getLendingsByUserId(userID).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
      getUser.setLendings(lendings);
    if (getUser != null){
		userModel = getUser;
		if (userModel != null){
			children.clear();
			removedchildren.clear();
			//Cirkulacija.getApp().getRecordsManager().getList().clear();
			loadUser(user, userModel);
			found = 1;
			return found;
		}
    }

    /*
    getGroup = (GetGroupCommand)service.executeCommand(getGroup);
    if (getGroup != null){
    	groupModel = getGroup.getUser();
    	if (groupModel != null){
    		group.loadGroup(groupModel.getUserId(), groupModel.getInstName(), groupModel.getSignDate(), groupModel.getAddress(), groupModel.getCity(), Utils.getString(groupModel.getZip()), 
    		groupModel.getPhone(), groupModel.getEmail(), groupModel.getFax(), groupModel.getSecAddress(), Utils.getString(groupModel.getSecZip()), groupModel.getSecCity(),
            groupModel.getSecPhone(), groupModel.getContFname(), groupModel.getContLname(), groupModel.getContEmail());
    		found = 2;
    		return found;
    	}
    }*/
    return found;
  }
  
  private void loadUser(User user, Member userModel){
    boolean blocked = false;
    String blockedInfo = "";
    if (userModel.getBlockReason()!=null && !"".equals(userModel.getBlockReason())){
      blockedInfo = "Blokirano: "+userModel.getBlockReason();
      blocked = true;  
    }
    
    Iterator it = userModel.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    user.getUserData().loadUser(userModel.getFirstName(), userModel.getLastName(), userModel.getParentName(),
        userModel.getAddress(), Utils.getString(userModel.getZip()), userModel.getCity(), userModel.getPhone(),
        userModel.getEmail(), userModel.getGender(), userModel.getAge(), userModel.getSecAddress(),
        userModel.getSecCity(), Utils.getString(userModel.getSecZip()), userModel.getSecPhone(), userModel.getJmbg(),
        userModel.getDocId(), userModel.getDocNo(), userModel.getDocCity(), userModel.getCountry(),
        userModel.getTitle(), userModel.getOccupation(), userModel.getIndexNo(), Utils.getString(userModel.getClassNo()),
        userModel.getOrganization(), userModel.getEducationLevel(), userModel.getLanguage(), userModel.getNote(),
        userModel.getInterests(), userModel.getWarningInd(), blocked, userModel.getBlockReason(), new HashSet(userModel.getDuplicates()), userModel.getPass());

    user.getMmbrship().loadUser(userModel.getUserId(), userModel.getMembershipType(), userModel.getUserCategory(), userModel.getGroups().toString(), new HashSet(userModel.getSignings()));

    Date maxDate = null;
    Date date = null;
    it = userModel.getSignings().iterator();
    while (it.hasNext()){
      date = ((Signing)it.next()).getUntilDate();
      if (date != null && (maxDate == null || maxDate.before(date))){
        maxDate = date;
      }
    }

    if (maxDate != null && maxDate.before(new Date())){
      if (!blockedInfo.equals(""))
        blockedInfo = blockedInfo + ", ";
      blockedInfo = blockedInfo + "Istekla \u010dlanarina";
    }
    
    warnings = new ArrayList<Warning>();
    Set<Lending> lendings = new HashSet<>(userModel.getLendings());
    for (Lending lend : lendings){
      Set<Warning> warns = new HashSet<Warning>(lend.getWarnings());
      for (Warning warn : warns){
        warnings.add(warn);
      }
    }
    
    user.getLending().loadUser(userModel.getUserId(), userModel.getFirstName(), userModel.getLastName(), maxDate, userModel.getNote(), dupno, blockedInfo, new HashSet(userModel.getLendings()), !warnings.isEmpty());
    
    user.setDirty(false);
    
    if (!chargeBook.equals("")){
      user.getLending().lendBook(chargeBook);
      chargeBook = "";
      user.setDirty(true);
    }

  }
  
  public void refreshInfo(User user, Member userModel){
  	boolean blocked = false;
    String blockedInfo = "";
    if (userModel.getBlockReason()!=null && !"".equals(userModel.getBlockReason())){
      blockedInfo = "Blokirano: "+userModel.getBlockReason();
      blocked = true;  
    }
    
    Iterator it = userModel.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    Date maxDate = null;
    Date date = null;
    it = userModel.getSignings().iterator();
    while (it.hasNext()){
      date = ((Signing)it.next()).getUntilDate();
      if (date != null && (maxDate == null || maxDate.before(date))){
        maxDate = date;
      }
    }
    
    if (maxDate != null && maxDate.before(new Date())){
      if (!blockedInfo.equals(""))
        blockedInfo = blockedInfo + ", ";
      blockedInfo = blockedInfo + "Istekla \u010dlanarina";
    }
    
    user.getLending().refreshInfo(userModel.getUserId(), userModel.getFirstName(), userModel.getLastName(), maxDate, userModel.getNote(), dupno, blockedInfo);
    
  }
	
	
  public void loadCombos(User user) throws Exception {


	if (BisisApp.appConfig.getCodersHelper() == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getUserData().loadEduLvl(BisisApp.appConfig.getCodersHelper()
                                .getEducationLevels().values().stream().collect(Collectors.toList()));

	user.getUserData().loadLanguage(BisisApp.appConfig.getCodersHelper()
            .getLanguages().values().stream().collect(Collectors.toList()));

	user.getUserData().loadOrganization(BisisApp.appConfig.getCodersHelper()
            .getOrganizations().values().stream().collect(Collectors.toList()));

	user.getMmbrship().loadGroups(BisisApp.appConfig.getCodersHelper()
            .getMembershipTypes().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));

	/*user.getMmbrship().loadLocation(BisisApp.appConfig.getCodersHelper()
            .getLocations().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));

	user.getMmbrship().loadBranchID(BisisApp.appConfig.getCodersHelper()
            .getLocations().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));

	user.getMmbrship().loadMmbrType(BisisApp.appConfig.getCodersHelper()
            .getMembershipTypes().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));

	user.getMmbrship().loadUserCateg(BisisApp.appConfig.getCodersHelper()
            .getUserCategories().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));

	user.getLending().loadLocation(BisisApp.appConfig.getCodersHelper()
            .getLocations().values().stream()
            .map(i -> i.getDescription()).collect(Collectors.toList()));*/
  }
  
  public void loadCombos(Group group) throws Exception {
  	/*GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    group.loadBranchID(getAll.getList());*/
  }
  
  public void loadCombos(SearchUsers searchusers) throws Exception {
  	/*GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    searchusers.loadCmbLoc1(getAll.getList());
    searchusers.loadCmbLoc2(getAll.getList());*/
  }
  
  public void loadCombos(SearchBooks searchusers) throws Exception {
  	/*GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    searchusers.loadCmbLocL(getAll.getList());
    searchusers.loadCmbLocR(getAll.getList());*/
  }
  
  public void loadCombos(/*Report report*/) throws Exception {
  	/*GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    report.loadCmbLocation(getAll.getList());*/
    
  }
	
	private Member toObjectModel(User user, Member userModel){
		UserData data = user.getUserData();
		userModel.setAddress(data.getAddress().trim());
		userModel.setAge(data.getAge());
		userModel.setCity(data.getCity().trim());
		userModel.setClassNo(Utils.getInteger(data.getClassNo()));
		userModel.setCountry(data.getCountry().trim());
		userModel.setDocCity(data.getDocCity().trim());
		userModel.setDocId(Integer.valueOf(data.getDocId()));
		userModel.setDocNo(data.getDocNo().trim());
		userModel.setEducationLevel(data.getEduLvl().getDescription());// TODO- usaglasiti sifarnike i model kornisnika cirukalcije???
		userModel.setEmail(data.getEmail().trim());
		userModel.setFirstName(data.getFirstName().trim());
		userModel.setGender(data.getGender());
		userModel.setIndexNo(data.getIndexNo().trim());
		userModel.setInterests(data.getInterests().trim());
		userModel.setJmbg(data.getJmbg().trim());
		userModel.setLanguage(data.getLanguages().getDescription());
		userModel.setLastName(data.getLastName().trim());
		userModel.setNote(data.getNote().trim());
		userModel.setOccupation(data.getOccupation().trim());
		userModel.setOrganization(data.getOrganization());
		userModel.setParentName(data.getParentName().trim());
		userModel.setPhone(data.getPhone().trim());
		userModel.setSecAddress(data.getTmpAddress().trim());
		userModel.setSecCity(data.getTmpCity().trim());
		userModel.setSecPhone(data.getTmpPhone().trim());
		userModel.setSecZip(Utils.getInteger(data.getTmpZip()));
		userModel.setTitle(data.getTitle().trim());
		userModel.setWarningInd(Integer.valueOf(data.getWarning()));
		userModel.setZip(Utils.getInteger(data.getZip()));
    if (data.getBlocked()){
      userModel.setBlockReason(data.getBlockedReason().trim());
    } else {
      userModel.setBlockReason("");
    }
		
		com.ftninformatika.bisis.circ.view.Membership mmbrship = user.getMmbrship();
		//userModel.setGroups(mmbrship.getGroup());
		userModel.setUserId(mmbrship.getUserID());
		userModel.setMembershipType(mmbrship.getMmbrType());
		userModel.setUserCategory(mmbrship.getUserCateg());
		
		return userModel;
	}
  
  /*private Groups toObjectModel(Group group, Groups groupModel){
    groupModel.setAddress(group.getAddress().trim());
    groupModel.setCity(group.getCity().trim());
    groupModel.setContEmail(group.getContactEmail().trim());
    groupModel.setContFname(group.getContactFirstName().trim());
    groupModel.setContLname(group.getContactLastName().trim());
    groupModel.setEmail(group.getEmail().trim());
    groupModel.setFax(group.getFax().trim());
    groupModel.setInstName(group.getOrganization().trim());
    groupModel.setPhone(group.getPhone().trim());
    groupModel.setSecAddress(group.getTmpAddress().trim());
    groupModel.setSecCity(group.getTmpCity().trim());
    groupModel.setSecPhone(group.getTmpPhone().trim());
    groupModel.setSecZip(Utils.getInteger(group.getTmpZip()));
    groupModel.setSignDate(group.getSignDate());
    groupModel.setUserId(group.getUserID());
    groupModel.setZip(Utils.getInteger(group.getZip()));
    return groupModel;
  }*/
  
  public void addSigning(Signing sig){
    userModel.getSignings().add(sig);
    if (!children.contains(sig)){
    	children.add(sig);
    }
  }
  
  public void removeSigning(Signing sig){
    userModel.getSignings().remove(sig);
    if (children.contains(sig)){
    	children.remove(sig);
    }
    removedchildren.add(sig);
  }
  
  public void updateSigning(Signing sig){
  	if (!children.contains(sig)){
    	children.add(sig);
    }
  }
  
  public void addLending(Lending lend){
    userModel.getLendings().add(lend);
    if (!children.contains(lend)){
    	 children.add(lend);
    }
   
  }
  
  public void updateLending(Lending lend){
  	if (!children.contains(lend)){
   	  children.add(lend);
  	}
  }
  
  public void addDuplicate(Duplicate dup){
    userModel.getDuplicates().add(dup);
    if (!children.contains(dup)){
    	children.add(dup);
    }
  }
  
  public void removeDuplicate(Duplicate dup){
    userModel.getDuplicates().remove(dup);
    if (children.contains(dup)){
    	children.remove(dup);
    }
    removedchildren.add(dup);
  }
  
  public void updateDuplicate(Duplicate dup){
  	if (!children.contains(dup)){
    	 children.add(dup);
    }
  }
  
  public void addPicturebooks(PictureBook pic){
    userModel.getPicturebooks().add(pic);
    if (!children.contains(pic)){
    	children.add(pic);
   }
  }
  
  public void updateWarning(Warning warn){
  	if (!children.contains(warn)){
    	children.add(warn);
    }
  }
	
  public Double getMembership(MembershipType mt, UserCategory uc){

  	for(Membership m: BisisApp.appConfig.getCodersHelper().getMemberships().values()) {
		if (m.getMemberType().equals(mt.getDescription()) && m.getUserCateg().equals(uc.getDescription()))
			return m.getCost(); //TODO-????
  	}

  	return null;
  }
  
  public String getUserId(String location){
   /* String loc = location;
    if (loc.equals(""))
      loc = "0";
      
    GetLastUserIDCommand getLastID = new GetLastUserIDCommand(Integer.valueOf(loc));
    getLastID = (GetLastUserIDCommand)service.executeCommand(getLastID);
    if (getLastID == null)
    	return null;
    int last = getLastID.getLastID();
    String userId = Utils.makeUserId(loc, Integer.toString(last));
    while (existsUser(userId)){
    	getLastID = (GetLastUserIDCommand)service.executeCommand(getLastID);
    	if (getLastID == null)
        	return null;
    	last = getLastID.getLastID();
    	userId = Utils.makeUserId(loc, Integer.toString(last));
    }
    return Integer.toString(last);*/
   return null;

  }
  
  public boolean existsUser(String userID){
  	boolean retVal = false;
      try {
          retVal = BisisApp.bisisService.memberExist(userID).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return retVal;
  }
  
  public String getChargedUser(String ctlgno){
  	/*GetChargedUserCommand getUser = new GetChargedUserCommand(ctlgno);
  	getUser = (GetChargedUserCommand)service.executeCommand(getUser);
  	if (getUser == null)
  		return "";
    Object[] user = getUser.getUser();
    String result = "";
    if (user != null){
      chargedUser = (String)user[0];
      result = (user[0] != null ? (String)user[0]+", ": "")+(user[1] != null ? (String)user[1]+" ": "")+(user[2] != null ? (String)user[2]: "") ;
    }
    return result;*/
  	return null;
  }
  
  public boolean dischargeUser(String ctlgno){
  	/*GetLendingCommand getLending = new GetLendingCommand(ctlgno);
  	getLending = (GetLendingCommand)service.executeCommand(getLending);
  	if (getLending == null)
  		return false;
    Lending lending = getLending.getLending();
    if (lending != null){
	    lending.setReturnDate(new Date());
	    lending.setLibrarianReturn(Cirkulacija.getApp().getLibrarian().getUsername());
    }
    Object primerak = Cirkulacija.getApp().getRecordsManager().changeStanje(ctlgno);
    DischargeBookCommand discharge = new DischargeBookCommand(lending, primerak);
    discharge = (DischargeBookCommand)service.executeCommand(discharge);
    return discharge.isSaved();
  }
  
  public List getCtlgNos(Date startDateL, Date endDateL, Location locationL, 
                                  Date startDateR, Date endDateR, Location locationR){
    Date startL = null;
    Date endL = null;
    if (startDateL != null){
      startL = Utils.setMinDate(startDateL);
      if (endDateL != null){
        endL = Utils.setMaxDate(endDateL);
      }else{
        endL = Utils.setMaxDate(startDateL);
      }
    }
    
    Location location;
    if (locationL != null){
      location = locationL;
    }else{
      location = locationR;
    }
    Date startR = null;
    Date endR = null;
    if (startDateR != null){
      startR = Utils.setMinDate(startDateR);
      if (endDateR != null){
        endR = Utils.setMaxDate(endDateR);
      }else{
        endR = Utils.setMaxDate(startDateR);
      }
    }
    
    GetCtlgNosCommand getCtlgNos = new GetCtlgNosCommand(startL, endL, location, startR, endR);
    getCtlgNos = (GetCtlgNosCommand)service.executeCommand(getCtlgNos);
    if (getCtlgNos == null)
    	return null;
    return getCtlgNos.getList();*/
  	return false;
  }
  
  public List getWarnings(){
    return warnings;
  }
  
  public Set getPicturebooks(){
  	if (userModel != null){
  		return (Set) userModel.getPicturebooks();
  	} else {
  		return null;
  	}
  }
  
  public String getEnvFile(){
      if (env == null){
          try{
              env = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("circ-options.xml").toURI())));
          }catch (Exception e) {
              e.printStackTrace();
          }
      }
      return env;
  }



  public String getValidatorFile(){
  	if (validator == null) {
        try {
            validator = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("options-validator.xml").toURI())));
        }
  		catch (Exception e) {
            e.printStackTrace();
  		}
  	}
  	return validator;
  }

}
