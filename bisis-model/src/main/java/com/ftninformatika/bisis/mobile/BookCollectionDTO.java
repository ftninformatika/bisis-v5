package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.BookCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCollectionDTO {
    String _id;
    String title;
    boolean showable;

    public BookCollectionDTO(BookCollection bc) {
        this._id = bc.get_id();
        this.title = bc.getTitle();
        this.showable = bc.isShowCollection();
    }
}
