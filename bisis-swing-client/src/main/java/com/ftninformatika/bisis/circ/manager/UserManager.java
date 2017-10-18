package com.ftninformatika.bisis.circ.manager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.view.*;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.circ.Lending;
import com.ftninformatika.bisis.models.circ.Membership;
import com.ftninformatika.bisis.models.circ.pojo.Duplicate;
import com.ftninformatika.bisis.models.circ.pojo.Signing;
import com.ftninformatika.bisis.models.circ.pojo.Warning;
import com.ftninformatika.bisis.models.circ.wrappers.MemberData;

public class UserManager {
	
  private Member member;
  private CorporateMember corporateMember;
  private String chargedUser;
  private String chargeBook = "";
  private List warnings = null;
  private String env = null;
  private String validator = null;
  private List lendings = null;
	
	public UserManager(){		
		lendings = new ArrayList();
	}
	
	public String saveUser(User user){
		if (user.getDirty() || !lendings.isEmpty()){
            String memberExists;
			if (user.getDirty()){

                try {
                    memberExists = BisisApp.bisisService.memberExist(user.getMmbrship().getUserID()).execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Gre\u0161ka u konekciji s bazom podataka!";
                }

				if (member == null){
					if (memberExists != null)
						return "Broj korisnika vec postoji!";
					member = new Member();
				} else {
				  if (memberExists != null && memberExists.equals(member.get_id()))
					  return "Broj korisnika vec postoji!";
				}
				member = toObjectModel(user, member);
			}

			if (BisisApp.appConfig.getClientConfig().getPincodeEnabled().equals("true") && (member.getPin() == null || member.getPin().equals(""))){
				String pin = Utils.generatePin();
				member.setPin(pin);
				user.getUserData().setPinCode(pin);
			}

            MemberData memberData = new MemberData();
			memberData.setMember(member);

			if (!lendings.isEmpty()){
                memberData.setLendings(lendings);
                memberData.setBooks(Cirkulacija.getApp().getRecordsManager().getList());
			}
            boolean saved = false;
            try {
                saved = BisisApp.bisisService.addUpdateMember(memberData).execute().body();
            } catch (Exception e) {
                e.printStackTrace();
            }

			if (saved){
                try {
                    memberData =BisisApp.bisisService.getMemberById(member.getUserId()).execute().body();
                    member = memberData.getMember();
                    lendings = memberData.getLendings();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (member != null){
                    Cirkulacija.getApp().getRecordsManager().getList().clear();
                    loadUser(user, member, lendings);
                }
				return "ok";
			} else {
                return "Gre\u0161ka u konekciji s bazom podataka!";
			}
		} else {
			return "ok";
		}
	}
	
	public void releaseUser(){
        try {
            Boolean released = BisisApp.bisisService.releaseMemberById(member.getUserId()).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
		member = null;
		lendings = null;
		chargeBook = "";
		Cirkulacija.getApp().getMainFrame().setRequestedPanel(0);

	}
  
  public boolean gotUser(){
    return member != null;
  }
  
  public String saveGroup(Group group){
    if (corporateMember == null){
      corporateMember = new CorporateMember();
    }
    corporateMember = toObjectModel(group, corporateMember);
    Boolean saved = false;
      try {
          saved = BisisApp.bisisService.saveCorporateMember(corporateMember).execute().body();
      } catch (Exception e) {
          e.printStackTrace();
      }

    if (saved){
    	return "ok";
    } else {
        return "Gre\u0161ka u konekciji s bazom podataka!";
    }
  }
  
  public void releaseGroup(){
    corporateMember = null;
  }
  
  public void initialiseUser(User user){
    member = new Member();
    lendings = new ArrayList<Lending>();
    user.getMmbrship().setTableModel(new HashSet(member.getSignings()));
    user.getLending().setTableModel(new HashSet(lendings));
    user.getUserData().setDupTableModel(new HashSet(member.getDuplicates()));
  }
  
  public int showUser(User user, String userID){
//      try {
//          member = BisisApp.bisisService.getMemberById(userID).execute().body();
//          lendings = BisisApp.bisisService.getLendingsByUserId(userID).execute().body();
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//
//      if (member != null){
//          Cirkulacija.getApp().getRecordsManager().getList().clear();
//          loadUser(user, member, lendings);
//      }
      return getUser(user, null, chargedUser);
  }
  
  public int showChargedUser(User user){
    return getUser(user, null, chargedUser);
  }
  
  public void chargeUser(String ctlgno){
    if (member == null){
      chargeBook = ctlgno;
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(3);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    } else {
      Cirkulacija.getApp().getMainFrame().getUserPanel().getLending().lendBook(ctlgno);
      Cirkulacija.getApp().getMainFrame().previousTwoPanels();
      Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
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
				DeleteObjectCommand deleteUser = new DeleteObjectCommand(member);
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
      try {
          MemberData memberData = BisisApp.bisisService.getMemberById(userID).execute().body();
          member = memberData.getMember();
          lendings = memberData.getLendings();
      } catch (Exception e) {
          e.printStackTrace();
      }

    if (member != null){
          if (member.getInUseBy() == null) {
              Cirkulacija.getApp().getRecordsManager().getList().clear();
              loadUser(user, member, lendings);
              found = 1;
              return found;
          } else {
              found = 3;
              return found;
          }
    }

      try {
          corporateMember = BisisApp.bisisService.getCorporateMemberById(userID).execute().body();
      } catch (Exception e) {
          e.printStackTrace();
      }
    if (corporateMember != null){
        group.loadGroup(corporateMember.getUserId(), corporateMember.getInstName(), corporateMember.getSignDate(), corporateMember.getAddress(), corporateMember.getCity(), Utils.getString(corporateMember.getZip()),
        corporateMember.getPhone(), corporateMember.getEmail(), corporateMember.getFax(), corporateMember.getSecAddress(), Utils.getString(corporateMember.getSecZip()), corporateMember.getSecCity(),
        corporateMember.getSecPhone(), corporateMember.getContFirstName(), corporateMember.getContLastName(), corporateMember.getContEmail());
        found = 2;
        return found;
    }
    return found;
  }
  
  private void loadUser(User user, Member member, List<Lending> lendings){
    boolean blocked = false;
    String blockedInfo = "";
    if (member.getBlockReason()!=null && !"".equals(member.getBlockReason())){
      blockedInfo = "Blokirano: "+member.getBlockReason();
      blocked = true;  
    }
    
    Iterator it = member.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    user.getUserData().loadUser(member.getFirstName(), member.getLastName(), member.getParentName(),
        member.getAddress(), Utils.getString(member.getZip()), member.getCity(), member.getPhone(),
        member.getEmail(), member.getGender(), member.getAge(), member.getSecAddress(),
        member.getSecCity(), Utils.getString(member.getSecZip()), member.getSecPhone(), member.getJmbg(),
        member.getDocId(), member.getDocNo(), member.getDocCity(), member.getCountry(),
        member.getTitle(), member.getOccupation(), member.getIndexNo(), Utils.getString(member.getClassNo()),
        member.getOrganization(), member.getEducationLevel(), member.getLanguage(), member.getNote(),
        member.getInterests(), member.getWarningInd(), blocked, member.getBlockReason(), new HashSet(member.getDuplicates()), member.getPin());

    user.getMmbrship().loadUser(member.getUserId(), member.getMembershipType(), member.getUserCategory(), member.getCorporateMember(), new HashSet(member.getSignings()));

    Date maxDate = null;
    Date date = null;
    it = member.getSignings().iterator();
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
    for (Lending lend : lendings){
      Set<Warning> warns = new HashSet<Warning>(lend.getWarnings());
      for (Warning warn : warns){
        warnings.add(warn);
      }
    }
    
    user.getLending().loadUser(member.getUserId(), member.getFirstName(), member.getLastName(), maxDate, member.getNote(), dupno, blockedInfo, new HashSet(lendings), !warnings.isEmpty());
    
    user.setDirty(false);
    
    if (!chargeBook.equals("")){
      user.getLending().lendBook(chargeBook);
      chargeBook = "";
      user.setDirty(true);
    }

  }
  
  public void refreshInfo(User user, Member member){
  	boolean blocked = false;
    String blockedInfo = "";
    if (member.getBlockReason()!=null && !"".equals(member.getBlockReason())){
      blockedInfo = "Blokirano: "+member.getBlockReason();
      blocked = true;  
    }
    
    Iterator it = member.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    Date maxDate = null;
    Date date = null;
    it = member.getSignings().iterator();
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
    
    user.getLending().refreshInfo(member.getUserId(), member.getFirstName(), member.getLastName(), maxDate, member.getNote(), dupno, blockedInfo);
    
  }
	
	
  public void loadCombos(User user) throws Exception {

	if (BisisApp.appConfig.getCodersHelper() == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");

	user.getUserData().loadEduLvl(BisisApp.appConfig.getCodersHelper()
            .getEducationLevels().values().stream()
            .map(i -> i.getDescription())
            .collect(Collectors.toList()));

	user.getUserData().loadLanguage(BisisApp.appConfig.getCodersHelper()
            .getLanguages().values().stream()
            .map(i -> i.getDescription())
            .collect(Collectors.toList()));

	user.getUserData().loadOrganization(BisisApp.appConfig.getCodersHelper()
            .getOrganizations().values().stream()
            .map( i -> {
                com.ftninformatika.bisis.models.circ.pojo.Organization o = new com.ftninformatika.bisis.models.circ.pojo.Organization();
                o.setAddress(i.getAddress());
                o.setCity(i.getCity());
                o.setId(i.get_id());
                o.setName(i.getName());
                o.setZip(i.getZip());
                return o;
            })
            .collect(Collectors.toList()));

	user.getMmbrship().loadGroups(BisisApp.appConfig.getCodersHelper()
            .getCorporateMembers().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.CorporateMember c = new com.ftninformatika.bisis.models.circ.pojo.CorporateMember();
                c.setAddress(i.getAddress());
                c.setCity(i.getCity());
                c.setContEmail(i.getContEmail());
                c.setContFirstName(i.getContFirstName());
                c.setContLastName(i.getContLastName());
                c.setEmail(i.getEmail());
                c.setFax(i.getFax());
                c.setInstName(i.getInstName());
                c.setUserId(i.getUserId());
                c.setPhone(i.getPhone());
                c.setSecAddress(i.getSecAddress());
                c.setSecCity(i.getSecCity());
                c.setSecPhone(i.getSecPhone());
                c.setSecZip(i.getSecZip());
                c.setSignDate(i.getSignDate());
                c.setZip(i.getZip());
                return c;
            })
            .collect(Collectors.toList()));

	user.getMmbrship().loadLocation(BisisApp.appConfig.getCodersHelper()
            .getCircLocations().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.CircLocation l = new com.ftninformatika.bisis.models.circ.pojo.CircLocation();
                l.setDescription(i.getDescription());
                l.setLocation_id(i.getLocation_id());
                return l;

            })
            .collect(Collectors.toList()));

	user.getMmbrship().loadBranchID(BisisApp.appConfig.getCodersHelper()
            .getCircLocations().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.CircLocation l = new com.ftninformatika.bisis.models.circ.pojo.CircLocation();
                l.setDescription(i.getDescription());
                l.setLocation_id(i.getLocation_id());
                return l;

            })
            .collect(Collectors.toList()));

	user.getMmbrship().loadMmbrType(BisisApp.appConfig.getCodersHelper()
            .getMembershipTypes().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.MembershipType m = new com.ftninformatika.bisis.models.circ.pojo.MembershipType();
                m.setDescription(i.getDescription());
                m.setPeriod(i.getPeriod());
                return m;
            })
            .collect(Collectors.toList()));

	user.getMmbrship().loadUserCateg(BisisApp.appConfig.getCodersHelper()
            .getUserCategories().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.UserCategory u = new com.ftninformatika.bisis.models.circ.pojo.UserCategory();
                u.setDescription(i.getDescription());
                u.setMaxPeriod(i.getMaxPeriod());
                u.setPeriod(i.getPeriod());
                u.setTitlesNo(i.getTitlesNo());
                return u;
            })
            .collect(Collectors.toList()));

	user.getLending().loadLocation(BisisApp.appConfig.getCodersHelper()
            .getCircLocations().values().stream()
            .map(i -> {
                com.ftninformatika.bisis.models.circ.pojo.CircLocation l = new com.ftninformatika.bisis.models.circ.pojo.CircLocation();
                l.setDescription(i.getDescription());
                l.setLocation_id(i.getLocation_id());
                return l;

            })
            .collect(Collectors.toList()));


  }
  
  public void loadCombos(Group group) throws Exception {
      group.loadBranchID((BisisApp.appConfig.getCodersHelper()
              .getCircLocations().values().stream()
              .map(i -> {
                  com.ftninformatika.bisis.models.circ.pojo.CircLocation l = new com.ftninformatika.bisis.models.circ.pojo.CircLocation();
                  l.setDescription(i.getDescription());
                  l.setLocation_id(i.getLocation_id());
                  return l;

              })
              .collect(Collectors.toList())));
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
	
	private Member toObjectModel(User user, Member member){
		UserData data = user.getUserData();
		member.setAddress(data.getAddress().trim());
		member.setAge(data.getAge());
		member.setCity(data.getCity().trim());
		member.setClassNo(Utils.getInteger(data.getClassNo()));
		member.setCountry(data.getCountry().trim());
		member.setDocCity(data.getDocCity().trim());
		member.setDocId(Integer.valueOf(data.getDocId()));
		member.setDocNo(data.getDocNo().trim());
		member.setEducationLevel(data.getEduLvl());
		member.setEmail(data.getEmail().trim());
		member.setFirstName(data.getFirstName().trim());
		member.setGender(data.getGender());
		member.setIndexNo(data.getIndexNo().trim());
		member.setInterests(data.getInterests().trim());
		member.setJmbg(data.getJmbg().trim());
		member.setLanguage(data.getLanguages());
		member.setLastName(data.getLastName().trim());
		member.setNote(data.getNote().trim());
		member.setOccupation(data.getOccupation().trim());
		member.setOrganization(data.getOrganization());
		member.setParentName(data.getParentName().trim());
		member.setPhone(data.getPhone().trim());
		member.setSecAddress(data.getTmpAddress().trim());
		member.setSecCity(data.getTmpCity().trim());
		member.setSecPhone(data.getTmpPhone().trim());
		member.setSecZip(Utils.getInteger(data.getTmpZip()));
		member.setTitle(data.getTitle().trim());
		member.setWarningInd(Integer.valueOf(data.getWarning()));
		member.setZip(Utils.getInteger(data.getZip()));
    if (data.getBlocked()){
      member.setBlockReason(data.getBlockedReason().trim());
    } else {
      member.setBlockReason("");
    }
		
		com.ftninformatika.bisis.circ.view.Membership mmbrship = user.getMmbrship();
		member.setCorporateMember(mmbrship.getGroup());
		member.setUserId(mmbrship.getUserID());
		member.setMembershipType(mmbrship.getMmbrType());
		member.setUserCategory(mmbrship.getUserCateg());
		
		return member;
	}
  
  private CorporateMember toObjectModel(Group group, CorporateMember corporateMember){
    corporateMember.setAddress(group.getAddress().trim());
    corporateMember.setCity(group.getCity().trim());
    corporateMember.setContEmail(group.getContactEmail().trim());
    corporateMember.setContFirstName(group.getContactFirstName().trim());
    corporateMember.setContLastName(group.getContactLastName().trim());
    corporateMember.setEmail(group.getEmail().trim());
    corporateMember.setFax(group.getFax().trim());
    corporateMember.setInstName(group.getOrganization().trim());
    corporateMember.setPhone(group.getPhone().trim());
    corporateMember.setSecAddress(group.getTmpAddress().trim());
    corporateMember.setSecCity(group.getTmpCity().trim());
    corporateMember.setSecPhone(group.getTmpPhone().trim());
    corporateMember.setSecZip(Utils.getInteger(group.getTmpZip()));
    corporateMember.setSignDate(group.getSignDate());
    corporateMember.setUserId(group.getUserID());
    corporateMember.setZip(Utils.getInteger(group.getZip()));
    return corporateMember;
  }
  
  public void addLending(Lending lend){
    if (!lendings.contains(lend)){
    	 lendings.add(lend);
    }
   
  }
  
  public void updateLending(Lending lend){
  	if (!lendings.contains(lend)){
   	  lendings.add(lend);
  	}
  }
	
  public Double getMembership(String membershipType, String userCategory){

  	for(Membership m: BisisApp.appConfig.getCodersHelper().getMemberships().values()) {
		if (m.getMemberType().equals(membershipType) && m.getUserCateg().equals(userCategory))
			return m.getCost();
  	}
  	return null;
  }
  
  public String getUserId(String location){
    String loc = location;
    if (loc.equals(""))
      loc = "0";

      Integer last = null;
      try {
          last = BisisApp.bisisService.getLastUserId(Integer.valueOf(loc)).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }

    if (last == null)
    	return null;
    String userId = Utils.makeUserId(loc, Integer.toString(last));
    while (existsUser(userId)){
        try {
            last = BisisApp.bisisService.getLastUserId(Integer.valueOf(loc)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (last == null)
            return null;
    	userId = Utils.makeUserId(loc, Integer.toString(last));
    }
    return Integer.toString(last);

  }
  
  public boolean existsUser(String userID){
  	String retVal = null;
      try {
          retVal = BisisApp.bisisService.memberExist(userID).execute().body();
      } catch (IOException e) {
          e.printStackTrace();
      }
      if (retVal == null){
          return false;
      } else {
          return true;
      }
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
  	if (member != null){
  		return (Set) member.getPicturebooks();
  	} else {
  		return null;
  	}
  }
  
  public String getEnvFile(){
      if (env == null){
          try{
              CircConfig config = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
              env = config.getCircOptionsXML();
              validator = config.getValidatorOptionsXML();
              //env = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("circ-options.xml").toURI())));
          }catch (Exception e) {
              e.printStackTrace();
          }
      }
      return env;
  }



  public String getValidatorFile(){
  	if (validator == null) {
        try {
            CircConfig config = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
            env = config.getCircOptionsXML();
            validator = config.getValidatorOptionsXML();
            //validator = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("options-validator.xml").toURI())));
        }
  		catch (Exception e) {
            e.printStackTrace();
  		}
  	}
  	return validator;
  }

}
