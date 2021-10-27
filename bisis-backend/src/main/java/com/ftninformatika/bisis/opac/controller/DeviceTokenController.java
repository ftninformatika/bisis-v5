package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.DeviceToken;
import com.ftninformatika.bisis.opac.repository.DeviceTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("deviceToken")
public class DeviceTokenController {
@Autowired
    DeviceTokenRepository deviceTokenRepository;

    @PostMapping("save")
    public void saveOrUpdateTokenDevice(@RequestBody DeviceToken deviceTokenDTO) throws FirebaseMessagingException {
        String username = deviceTokenDTO.getUsername();
        String token = deviceTokenDTO.getDeviceToken();
        String library = deviceTokenDTO.getLibrary();
        String platform = deviceTokenDTO.getPlatform();
        DeviceToken deviceTokenOld = deviceTokenRepository.findByDeviceToken(token);
        List subscribeTokens = Collections.singletonList(deviceTokenDTO.getDeviceToken());
        if (deviceTokenOld == null){
            deviceTokenDTO.setLastAccessed(new Date());
            deviceTokenRepository.save(deviceTokenDTO);
            FirebaseMessaging.getInstance().subscribeToTopic(subscribeTokens, library+"-"+platform);
        }else{
            if (!library.equalsIgnoreCase(deviceTokenOld.getLibrary())){
                FirebaseMessaging.getInstance().unsubscribeFromTopic(subscribeTokens, deviceTokenOld.getLibrary()+"-"+platform);
            }
            deviceTokenOld.setUsername(username);
            deviceTokenOld.setLibrary(library);
            deviceTokenOld.setPlatform(platform);
            deviceTokenOld.setLastAccessed(new Date());
            deviceTokenRepository.save(deviceTokenOld);
            FirebaseMessaging.getInstance().subscribeToTopic(subscribeTokens, library+"-"+platform);
        }
    }
}
