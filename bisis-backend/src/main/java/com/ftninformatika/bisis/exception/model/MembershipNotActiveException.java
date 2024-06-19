package com.ftninformatika.bisis.exception.model;


public class MembershipNotActiveException extends RuntimeException {

    public MembershipNotActiveException() {
        super("Корисник нема активну чланарину.");;
    }

}
