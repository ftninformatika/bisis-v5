package com.ftninformatika.bisis.models.coders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="coders.status")
public class ItemStatus extends Coder {
    @Id private String _id;
    private boolean lendable;
    private boolean showable;

}
