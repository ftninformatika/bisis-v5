package com.ftninformatika.bisis.circ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.circ.pojo.PictureBook;
import com.ftninformatika.bisis.circ.pojo.Duplicate;
import com.ftninformatika.bisis.circ.pojo.CorporateMember;
import com.ftninformatika.bisis.circ.pojo.MembershipType;
import com.ftninformatika.bisis.circ.pojo.UserCategory;
import com.ftninformatika.bisis.circ.pojo.Organization;
import com.ftninformatika.bisis.reservations.Reservation;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.utils.date.DateUtils;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author Petar
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_members")
@ToString(of = {"userId","firstName"})
public class Member implements java.io.Serializable {

	@Id
    private String _id;
	private String inUseBy;
	private Organization organization;
	private String language;
	private String educationLevel;
	private MembershipType membershipType;
	private UserCategory userCategory;
	private CorporateMember corporateMember;
	private String userId;
	private String firstName;
	private String lastName;
	private String parentName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email; // ovo je username za prijavljivanje
	private String password;
	private String jmbg;
	private Date birthday;
	private Integer docId;
	private String docNo;
	private String docCity;
	private String country;
	private String gender;
	private String age;
	private String secAddress;
	private String secZip;
	private String secCity;
	private String secPhone;
	private String note;
	private String oldNumbers;
	private String interests;
	private Integer warningInd;
	private String occupation;
	private String title;
	private String indexNo;
	private Integer classNo;
	private String blockReason;
	private String pin;
	private boolean activatedWebProfile;

	private List<Signing> signings = new ArrayList<>();
	private List<Duplicate> duplicates = new ArrayList<>();
	private List<PictureBook> picturebooks = new ArrayList<>();
	private List<ReservationOnProfile> reservations = new ArrayList<>();

	public void appendReservation(ReservationOnProfile reservation){
		reservations.add(reservation);
	}

	@JsonIgnore
	public String getLibrarianForSigningDate(Date date){
		for (Signing s: signings){
			if (DateUtils.compareDates(date, s.getSignDate()))
				return s.getLibrarian();
		}
		return null;
	}

	@JsonIgnore
	public String getRecieptForSigingDate(Date date){

		for (Signing s: signings){
			if (DateUtils.compareDates(date, s.getSignDate()))
				return s.getReceipt();
		}
		return null;
	}
	@JsonIgnore
	public Double getCostForSigningDate(Date date){

		for (Signing s: signings){
			if (DateUtils.compareDates(date, s.getSignDate()))
				return s.getCost();
		}
		return null;
	}


}
