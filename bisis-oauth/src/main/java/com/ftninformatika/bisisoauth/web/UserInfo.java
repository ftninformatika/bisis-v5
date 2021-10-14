package com.ftninformatika.bisisoauth.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    String sub;
    String name;
    String given_name;
    String family_name;
    String age;
}
