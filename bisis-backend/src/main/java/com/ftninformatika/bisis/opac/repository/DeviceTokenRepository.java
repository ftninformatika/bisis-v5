package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.DeviceToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTokenRepository extends MongoRepository<DeviceToken, String> {
    DeviceToken findByDeviceToken(String token);
    List<DeviceToken> findDeviceTokenByLibraryAndUsernameExists(String library);
    List<DeviceToken> findDeviceTokenByLibraryAndUsername(String library, String username);
}

