package com.ftninformatika.bisis.opac_mobile;

import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.mobile.LendingDTO;
import com.ftninformatika.bisis.rest_service.service.implementations.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/mobile/members")
public class MemberMobileController {
    @Autowired
    MemberService memberService;

    @GetMapping("/lending_history/{memberNo}")
    public ResponseEntity<List<LendingDTO>> getUserLendingHistory(@PathVariable("memberNo") String memberNo) {
        List<Report> lendingReports = memberService.getReturnedLendingsReport(memberNo);
        List<LendingDTO> lendingDTOS = convertToDTO(lendingReports);
        return new ResponseEntity<>(lendingDTOS, HttpStatus.OK);
    }

    @GetMapping("/active_lendings/{memberNo}")
    public ResponseEntity<List<LendingDTO>> getUserActiveLendings(@PathVariable("memberNo") String memberNo) {
        List<Report> lendingReports = memberService.getOnlyActiveLendingsReport(memberNo);
        List<LendingDTO> lendingDTOS = convertToDTO(lendingReports);
        return new ResponseEntity<>(lendingDTOS, HttpStatus.OK);
    }

    private List<LendingDTO> convertToDTO(List<Report> lendingReports) {
        List<LendingDTO> lendingDTOS = new ArrayList<>();
        for (Report report : lendingReports) {
            lendingDTOS.add(new LendingDTO(report));
        }

        return lendingDTOS;
    }

}
