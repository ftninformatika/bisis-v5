package com.ftninformatika.bisis.coders;

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
@Document("coders.inventory_unit_status")

public class InventoryUnitStatusCoder extends Coder {
    @Id
    private String _id;
}
