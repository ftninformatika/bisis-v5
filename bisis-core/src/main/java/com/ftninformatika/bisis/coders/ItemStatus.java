package com.ftninformatika.bisis.coders;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="coders.status")
public class ItemStatus {
    @Id private String _id;
    private String status;
    private String description;
    private boolean lendable;
    private boolean showable;
}
