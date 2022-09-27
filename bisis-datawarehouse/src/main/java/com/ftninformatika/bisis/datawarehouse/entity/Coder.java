package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Coder implements Persistable<String> {
    @Id
    private String id;
    private String description;
    private String library;

    @Override
    public boolean isNew() {
        return true;
    }
}
