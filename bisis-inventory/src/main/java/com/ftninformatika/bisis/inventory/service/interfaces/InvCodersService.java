package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.library_configuration.EnumLocationLevel;

import java.util.List;

public interface InvCodersService {
    List<?> getInvLocationsByLib(String library);
    EnumLocationLevel getEnumInvLocation(String library);
}
