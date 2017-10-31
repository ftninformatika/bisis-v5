package com.ftninformatika.bisis.auth.converter;

import com.ftninformatika.bisis.auth.converter.factory.ConverterFactory;
import com.ftninformatika.bisis.auth.dto.UserDTO;
import com.ftninformatika.bisis.auth.model.LibrarianUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ConverterFacade {

    @Autowired
    private ConverterFactory converterFactory;

    public LibrarianUser convert(final UserDTO dto) {
        return converterFactory.getConverter(dto.getClass()).convert(dto);
    }
}
