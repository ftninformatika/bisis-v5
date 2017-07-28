package com.ftninformatika.bisis.models.circ;

import com.ftninformatika.bisis.models.circ.pojo.Signing;
import com.ftninformatika.bisis.models.circ.pojo.PictureBook;
import com.ftninformatika.bisis.models.circ.pojo.Duplicate;
import com.ftninformatika.bisis.models.circ.pojo.CorporateMember;
import com.ftninformatika.bisis.models.circ.pojo.MembershipType;
import com.ftninformatika.bisis.models.circ.pojo.Organization;
import com.ftninformatika.bisis.models.circ.pojo.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Petar
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{libraryPrefixProvider.getLibPrefix()}_member")
public class Member implements java.io.Serializable {


	@Id
    private String _id;
	private Organization organization;
	private String language;
	private String educationLevel;
	private MembershipType membershipType;
	private UserCategory userCategory;
	private CorporateMember corporateMember;
	private Integer groups;
	private String userId;
	private String firstName;
	private String lastName;
	private String parentName;
	private String address;
	private String city;
	private Integer zip;
	private String phone;
	private String email;
	private String jmbg;
	private Integer docId;
	private String docNo;
	private String docCity;
	private String country;
	private String gender;
	private String age;
	private String secAddress;
	private Integer secZip;
	private String secCity;
	private String secPhone;
	private String note;
	private String interests;
	private Integer warningInd;
	private String occupation;
	private String title;
	private String indexNo;
	private Integer classNo;
	private String pass;
	private String blockReason;

	private List<Signing> signings = new ArrayList<>();
	private List<Duplicate> duplicates = new ArrayList<>();
	private List<PictureBook> picturebooks = new ArrayList<>();

}
