package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the content_type database table.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="content_type", schema = "bisis_reports", catalog = "bisis")
public class ContentType extends Coder implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToMany(mappedBy = "contentTypes")
    Set<Item> items;
}
