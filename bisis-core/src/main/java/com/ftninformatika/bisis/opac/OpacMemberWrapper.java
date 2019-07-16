package com.ftninformatika.bisis.opac;

import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.circ.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  16.7.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpacMemberWrapper {
    private LibraryMember libraryMember;
    private Member member;
}
