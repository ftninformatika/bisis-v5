package com.ftninformatika.bisis.opac.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCardDTO {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private Date membershipUntil;
    private String libraryMemberId;
}
