package com.ftninformatika.bisis.models.circ;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Petar on 10/18/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {

    private String newPassword;
    private String passwordResetString;
}
