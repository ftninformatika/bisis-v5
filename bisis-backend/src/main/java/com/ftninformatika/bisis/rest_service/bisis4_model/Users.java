package com.ftninformatika.bisis.rest_service.bisis4_model;


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
@Document(collection = "gbns.members")
public class Users implements java.io.Serializable {


	@Id
    private String _id;
	private Integer sysId;
	private Integer organizationId;
	private Integer languages;
	private Integer educationLevel;
	private Integer membershipType;
	private Integer userCategory;
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

	private List<Lending> lending = new ArrayList<>();
	private List<Signing> signing = new ArrayList<>();

	/** minimal constructor */
	public Users(int sysId, String userId) {
		this.sysId = sysId;
		this.userId = userId;
	}



	
}
