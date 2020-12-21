package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Membership;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.dto.ConfirmReservationDTO;
import com.ftninformatika.bisis.circ.dto.CurrentReservationDTO;
import com.ftninformatika.bisis.circ.pojo.Duplicate;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.circ.pojo.Warning;
import com.ftninformatika.bisis.circ.view.*;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.circ.wrappers.MergeData;
import com.ftninformatika.bisis.ecard.ElCardInfo;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.validators.memberdata.DataErrors;
import com.ftninformatika.utils.validators.memberdata.DataValidator;
import com.ftninformatika.utils.validators.memberdata.MemberDataDatesValidator;
import com.ftninformatika.utils.validators.memberdata.MemberDateError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserManager {

    private Member member;
    private CorporateMember corporateMember;
    private String chargedUser;
    private String chargeBook = "";
    private List warnings = null;
    private String env = null;
    private String validator = null;
    private List lendings =  new ArrayList();
    private ArrayList usersReservation = new ArrayList();
    private static Logger log = Logger.getLogger(UserManager.class);
    private String defaultLocation;

    // list of returned books
    private List<String> returnedBooks;

    private List<ReservationDTO> reservationsForPrint;

    public UserManager() {
    }

    public Member getMember() {
        return member;
    }

    public List<ReservationDTO> getReservationsForPrint(){
        return this.reservationsForPrint;
    }
    /**
     * This method is called when book(s) is returned to the library.
     * If there is at least one returned book, get first reservation from queue for that book
     */
    public List<ReservationDTO> getReservationsForReturnedBooks(String ctlgNo) {
        this.reservationsForPrint = new ArrayList<>();

        if (!ctlgNo.equals("")){   // razduzivanje jednog primerka iz stabla
            List<String> oneReturn = new ArrayList<>();
            oneReturn.add(ctlgNo);
            try {
                this.reservationsForPrint =  BisisApp.bisisService.getReservationsForReturnedBooks(oneReturn).execute().body();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                log.error(ioException);
            }
        }else if (this.returnedBooks.size() > 0){
            try {
                this.reservationsForPrint =  BisisApp.bisisService.getReservationsForReturnedBooks(this.returnedBooks).execute().body();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                log.error(ioException);
            }
            this.returnedBooks.clear();
        }
        return this.reservationsForPrint;
    }

    public ReservationDTO confirmReservationAndAssignBook(ReservationDTO r) throws IOException {
        ConfirmReservationDTO confirmReservationDTO = new ConfirmReservationDTO(r.get_id(), r.getRecord_id(), r.getCtlgNo());
        log.info("Potvrda rezervacije za record: " + r.getRecord_id() + " za primerak: " + r.getCtlgNo());
        return BisisApp.bisisService.confirmReservation(confirmReservationDTO).execute().body();
    }

    // in tree view, when librarian clicks on button to display reservation info
    public ReservationDTO getCurrentReservationByPrimerak(String ctlgNo, String userId){
        this.reservationsForPrint = new ArrayList<>();
        CurrentReservationDTO currentReservation = new CurrentReservationDTO(userId, ctlgNo);
        try {
            ReservationDTO reservation = BisisApp.bisisService.getCurrentReservationByPrimerak(currentReservation).execute().body();
            if (reservation != null){
                this.reservationsForPrint.add(reservation);
                return reservation;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            log.error(ioException);
        }
        return null;
    }

    public ReservationDTO getNextReservation(String  userId, String ctlgNo){
        this.reservationsForPrint = new ArrayList<>();
        CurrentReservationDTO currentReservation = new CurrentReservationDTO(userId, ctlgNo);
        try {
            ReservationDTO nextReservation =  BisisApp.bisisService.getNextReservation(currentReservation).execute().body();
            log.info("Get next reservation - Rezervacija za primerak: " + ctlgNo + " je obrisana korisniku: " + userId);
            if (nextReservation != null){
                log.info("Get next reservation - Prihvacena je rezervacija za primerak: " + nextReservation.getCtlgNo() +
                        " za korisnika: " + nextReservation.getUserId());
                this.reservationsForPrint.add(nextReservation);
                return nextReservation;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            log.error(ioException);
        }
        return null;
    }

    public String saveUser(User user) {
        if (user.getDirty()) {
            String memberExists;


            try {
                memberExists = BisisApp.bisisService.memberExist(user.getMmbrship().getUserID()).execute().body();
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                return Messages.getString("USER_MANAGER_CONNECTION_ERROR");
            }

            if (member == null) {
                if (memberExists != null && !memberExists.equals("")) {
                    log.info("Broj korisnika vec postoji:" + (member == null? "null" : member.getUserId()));
                    return Messages.getString("USER_MANAGER_USER_ALREADY_EXIST");
                }
                member = new Member();
            } else {
                if (memberExists != null && !memberExists.equals("") && !memberExists.equals(member.get_id())) {
                    log.info("Broj korisnika vec postoji:" + (member == null? "null" : member.getUserId()));
                    return Messages.getString("USER_MANAGER_USER_ALREADY_EXIST");
                }
            }
            member = toObjectModel(user, member);


            if (BisisApp.appConfig.getClientConfig().getPincodeEnabled().equals("yes") && (member.getPin() == null || member.getPin().equals(""))) {
                String pin = Utils.generatePin();
                member.setPin(pin);
                user.getUserData().setPinCode(pin);
            }

            MemberData memberData = new MemberData();
            memberData.setMember(member);

            if (!lendings.isEmpty()) {
                memberData.setLendings(lendings);
                memberData.setBooks(Cirkulacija.getApp().getRecordsManager().getListOfItems());
            }

            MemberDateError errorInDates = MemberDataDatesValidator.validateMemberDataDates(memberData);
            if (errorInDates != MemberDateError.NO_ERROR) {
                return Messages.getString(errorInDates.getMessageKey());
            }
            try {
                // before updating member data, check if there are any books that are being returned
                this.returnedBooks = new ArrayList<>();
                for (ItemAvailability ia : memberData.getBooks()){
                    if (!ia.getBorrowed()){
                        this.returnedBooks.add(ia.getCtlgNo());
                    }
                }

                // update member's data
                memberData = BisisApp.bisisService.addUpdateMemberData(memberData).execute().body();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Greska pri cuvanju korisnika: " + user.getMmbrship().getUserID());
                log.error(e);
                return Messages.getString("USER_MANAGER_CONNECTION_ERROR");
            }



            if (memberData != null) {

                log.info("Korisnik sacuvan: " + user.getMmbrship().getUserID());
//                try {
//                    memberData =BisisApp.bisisService.getMemberById(member.getUserId()).execute().body();
                    member = memberData.getMember();
                    lendings = memberData.getLendings() != null ? memberData.getLendings() : new ArrayList();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                if (member != null){
                    Cirkulacija.getApp().getRecordsManager().getListOfItems().clear();
                    loadUser(user, member, lendings);
                }
                return "ok";
            } else {
                log.error("Greska pri cuvanju podataka: " + user.getMmbrship().getUserID());
                return Messages.getString("USER_MANAGER_CONNECTION_ERROR");
            }
        } else {
            return "ok";
        }
    }

    public boolean releaseUser() {
        Boolean released = true;
        if (member != null && member.get_id() != null) {
            released = false;
            log.info("Otkljucavanje korisnika: " + member.getUserId());
            try {
                released = BisisApp.bisisService.releaseMemberById(member.getUserId()).execute().body();
            } catch (Exception e) {
                log.error(e);
                //e.printStackTrace();
            }
        }
        if (released) {
            member = null;
            lendings = null;
            chargeBook = "";
            Cirkulacija.getApp().getRecordsManager().releaseListOfItems();
            Cirkulacija.getApp().getMainFrame().setRequestedPanel(0);
            log.info("Otkljucan korisnik");
        } else {
            log.info("Otkljucavanje nije uspelo");
        }
        return released;
    }

    public boolean gotUser() {
        return member != null;
    }

    public String getCurrentUserId() {
        return member.getUserId();
    }

    public String saveGroup(Group group) {
        if (corporateMember == null) {
            corporateMember = new CorporateMember();
        }
        corporateMember = toObjectModel(group, corporateMember);
        Boolean saved = false;
        try {
            saved = BisisApp.bisisService.saveCorporateMember(corporateMember).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (saved) {
            return "ok";
        } else {
            return Messages.getString("USER_MANAGER_CONNECTION_ERROR");
        }
    }

    public void releaseGroup() {
        corporateMember = null;
    }

    public void initialiseUser(User user) {
        member = new Member();
        lendings = new ArrayList<Lending>();
        user.getMmbrship().setTableModel(member.getSignings());
        user.getLending().setTableModel(lendings);
        user.getUserData().setDupTableModel(member.getDuplicates());
    }

    public int showUser(User user, String userID) {
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
        return getUser(user, null, userID);
    }

    public int showChargedUser(User user) {
        return getUser(user, null, chargedUser);
    }

    public void chargeUser(String ctlgno) {
        if (member == null) {
            chargeBook = ctlgno;
            Cirkulacija.getApp().getMainFrame().setRequestedPanel(3);
            Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
        } else {
            Cirkulacija.getApp().getMainFrame().getUserPanel().getLending().lendBook(ctlgno);
            Cirkulacija.getApp().getMainFrame().previousTwoPanels();
            //Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
        }
    }

    public String archiveUser(User user) {
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
					return deleteUser.getMessageKey();
				}
				
			} else {
				return archiveUser.getMessageKey();
			}
		} else {
			return "Gre\u0161ka u konekciji s bazom podataka!";
		}*/
        return null;
    }

    public int getUser(User user, Group group, String userID) {
        int found = 0;

        try {
            log.info("Otvaranje korisnika: " + userID);
            MemberData memberData = BisisApp.bisisService.getAndLockMemberById(userID, BisisApp.appConfig.getLibrarian().get_id()).execute().body();
            if (memberData != null) {
                log.info("Pronadjen individualni korisnik: " + userID);
                if (memberData.getInUseBy() == null) {
                    member = memberData.getMember();
                    member.getSignings().sort(Comparator.comparing(Signing::getSignDate));
                    lendings = memberData.getLendings();
                    if (member != null) {
                        Cirkulacija.getApp().getRecordsManager().getListOfItems().clear();
                        loadUser(user, member, lendings);
                        found = 1;
                        return found;
                    }
                } else {
                    log.info("Zakljucan korisnik: " + userID);
                    found = 3;
                    return found;
                }
            }

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }

        try {
            corporateMember = BisisApp.bisisService.getCorporateMemberById(userID).execute().body();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        if (corporateMember != null) {
            log.info("Pronadjen kolektivni korisnik: " + userID);
            group.loadGroup(corporateMember.getUserId(), corporateMember.getInstName(), corporateMember.getSignDate(), corporateMember.getAddress(), corporateMember.getCity(), Utils.getString(corporateMember.getZip()),
                    corporateMember.getPhone(), corporateMember.getEmail(), corporateMember.getFax(), corporateMember.getSecAddress(), Utils.getString(corporateMember.getSecZip()), corporateMember.getSecCity(),
                    corporateMember.getSecPhone(), corporateMember.getContFirstName(), corporateMember.getContLastName(), corporateMember.getContEmail());
            found = 2;
            return found;
        }
        return found;
    }

    public int getUserByECard(User user, Group group, ElCardInfo info) {
        int found = 0;

        try {
            MemberData memberData = BisisApp.bisisService.getAndLockByECard(info, BisisApp.appConfig.getLibrarian().get_id()).execute().body();
            if (memberData != null) {
                if (memberData.getInUseBy() == null) {
                    member = memberData.getMember();
                    member.getSignings().sort(Comparator.comparing(Signing::getSignDate));
                    lendings = memberData.getLendings();
                    if (member != null) {
                        Cirkulacija.getApp().getRecordsManager().getListOfItems().clear();
                        loadUser(user, member, lendings);
                        found = 1;
                        return found;
                    }
                } else {
                    found = 3;
                    return found;
                }
            }

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }

        return found;
    }

    private void loadUser(User user, Member member, List<Lending> lendings) {
        boolean blocked = false;
        String blockedInfo = "";
        if (member.getBlockReason() != null && !"".equals(member.getBlockReason())) {
            blockedInfo = "Blokirano: " + member.getBlockReason();
            blocked = true;
        }

        Iterator it = member.getDuplicates().iterator();
        String dupno = "";
        while (it.hasNext()) {
            Duplicate dup = (Duplicate) it.next();
            dupno = "Duplikat " + dup.getDupNo();
        }

        user.getUserData().loadUser(member.getFirstName(), member.getLastName(), member.getParentName(),
                member.getAddress(), member.getZip(), member.getCity(), member.getPhone(),
                member.getEmail(), member.getGender(), member.getAge(), member.getSecAddress(),
                member.getSecCity(), member.getSecZip(), member.getSecPhone(), member.getJmbg(), member.getBirthday(),
                member.getDocId(), member.getDocNo(), member.getDocCity(), member.getCountry(),
                member.getTitle(), member.getOccupation(), member.getIndexNo(), Utils.getString(member.getClassNo()),
                member.getOrganization(), member.getEducationLevel(), member.getLanguage(), member.getNote(), member.getOldNumbers(),
                member.getInterests(), member.getWarningInd(), blocked, member.getBlockReason(), member.getDuplicates(), member.getPin(), member.isActivatedWebProfile());

        user.getMmbrship().loadUser(member.getUserId(), member.getMembershipType(), member.getUserCategory(), member.getCorporateMember(), member.getSignings());

        Date maxDate = null;
        Date date = null;
        it = member.getSignings().iterator();
        while (it.hasNext()) {
            date = ((Signing) it.next()).getUntilDate();
            if (date != null && (maxDate == null || maxDate.before(date))) {
                maxDate = date;
            }
        }

        if (maxDate != null && maxDate.before(new Date())) {
            if (!blockedInfo.equals(""))
                blockedInfo = blockedInfo + ", ";
            blockedInfo = blockedInfo + "Istekla \u010dlanarina";
        }

        warnings = new ArrayList<Warning>();
        for (Lending lend : lendings) {
            Set<Warning> warns = new HashSet<Warning>(lend.getWarnings());
            for (Warning warn : warns) {
                warnings.add(warn);
            }
        }

        user.getLending().loadUser(member.getUserId(), member.getFirstName(), member.getLastName(), maxDate, member.getNote(), dupno, blockedInfo, lendings, !warnings.isEmpty());

        user.getReservationsPanel().loadUser(member.getUserId(), member.getFirstName(), member.getLastName(),
                maxDate, member.getNote(), dupno, blockedInfo, member.getReservations(), !warnings.isEmpty());

        user.setDirty(false);

        if (!chargeBook.equals("")) {
            user.getLending().lendBook(chargeBook);
            chargeBook = "";
            user.setDirty(true);
        }

    }

    public String getLibraryBranchName(String coderId){
        try {
            return BisisApp.bisisService.getLibraryBranchName(coderId).execute().body();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return coderId;
        }
    }

    public void refreshInfo(User user, Member member) {
        boolean blocked = false;
        String blockedInfo = "";
        if (member.getBlockReason() != null && !"".equals(member.getBlockReason())) {
            blockedInfo = "Blokirano: " + member.getBlockReason();
            blocked = true;
        }

        Iterator it = member.getDuplicates().iterator();
        String dupno = "";
        while (it.hasNext()) {
            Duplicate dup = (Duplicate) it.next();
            dupno = "Duplikat " + dup.getDupNo();
        }

        Date maxDate = null;
        Date date = null;
        it = member.getSignings().iterator();
        while (it.hasNext()) {
            date = ((Signing) it.next()).getUntilDate();
            if (date != null && (maxDate == null || maxDate.before(date))) {
                maxDate = date;
            }
        }

        if (maxDate != null && maxDate.before(new Date())) {
            if (!blockedInfo.equals(""))
                blockedInfo = blockedInfo + ", ";
            blockedInfo = blockedInfo + "Istekla \u010dlanarina";
        }

        user.getLending().refreshInfo(member.getUserId(), member.getFirstName(), member.getLastName(), maxDate, member.getNote(), dupno, blockedInfo);

    }

    public String createWebAccount() throws Exception {
        if (member == null)
            throw new Exception(Messages.getString("USER_MANAGER_USER_NOT_LOADED"));
        if (DataValidator.validateEmail(member.getEmail()) == DataErrors.EMAIL_FORMAT_INVALID)
            throw new Exception(Messages.getString(DataErrors.EMAIL_FORMAT_INVALID.getMessageKey()));
        if (member.isActivatedWebProfile())
            throw new Exception(Messages.getString(""));
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setAuthorities(new ArrayList<>(Arrays.asList(Authority.ROLE_USER)));
        libraryMember.setUsername(member.getEmail());
        libraryMember.setLibraryPrefix(BisisApp.appConfig.getLibrary());
        libraryMember.setIndex(member.get_id());
        libraryMember.setProfileActivated(false);
        Response<LibraryMember> createdMemberResp = BisisApp.bisisService.createWebAccount(libraryMember).execute();
        if (createdMemberResp.code() == HttpStatus.CONFLICT.value())
            throw new Exception(Messages.getString("USER_MANAGER_EMAIL_ALREADY_EXIST"));
        if (createdMemberResp.code() == HttpStatus.EXPECTATION_FAILED.value())
            throw new Exception(Messages.getString("USER_MANAGER_INVALID_USER_DATA"));
        if (createdMemberResp.body() == null)
            throw new Exception(Messages.getString("USER_MANAGER_CONNECTION_ERROR"));
        return Messages.getString("USER_MANAGER_ACTIVATION_EMAIL_SENT");
    }


    public void loadCombos(User user) throws Exception {

        if (BisisApp.appConfig.getCodersHelper() == null)
            throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");

        user.getUserData().loadEduLvl(BisisApp.appConfig.getCodersHelper()
                .getEducationLevels().stream()
                .map(i -> i.getDescription())
                .collect(Collectors.toList()));

        user.getUserData().loadLanguage(BisisApp.appConfig.getCodersHelper()
                .getLanguages().stream()
                .map(i -> i.getDescription())
                .collect(Collectors.toList()));

        user.getUserData().loadOrganization(BisisApp.appConfig.getCodersHelper()
                .getOrganizations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.Organization o = new com.ftninformatika.bisis.circ.pojo.Organization();
                    o.setAddress(i.getAddress());
                    o.setCity(i.getCity());
                    o.setId(i.get_id());
                    o.setName(i.getName());
                    o.setZip(i.getZip());
                    return o;
                })
                .collect(Collectors.toList()));

        user.getMmbrship().loadGroups(BisisApp.appConfig.getCodersHelper()
                .getCorporateMembers().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CorporateMember c = new com.ftninformatika.bisis.circ.pojo.CorporateMember();
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
                .getCircLocations().stream()
                .map(i -> i.getDescription())
                .collect(Collectors.toList()));

        user.getMmbrship().loadBranchID(BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList()));

        List<com.ftninformatika.bisis.circ.pojo.MembershipType> membershipTypes = BisisApp.appConfig.getCodersHelper()
                .getMembershipTypes().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.MembershipType m = new com.ftninformatika.bisis.circ.pojo.MembershipType();
                    m.setDescription(i.getDescription());
                    m.setPeriod(i.getPeriod());
                    return m;
                }).collect(Collectors.toList());
        Collections.sort(membershipTypes, new com.ftninformatika.bisis.circ.pojo.MembershipType.MembershiTypeComparator());
        user.getMmbrship().loadMmbrType(membershipTypes);

        List<com.ftninformatika.bisis.circ.pojo.UserCategory> userCategories = BisisApp.appConfig.getCodersHelper()
                .getUserCategories().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.UserCategory u = new com.ftninformatika.bisis.circ.pojo.UserCategory();
                    u.setDescription(i.getDescription());
                    u.setMaxPeriod(i.getMaxPeriod());
                    u.setPeriod(i.getPeriod());
                    u.setTitlesNo(i.getTitlesNo());
                    return u;
                }).collect(Collectors.toList());
        Collections.sort(userCategories, new com.ftninformatika.bisis.circ.pojo.UserCategory.UserCategoryComparator());
        user.getMmbrship().loadUserCateg(userCategories);

        user.getLending().loadLocation(BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList()));


    }

    public void loadCombos(Group group) throws Exception {
        group.loadBranchID((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));
    }

    public void loadCombos(SearchUsers searchusers) throws Exception {
        searchusers.loadCmbLoc1((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));
        searchusers.loadCmbLoc2((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));
    }

    public void loadCombos(SearchBooks searchbooks) throws Exception {
        searchbooks.loadCmbLocL((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));
        searchbooks.loadCmbLocR((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));
        searchbooks.loadCmbFilter((BisisApp.appConfig.getCodersHelper()
                .getLocationsList2().stream()
                .collect(Collectors.toList())));
    }

    public void loadCombos(Report report) throws Exception {
        report.loadCmbLocation((BisisApp.appConfig.getCodersHelper()
                .getCircLocations().stream()
                .map(i -> {
                    com.ftninformatika.bisis.circ.pojo.CircLocation l = new com.ftninformatika.bisis.circ.pojo.CircLocation();
                    l.setDescription(i.getDescription());
                    l.setLocationCode(i.getLocationCode());
                    return l;

                })
                .collect(Collectors.toList())));

    }

    private Member toObjectModel(User user, Member member) {
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
        member.setBirthday(data.getBirthday());
        member.setLanguage(data.getLanguages());
        member.setLastName(data.getLastName().trim());
        member.setNote(data.getNote().trim());
        member.setOldNumbers(data.getOldNumbers().trim());
        member.setOccupation(data.getOccupation().trim());
        member.setOrganization(data.getOrganization());
        member.setParentName(data.getParentName().trim());
        member.setPhone(data.getPhone().trim());
        member.setSecAddress(data.getTmpAddress().trim());
        member.setSecCity(data.getTmpCity().trim());
        member.setSecPhone(data.getTmpPhone().trim());
        member.setSecZip(data.getTmpZip());
        member.setTitle(data.getTitle().trim());
        member.setWarningInd(Integer.valueOf(data.getWarning()));
        member.setZip(data.getZip());
        if (data.getBlocked()) {
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

    private CorporateMember toObjectModel(Group group, CorporateMember corporateMember) {
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

    public void addLending(Lending lend) {
        if (!lendings.contains(lend)) {
            lendings.add(lend);
        }

    }

    public void addReservation(ReservationOnProfile reservation) {
        if (!usersReservation.contains(reservation)) {
            usersReservation.add(reservation);
        }

    }

    public void updateLending(Lending lend) {
        if (!lendings.contains(lend)) {
            lendings.add(lend);
        }
    }

    public Double getMembership(String membershipType, String userCategory) {

        for (Membership m : BisisApp.appConfig.getCodersHelper().getMemberships()) {
            if (m.getMemberType().equals(membershipType) && m.getUserCateg().equals(userCategory))
                return m.getCost();
        }
        return null;
    }

    public String getUserId(String location) {
        String loc = location;
        if (loc.equals(""))
            loc = "00";
        //loc = String.valueOf(Integer.parseInt(loc));

        Integer last = null;
        try {
            last = BisisApp.bisisService.getLastUserId(loc).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (last == null)
            return null;
        String userId = Utils.makeUserId(loc, Integer.toString(last));
        while (existsUser(userId)) {
            try {
                last = BisisApp.bisisService.getLastUserId(loc).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (last == null)
                return null;
            userId = Utils.makeUserId(loc, Integer.toString(last));
        }
        return Integer.toString(last);

    }

    public boolean existsUser(String userID) {
        String retVal = null;
        try {
            retVal = BisisApp.bisisService.memberExist(userID).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (retVal == null || retVal.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public String getChargedUser(String ctlgno, Boolean isBookReserved) {
        Member m = null;
        try {
            if (isBookReserved){
                m = BisisApp.bisisService.getAssignedUser(ctlgno).execute().body();
            }else {
                m = BisisApp.bisisService.getChargedUser(ctlgno).execute().body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = "";
        if (m != null) {
            chargedUser = m.getUserId();
            result = (m.getUserId() != null ? m.getUserId() + ", " : "") + (m.getFirstName() != null ? m.getFirstName() + " " : "") + (m.getLastName() != null ? m.getLastName() : "");
        }

        return result;
    }

    public String getChargedUserId(){
        return chargedUser;
    }


    // todo ovo je drugi flow za razduzivanje
    public boolean dischargeUser(String ctlgno) {
        Lending lending = null;
        boolean done = false;
        try {
            lending = BisisApp.bisisService.getLending(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lending != null) {
            if (lending.getLocation().equals(getDefaultLocation())) {
                lending.setReturnDate(new Date());
                lending.setLibrarianReturn(Cirkulacija.getApp().getLibrarian().getUsername());
                try {
                    done = BisisApp.bisisService.dischargeBook(lending).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return done;
    }


    public List<MemberData> getUsers(List<String> userIDs) {
        List<MemberData> memberDataList = new ArrayList<>();
        try {
            for (String userId: userIDs) {
                MemberData memberData = BisisApp.bisisService.getAndLockMemberById(userId, BisisApp.appConfig.getLibrarian().get_id()).execute().body();
                if (memberData != null) {
                    memberDataList.add(memberData);
                }
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return memberDataList;
    }

    public boolean releaseUsers(List<MemberData> memberDataList) {
        Boolean released = true;
        for (MemberData memberData: memberDataList) {
            if (memberData.getMember() != null) {
                try {
                    released = BisisApp.bisisService.releaseMemberById(memberData.getMember().getUserId()).execute().body();
                    if (!released) {
                        return released;
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
        return released;
    }

    public boolean mergeUsers(String user, String userId, List<String> userList) {
        boolean done = false;
        MergeData mergeData = new MergeData();
        mergeData.setUser(user);
        mergeData.setUserId(userId);
        mergeData.setUserList(userList);
        try {
           done = BisisApp.bisisService.merge(mergeData).execute().body();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return done;
    }

    private String getDefaultLocation() {
        if (defaultLocation == null) {
            for (CircLocation circLocation : BisisApp.appConfig.getCodersHelper().getCircLocations()) {
                if (Integer.parseInt(circLocation.getLocationCode()) == Cirkulacija.getApp().getEnvironment().getLocation()) {
                    defaultLocation = circLocation.getDescription();
                }
            }
        }
        return defaultLocation;
    }

    public List getWarnings() {
        return warnings;
    }

    public List getPicturebooks() {
        if (member != null) {
            return member.getPicturebooks();
        } else {
            return null;
        }
    }

    public String getEnvFile() {
        if (env == null) {
            try {
                CircConfig config = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
                env = config.getCircOptionsXML();
                validator = config.getValidatorOptionsXML();
                //env = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("circ-options.xml").toURI())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return env;
    }


    public String getValidatorFile() {
        if (validator == null) {
            try {
                CircConfig config = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
                env = config.getCircOptionsXML();
                validator = config.getValidatorOptionsXML();
                //validator = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("options-validator.xml").toURI())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return validator;
    }

}
