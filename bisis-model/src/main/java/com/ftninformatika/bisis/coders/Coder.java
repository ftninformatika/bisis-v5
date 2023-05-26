package com.ftninformatika.bisis.coders;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import lombok.*;

/**
 * Created by dboberic on 26/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")

@JsonSubTypes({
        @JsonSubTypes.Type(value = Binding.class, name = "binding"),
        @JsonSubTypes.Type(value = AccessionRegister.class, name = "accessionRegister"),
        @JsonSubTypes.Type(value = Acquisition.class, name = "acquisitionCoder"),
        @JsonSubTypes.Type(value = Counter.class, name = "counter"),
        @JsonSubTypes.Type(value = Location.class, name ="location"),
        @JsonSubTypes.Type(value = Sublocation.class, name ="sublocation"),
        @JsonSubTypes.Type(value = Format.class, name ="format"),
        @JsonSubTypes.Type(value = ItemStatus.class, name ="itemStatus"),
        @JsonSubTypes.Type(value = InternalMark.class, name ="internalMark"),
        @JsonSubTypes.Type(value = Availability.class, name ="availability"),
        @JsonSubTypes.Type(value = Task.class, name ="task"),
        @JsonSubTypes.Type(value = CircLocation.class, name = "circLocation"),
        @JsonSubTypes.Type(value = MembershipType.class, name ="membershipType"),
        @JsonSubTypes.Type(value = UserCategory.class, name ="userCateg"),
        @JsonSubTypes.Type(value = Membership.class, name ="membership"),
        @JsonSubTypes.Type(value = EducationLvl.class, name ="educationLvl"),
        @JsonSubTypes.Type(value = Language.class, name ="language"),
        @JsonSubTypes.Type(value = Organization.class, name ="organization"),
        @JsonSubTypes.Type(value = Place.class, name ="place"),
        @JsonSubTypes.Type(value = WarningCounter.class, name ="warningCounter"),
        @JsonSubTypes.Type(value = InventoryStatus.class, name ="inventoryStatus"),

})

public class Coder {
    private String library;
    private String coder_id;
    private String description;
    private String type;
}
