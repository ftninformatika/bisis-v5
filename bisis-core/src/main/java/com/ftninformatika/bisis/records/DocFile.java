package com.ftninformatika.bisis.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Petar on 1/24/2018.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{libraryPrefixProvider.getLibPrefix()}_record_files")
public class DocFile {
    private String fileName;
    private String uploader;
    private String fileId;
    private String rn;
    private String contentType;
    private String library;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'")
    private Date uploadDate;
}
