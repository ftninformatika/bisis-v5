package com.ftninformatika.bisis.datawarehouse.entity;

import com.ftninformatika.bisis.datawarehouse.model.SearchRequest;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The persistent class for the acquisition database table.
 * 
 */

@TypeDefs({
		@TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="saved_query", schema="bisis_reports", catalog="bisis")

public class SavedQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SAVED_QUERY_ID_GENERATOR", sequenceName="bisis_reports.saved_query_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAVED_QUERY_ID_GENERATOR")
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="date")
	private LocalDateTime date;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "content")
	private SearchRequest content;

	private String library;

}
