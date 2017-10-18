package com.ftninformatika.bisis.models.circ.wrappers;

import com.ftninformatika.bisis.models.circ.Lending;
import com.ftninformatika.bisis.models.circ.Member;

import java.util.List;

public class MemberData {

    private Member member;
    private List<Lending> lendings;
    private List<Object> books;
    private Boolean isSaved;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }

    public Boolean isSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public List<Object> getBooks() {
        return books;
    }

    public void setBooks(List<Object> books) {
        this.books = books;
    }
}
