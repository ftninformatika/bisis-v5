package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.core.repositories.MemberRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.exception.model.*;
import com.ftninformatika.bisis.opac.dto.AudioBookRequestDTO;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@PropertySource(value = "classpath:knjigaprica-secret.properties")
public class AudioBookService {

    @Value("${knjigaprica.api.url}")
    private String knjigaPricaApiUrl;

    @Value("${knjigaprica.api.key}")
    private String knjigaPricaApiKey;

    private final MemberRepository memberRepository;
    private final RecordsRepository recordsRepository;

    public AudioBookService(MemberRepository memberRepository, RecordsRepository recordsRepository) {
        this.memberRepository = memberRepository;
        this.recordsRepository = recordsRepository;
    }

    public String getKnjigaPricaApiUrl() {
        return knjigaPricaApiUrl;
    }

    public String getKnjigaPricaApiKey() {
        return knjigaPricaApiKey;
    }

    public AudioBookRequestDTO createKnjigaPricaRequestBody(Integer rn) {
        Record record = recordsRepository.getByRn(rn);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = ((BisisUserDetailsImpl)authentication.getPrincipal()).getUsername();
        String library = ((BisisUserDetailsImpl)authentication.getPrincipal()).getLibrary();

        Member member = memberRepository.getMemberByEmail(email);
        AudioBookRequestDTO retVal = new AudioBookRequestDTO();
        retVal.setMemberData(member);
        retVal.setBookId(record.get_id()); //TODO: proveriti odakle iz zapisa se cupa ID koji je vezan za knjigaprica i zameniti ovu vrednost
        retVal.setUserIdentifier(email);
        retVal.setLibraryName(library);

        return retVal;
    }

    //    dobiju podatak da li ulogovani korisnik
    //    (a) jeste član,
    //    (b) ima aktivnu članarinu,
    //    (c) jeste slepa ili slabovida osoba, kao i da li u UNIMARC zapisu koji odgovara pozajmljenoj knjizi ima podatak o njihovoj audioknjizi.
    public String audioBookForBlind(Integer rn, String memberId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("Корисник није пријављен.");
        }

        Date today = DateUtils.getStartOfDay(new Date());
        Member member = memberRepository.getMemberByUserId(memberId);
        if (member == null) {
            throw new UnknownMemberException();
        }

        //        TODO: promeniti
        if (member.getMembershipType().getDescription().contains("B5")) {
            throw new UnknownMemberException("Пријављени корнисник није слепа/слабовида особа.");
        }

        boolean signingActive = false;
        for (Signing s:  member.getSignings()) {
            if (s.getSignDate().compareTo(today) * s.getUntilDate().compareTo(today) >= 0) {
                signingActive = true;
                break;
            }
        };

        if (!signingActive) {
            throw new MembershipNotActiveException();
        }

        String audioBookUrl = null;
        Record record = recordsRepository.getByRn(rn);

        for (Field f : record.getFields("452")) {
            Subfield sfUrl = f.getSubfield('u');
            Subfield sfSource = f.getSubfield('5');
            if (sfUrl != null && sfSource != null && sfSource.getContent().equals(SLEPI)) {
                audioBookUrl = f.getSubfield('u').getContent(); // TODO: ovo je url, proveriti jos jednom u koje polje ID ide
            }
        }

        if (audioBookUrl == null) {
            throw new AudioBookDoesNotExistInRecordException("Не постоји аудио књига за слепе/слабовиде особе за тражени запис.");
        }

        return audioBookUrl;
    }

    private static String SLEPI =  "slepislabovidi";
}
