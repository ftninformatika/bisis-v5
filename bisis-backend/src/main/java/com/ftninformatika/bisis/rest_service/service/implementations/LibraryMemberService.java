package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.core.repositories.LendingRepository;
import com.ftninformatika.bisis.core.repositories.LibrarianRepository;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.pojo.UserCategory;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.dto.MemberCardDTO;
import com.ftninformatika.bisis.opac.dto.ProlongLendingResponseDTO;
import com.ftninformatika.bisis.opac.dto.ShelfDto;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.reservations.service.impl.OpacReservationsService;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.bisisauthentication.security.JWTUtil;
import com.ftninformatika.utils.constants.ReservationsConstants;
import com.ftninformatika.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author badf00d21  5.7.19.
 */
@Service
public class LibraryMemberService {


    @Value("security.token.secret.key")
    private String tokenKey;

    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired LibraryPrefixProvider libraryPrefixProvider;
    @Autowired OpacSearchService opacSearchService;
    @Autowired RecordsRepository recordsRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired
    LibrarianRepository librarianRepository;
    @Autowired LendingRepository lendingRepository;
    @Autowired OpacReservationsService reservationsService;
    @Autowired JWTUtil jwtUtil;

    /**
     * Resume lending for authenticated OPAC user
     */
    public ProlongLendingResponseDTO prolongLending(String library, String email, String lendingId) {
        ProlongLendingResponseDTO prolongResponseDTO = new ProlongLendingResponseDTO();

        LibraryMember libraryMember = libraryMemberRepository.findByUsername(email);
        if (libraryMember == null || libraryMember.getIndex() == null){
            prolongResponseDTO.setProlongable(false);
            return prolongResponseDTO;
        }

        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        Optional<Lending> lending = lendingRepository.findById(lendingId);
        if (!member.isPresent() || !lending.isPresent()
                || lending.get().getResumeDate() != null || lending.get().getDeadline() == null) {
            prolongResponseDTO.setProlongable(false);
            return prolongResponseDTO;
        }
        UserCategory category = member.get().getUserCategory();

        Date deadLineDate = lending.get().getDeadline();
        Date today = new Date();
        Date prolongDate = DateUtils.incDecDays(deadLineDate, category.getPeriod());
        Date maxDate = DateUtils.incDecDays(deadLineDate, category.getMaxPeriod());

        // if there are reservations in the queue, forbid prolonging
        if (reservationsService.isReservationPresentOnLocation(library, lending.get().getCtlgNo())){
            prolongResponseDTO.setProlongable(false);
            prolongResponseDTO.setMessage(ReservationsConstants.PROLONG_NOT_ALLOWED);
            return prolongResponseDTO;
        }

        Lending l = lending.get();

        if (prolongDate.before(today)) {
            prolongResponseDTO.setProlongable(false);
            return prolongResponseDTO;
        }

        if (maxDate.before(prolongDate)) {
            return getLendingResponseDTO(maxDate, l);
        }

        return getLendingResponseDTO(prolongDate, l);
    }

    private ProlongLendingResponseDTO getLendingResponseDTO(Date prolongDate, Lending l) {
        l.setResumeDate(new Date());
        l.setDeadline(prolongDate);
        l.setLibrarianResume("member");
        lendingRepository.save(l);

        ProlongLendingResponseDTO prolongResponseDTO = new ProlongLendingResponseDTO();
        prolongResponseDTO.setProlongable(true);
        prolongResponseDTO.setDeadline(prolongDate);
        prolongResponseDTO.setResume(l.getResumeDate());
        return prolongResponseDTO;
    }

    /**
     * Hash library user password and activate OPAC profile
     * Set library prefix provider according to library from user info,
     * to be able to read from right repo
     * @param libraryMember library user with plain text pass
     * @return
     */
    public boolean activateMember(LibraryMember libraryMember) {
        List<String> allPrefixes = libraryConfigurationRepository.findAll()
                .stream().map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        if (libraryMember == null || libraryMember.getLibraryPrefix() == null
            || !allPrefixes.contains(libraryMember.getLibraryPrefix()))
            return false;
        libraryPrefixProvider.setPrefix(libraryMember.getLibraryPrefix());
        if (libraryMember.getAuthorities().contains(Authority.ROLE_ADMIN)) {
            return activateAdmin(libraryMember);
        }
        String hashedPass = BCrypt.hashpw(libraryMember.getPassword(), BCrypt.gensalt());
        Member member = memberRepository.findById(libraryMember.getIndex()).get();
        libraryMember.setPassword(hashedPass);
        libraryMember.setProfileActivated(true);
        libraryMember.setActivationToken(null);
        LibraryMember savedLm = libraryMemberRepository.save(libraryMember);
        member.setActivatedWebProfile(true);
        member = memberRepository.save(member);
        return (savedLm != null && member != null);
    }


