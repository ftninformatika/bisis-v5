package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.coders.Coder;

import java.util.List;

public interface InvCodersService {
    List<?> getInvLocationsByLib(String library);
}
