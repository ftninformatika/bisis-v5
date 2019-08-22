package com.ftninformatika.bisis.opac2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  22.8.19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToShelfDto {
    private String email;
    private String bookId;
}
