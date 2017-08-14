package com.ftninformatika.bisis.circ.commands;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.models.circ.Member;

import java.io.IOException;

/**
 * Created by Petar on 8/11/2017.
 */
public class GetUserCommand {
    String userID;
    Member user = null;

    public GetUserCommand(String memberId){
        this.userID = userID;
    }

    public void setUserID(String memberId){
        this.userID = userID;
    }

    public Member getUser(){
        return user;
    }

    public void execute() {
        try {
            user = BisisApp.bisisService.getMemberById(userID).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
