package com.ftninformatika.bisis.opac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(collection = "device_token")
public class DeviceToken implements Serializable {
    @Id
    String id;
    String deviceToken;
    String username;
    String library;
    Date lastAccessed;
    String platform;
}
