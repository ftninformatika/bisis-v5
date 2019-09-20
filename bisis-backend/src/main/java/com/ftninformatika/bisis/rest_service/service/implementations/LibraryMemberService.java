package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.dto.ShelfDto;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.members.OpacMemberWrapper;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.utils.date.DateUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    /**
     * Gets wrapper object that contains library member (OPAC)
     * and its BISIS circulation model
     * @param libraryMember
     * @return
     */
    public OpacMemberWrapper getOpacWrapperMember(LibraryMember libraryMember) {
        List<String> allPrefixes = libraryConfigurationRepository.findAll()
                .stream().map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        if (libraryMember == null || libraryMember.getLibraryPrefix() == null
                || !allPrefixes.contains(libraryMember.getLibraryPrefix()))
            return null;
        libraryPrefixProvider.setPrefix(libraryMember.getLibraryPrefix());
        OpacMemberWrapper retVal = new OpacMemberWrapper();
        Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
        if (!member.isPresent())
            return null;
        libraryMember.setPassword(null);
        retVal.setLibraryMember(libraryMember);
        retVal.setMember(member.get());
        return retVal;
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
     * Checks if provided email already exist activated in
     * OPAC users collection (library_members)
     * @param email
     * @return
     */
    public boolean emailExistAndActivated(String email) {
        if (email == null) return true;
        LibraryMember lm = libraryMemberRepository.findByUsername(email);
        if (lm != null && lm.getProfileActivated()) return true;
        return false;
    }

    /**
     *
     * @param libraryMember- new OPAC account, without activation link
     * @return - activation token, valid for next 5 days
     */
    public String generateActivationToken(LibraryMember libraryMember) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", libraryMember.getUsername());
        Date activationDeadline = DateUtils.incDecDays(new Date(), 5);
        tokenData.put("acivationDate", activationDeadline);
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData).setExpiration(activationDeadline);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
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
        Iterator<Record> records =recordsRepository.findAllById(libraryMember.getMyBookshelfBooks()).iterator();
        while (records.hasNext()) {
            Record r = records.next();
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

}
