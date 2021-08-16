package com.ftninformatika.bisis.circ.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergeData {
    //userId of selected user
    String user;
    //userId to be used
    String userId;
    //list of users to merge
    List<String> userList;

}
