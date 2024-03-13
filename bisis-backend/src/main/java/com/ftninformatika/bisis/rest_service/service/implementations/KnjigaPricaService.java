package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.core.repositories.MemberRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.dto.KnjigaPricaRequestDTO;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "classpath:knjigaprica-secret.properties")
public class KnjigaPricaService {

    @Value("${knjigaprica.api.url}")
    private String knjigaPricaApiUrl;

    @Value("${knjigaprica.api.key}")
    private String knjigaPricaApiKey;

    private final MemberRepository memberRepository;
    private final RecordsRepository recordsRepository;

    public KnjigaPricaService(MemberRepository memberRepository, RecordsRepository recordsRepository) {
        this.memberRepository = memberRepository;
        this.recordsRepository = recordsRepository;
    }

    public String getKnjigaPricaApiUrl() {
        return knjigaPricaApiUrl;
    }

    public String getKnjigaPricaApiKey() {
        return knjigaPricaApiKey;
    }

    public KnjigaPricaRequestDTO createKnjigaPricaRequestBody(Integer rn) {
        Record record = recordsRepository.getByRn(rn);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = ((BisisUserDetailsImpl)authentication.getPrincipal()).getUsername();
        String library = ((BisisUserDetailsImpl)authentication.getPrincipal()).getLibrary();

        Member member = memberRepository.getMemberByEmail(email);
        KnjigaPricaRequestDTO retVal = new KnjigaPricaRequestDTO();
        retVal.setMemberData(member);
        retVal.setBookId(record.get_id()); //TODO: proveriti odakle iz zapisa se cupa ID koji je vezan za knjigaprica i zameniti ovu vrednost
        retVal.setUserIdentifier(email);
        retVal.setLibraryName(library);

        return retVal;
    }
}
