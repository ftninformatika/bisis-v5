package com.ftninformatika.bisis.auth.service;

import com.ftninformatika.bisis.auth.model.LibrarianUser;
import com.ftninformatika.bisis.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BasicUserService implements UserService {

    private final UserRepository repository;

    @Autowired
    public BasicUserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public LibrarianUser create(final LibrarianUser librarianUser) {
        librarianUser.setCreatedAt(String.valueOf(LocalDateTime.now()));
        return repository.save(librarianUser);
    }

    @Override
    public LibrarianUser find(final String id) {
        return repository.findById(id).get();
    }

    @Override
    public LibrarianUser findByUsername(final String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public List<LibrarianUser> findAll() {
        return repository.findAll();
    }

    @Override
    public LibrarianUser update(final String id, final LibrarianUser librarianUser) {
        librarianUser.setId(id);

        final LibrarianUser saved = repository.findById(id).get();

        if (saved != null) {
            librarianUser.setCreatedAt(saved.getCreatedAt());
            librarianUser.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        } else {
            librarianUser.setCreatedAt(String.valueOf(LocalDateTime.now()));
        }
        repository.save(librarianUser);
        return librarianUser;
    }

    @Override
    public String delete(final String id) {
        repository.deleteById(id);
        return id;
    }

}