    /**
     * Activates account for admin users
     * @param libraryMember
     * @return true if activated
     */
    public boolean activateAdmin(LibraryMember libraryMember) {
        String hashedPass = BCrypt.hashpw(libraryMember.getPassword(), BCrypt.gensalt());
        LibrarianDB librarian = librarianRepository.findById(libraryMember.getLibrarianIndex()).get();
        libraryMember.setPassword(hashedPass);
        libraryMember.setProfileActivated(true);
        libraryMember.setActivationToken(null);
        LibraryMember savedLm = libraryMemberRepository.save(libraryMember);
        librarian.setRole(Librarian.Role.OPACADMIN);
        librarian = librarianRepository.save(librarian);
        return (savedLm != null && librarian != null);
    }

    /**
     *
     * @param libraryMember- new OPAC account, without activation link
     * @return - activation token, valid for next 5 days
     */
    public String generateActivationToken(LibraryMember libraryMember) {
        UserDetails userDetails = new BisisUserDetailsImpl(libraryMember);
        return jwtUtil.generateToken(userDetails);
    }

    /**
     *
     * @param shelfDto obj with username and MongoId of book that needs
     *        to be added to shelf
     * @return indicator if book put on shelf
     */
    public boolean addToShelf(ShelfDto shelfDto) {
        if (isShelfDtoValid(shelfDto))
            return false;
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(shelfDto.getEmail());
        if (libraryMember == null)
            return false;
        if (!recordsRepository.findById(shelfDto.getBookId()).isPresent())
            return false;
        if (libraryMember.getMyBookshelfBooks() == null)
            libraryMember.setMyBookshelfBooks(new ArrayList<>());
        if (libraryMember.getMyBookshelfBooks().contains(shelfDto.getBookId()))
            return false;
        libraryMember.getMyBookshelfBooks().add(shelfDto.getBookId());
        libraryMemberRepository.save(libraryMember);
        return true;
    }

    public boolean removeFromShelf(ShelfDto shelfDto) {
        if (isShelfDtoValid(shelfDto))
            return false;
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(shelfDto.getEmail());
        if (libraryMember == null)
            return false;
        if (!recordsRepository.findById(shelfDto.getBookId()).isPresent())
            return false;
        if (libraryMember.getMyBookshelfBooks() == null)
            libraryMember.setMyBookshelfBooks(new ArrayList<>());
        if (!libraryMember.getMyBookshelfBooks().contains(shelfDto.getBookId()))
            return false;
        libraryMember.getMyBookshelfBooks().remove(shelfDto.getBookId());
        libraryMemberRepository.save(libraryMember);
        return true;
    }

    public List<Book> getShelf(String username, String lib) {
        if (username == null || username.trim().equals(""))
            return null;
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(username);
        if (libraryMember == null)
            return null;
        List<Book> retVal = new ArrayList<>();
        if (libraryMember.getMyBookshelfBooks() == null || libraryMember.getMyBookshelfBooks().size() == 0)
            return retVal;
        for (Record r : recordsRepository.findAllById(libraryMember.getMyBookshelfBooks())) {
            Book b = opacSearchService.getBookByRec(r);
            retVal.add(b);
        }
        return retVal;
    }

    private boolean isShelfDtoValid(ShelfDto shelfDto) {
        return  ((shelfDto == null || shelfDto.getEmail() == null ||
                shelfDto.getEmail().trim().equals("") ||
                shelfDto.getBookId() == null ||
                shelfDto.getBookId().trim().equals("")));
    }

    public MemberCardDTO getMemberCard(String library, String email) {
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(email);
        if (libraryMember == null || libraryMember.getIndex() == null){
            return null;
        }
        Optional<Member> memberOptional = memberRepository.findById(libraryMember.getIndex());
        if (!memberOptional.isPresent()) {
            return null;
        }
        MemberCardDTO memberCardDTO = new MemberCardDTO();

        Member member = memberOptional.get();
        memberCardDTO.setUserId(member.getUserId());
        memberCardDTO.setUsername(libraryMember.getUsername());
        memberCardDTO.setFirstName(member.getFirstName());
        memberCardDTO.setLastName(member.getLastName());
        memberCardDTO.setLibraryMemberId(libraryMember.get_id());
        memberCardDTO.setChild(member.getAge().equals("C"));
        
        Date date = null;
        for (Signing signing : member.getSignings()) {
            if (date == null || date.before(signing.getUntilDate())) {
                date = signing.getUntilDate();
            }
        }
        memberCardDTO.setMembershipUntil(date);
        return memberCardDTO;
    }

}
