package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.DeviceToken;
import com.ftninformatika.bisis.opac.repository.DeviceTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("deviceToken")
public class DeviceTokenController {
    @Autowired
    DeviceTokenRepository deviceTokenRepository;
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @PostMapping("save")
    public void saveOrUpdateTokenDevice(@RequestBody DeviceToken deviceTokenDTO) throws FirebaseMessagingException {
        String username = deviceTokenDTO.getUsername();
        String token = deviceTokenDTO.getDeviceToken();
        String library = deviceTokenDTO.getLibrary();
        String platform = deviceTokenDTO.getPlatform();
        if (platform != null && library != null && token != null) {
            List<String> tokens = Collections.singletonList(token);
            unsubscribeFromTopics(tokens);

            DeviceToken deviceTokenOld = deviceTokenRepository.findByDeviceToken(token);
            if (deviceTokenOld == null) {
                if (username != null && !username.isEmpty()) {
                    deviceTokenDTO.setLastAccessed(new Date());
                    deviceTokenRepository.save(deviceTokenDTO);
                }
                FirebaseMessaging.getInstance().subscribeToTopic(tokens, library + "-" + platform);
            } else {
                if (username == null || username.isEmpty()) {
                    deviceTokenRepository.delete(deviceTokenOld);
                } else {
                    deviceTokenOld.setUsername(username);
                    deviceTokenOld.setLibrary(library);
                    deviceTokenOld.setPlatform(platform);
                    deviceTokenOld.setLastAccessed(new Date());
                    deviceTokenRepository.save(deviceTokenOld);
                }
                FirebaseMessaging.getInstance().subscribeToTopic(tokens, library + "-" + platform);
            }
        }
    }

    private void unsubscribeFromTopics(List<String> tokens) throws FirebaseMessagingException {
        Sort sort = new Sort(Sort.Direction.ASC, "mobileOrderNo");
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileOrderNo(sort);
        List<String> libraries = libraryConfigurations.stream().map(l -> l.getLibraryName()).collect(Collectors.toList());
        for (String library: libraries) {
            TopicManagementResponse responseAndroid = FirebaseMessaging.getInstance().unsubscribeFromTopic(tokens, library + "-android");
            TopicManagementResponse responseIos = FirebaseMessaging.getInstance().unsubscribeFromTopic(tokens, library + "-ios");
        }
    }

//    @GetMapping("unsubscribe")
//    public void unsubscribe() throws FirebaseMessagingException {
//        List<DeviceToken> deviceTokens = deviceTokenRepository.findAll();
//        List<String> tokens = deviceTokens.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
//        tokens.removeAll(Arrays.asList("", null));
//        unsubscribeFromTopics(tokens);
//    }
//
//    @GetMapping("subscribe")
//    public void subscribe() throws FirebaseMessagingException {
//        List<DeviceToken> deviceTokens = deviceTokenRepository.findAll();
//        for (DeviceToken deviceToken: deviceTokens) {
//            List subscribeTokens = Collections.singletonList(deviceToken.getDeviceToken());
//            TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(subscribeTokens, deviceToken.getLibrary() + "-" + deviceToken.getPlatform());
//        }
//    }
}
