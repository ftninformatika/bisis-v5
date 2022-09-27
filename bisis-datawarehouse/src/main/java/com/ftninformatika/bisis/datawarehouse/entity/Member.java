package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the member database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="member", schema = "bisis_reports", catalog = "bisis")
@AttributeOverride(name = "description", column = @Column(name = "first_name", insertable = false,updatable = false))

public class Member extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String city;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="member")
	private Set<Lending> lendings;

	//bi-directional many-to-one association to Membership
	@OneToMany(mappedBy="member")
	private Set<Membership> memberships;

}
