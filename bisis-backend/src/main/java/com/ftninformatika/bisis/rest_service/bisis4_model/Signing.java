package com.ftninformatika.bisis.rest_service.bisis4_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Signing implements java.io.Serializable {

	@Id
	private String id;

	private int location;
	private Date signDate;
	private Date untilDate;
	private Double cost;
	private String receiptId;
  	private String librarian;



	/** minimal constructor */
	public Signing(String id, int location, String librarian,
				   Date signDate) {
		this.id = id;
		this.location = location;
		this.librarian = librarian;
		this.signDate = signDate;
	}

}
