package com.ftninformatika.bisis.librarian;

/**
 * Created by Petar on 6/20/2017.
 */
public class Librarian {

    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private int obrada;
    private int cirkulacija;
    private int administracija;
    private String context;
    private String biblioteka;

    public Librarian(String username, String password, String ime, String prezime, String email, String napomena, int obrada, int cirkulacija, int administracija, String context, String biblioteka) {
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.napomena = napomena;
        this.obrada = obrada;
        this.cirkulacija = cirkulacija;
        this.administracija = administracija;
        this.context = context;
        this.biblioteka = biblioteka;
    }

    public Librarian() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getNapomena() {
        return napomena;
    }

    public int getObrada() {
        return obrada;
    }

    public int getCirkulacija() {
        return cirkulacija;
    }

    public int getAdministracija() {
        return administracija;
    }

    public String getContext() {
        return context;
    }

    public String getBiblioteka() {
        return biblioteka;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public void setObrada(int obrada) {
        this.obrada = obrada;
    }

    public void setCirkulacija(int cirkulacija) {
        this.cirkulacija = cirkulacija;
    }

    public void setAdministracija(int administracija) {
        this.administracija = administracija;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setBiblioteka(String biblioteka) {
        this.biblioteka = biblioteka;
    }
}
