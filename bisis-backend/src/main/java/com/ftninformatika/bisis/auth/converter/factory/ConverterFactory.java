package com.ftninformatika.bisis.auth.converter.factory;

import com.ftninformatika.bisis.auth.converter.dto.UserDTOConverter;
import com.ftninformatika.bisis.auth.dto.UserDTO;
import com.ftninformatika.bisis.auth.model.LibrarianUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Component
public class ConverterFactory {

    private Map<Object, Converter<UserDTO, LibrarianUser>> converters;

    public ConverterFactory() {

    }

    @PostConstruct
    public void init() {
        converters = new HashMap<>();
        converters.put(UserDTO.class, new UserDTOConverter());
    }

    public Converter<UserDTO, LibrarianUser> getConverter(final Object type) {
        return converters.get(type);
    }
}
