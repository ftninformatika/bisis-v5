package com.ftninformatika.bisis.circ;

// Generated Nov 15, 2006 4:24:31 PM by Hibernate Tools 3.2.0.beta8

import com.ftninformatika.bisis.circ.pojo.Warning;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_lendings")
@ToString(of = {"ctlgNo", "lendDate", "returnDate"})
public class Lending implements java.io.Serializable {

	@Id
	private String _id;
    private String userId;
    private String ctlgNo;
	private String location;
	private String librarianLend;
	private String librarianReturn;
	private String librarianResume;
	private Date lendDate;
	private Date returnDate;
	private Date resumeDate;
    private Date deadline;
    private List<Warning> warnings = new ArrayList<>();

}
