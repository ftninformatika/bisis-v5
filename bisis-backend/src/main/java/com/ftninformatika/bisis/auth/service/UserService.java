package com.ftninformatika.bisis.auth.service;

import com.ftninformatika.bisis.auth.model.LibrarianUser;

import java.util.List;


public interface UserService {

    LibrarianUser create(LibrarianUser object);

    LibrarianUser find(String id);

    LibrarianUser findByUsername(String userName);

    List<LibrarianUser> findAll();

    LibrarianUser update(String id, LibrarianUser object);

    String delete(String id);
}
