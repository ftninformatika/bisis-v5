package com.ftninformatika.bisis.opac2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  16.9.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    private String username;
    private String newPassword;
}
