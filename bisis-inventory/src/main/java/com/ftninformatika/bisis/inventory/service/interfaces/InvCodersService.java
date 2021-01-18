package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.inventory.EnumInvLocation;

import java.util.List;

public interface InvCodersService {
    List<?> getInvLocationsByLib(String library);
    EnumInvLocation getEnumInvLocation(String library);
}
