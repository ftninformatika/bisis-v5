package com.ftninformatika.bisis.opac.service;

import com.ftninformatika.bisis.opac.DeviceToken;
import com.ftninformatika.bisis.opac.Notification;
import com.ftninformatika.bisis.opac.repository.DeviceTokenRepository;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    DeviceTokenRepository deviceTokenRepository;

    public Message getIOSMessage(Notification notification, String topic){
        Message message = Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(notification.getTitle())
                        .setBody(notification.getContent())
                        .build())
                .putData("title", notification.getTitle())
                .putData("content", notification.getContent())
                .putData("type", notification.getType())
                .setTopic(topic)
                .build();
        return message;
    }
    public Message getAndroidMessage(Notification notification, String topic){
        Message message = Message.builder()
                .putData("title", notification.getTitle())
                .putData("content", notification.getContent())
                .putData("type", notification.getType())
                .setTopic(topic)
                .build();
        return message;
    }
    public MulticastMessage getAndroidMulticastMessage(String title, String content, String type, List tokens){
        MulticastMessage message = MulticastMessage.builder()
                .putData("title", title)
                .putData("content", content)
                .putData("type",type)
                .addAllTokens(tokens)
                .build();
        return message;
    }
    public MulticastMessage getIOSMulticastMessage(String title,String content,String type,List tokens){

        final Aps aps =
                Aps.builder()
                        .setContentAvailable(true)
                        .build();
        final ApnsConfig apnsConfig =
                ApnsConfig.builder()
                        .setAps(aps)
                        .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(content)
                        .build())
                .putData("title", title)
                .putData("content", content)
                .putData("type",type)
                .addAllTokens(tokens)
                .setApnsConfig(apnsConfig)
                .build();
        return message;
    }

    public void sendSingleMessageToTopic(Message message){
        try{
            FirebaseMessaging.getInstance().send(message);
        }catch(Exception e){
                //TODO:handle exception
        }
    }
    public void sendMessageToDeviceList(MulticastMessage message){
        try{
            FirebaseMessaging.getInstance().sendMulticast(message);
        }catch(Exception e){
            //TODO:handle exception
        }
    }

    public void sendMessageToUsername(String username,String title,String content,String type){
        List<DeviceToken>  deviceTokensAndroid = deviceTokenRepository.findDeviceTokenByUsernameAndPlatform(username,"android");
        List<String> tokensAndroid = deviceTokensAndroid.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
        if (!tokensAndroid.isEmpty()){
            MulticastMessage message = getAndroidMulticastMessage(title,content,type,tokensAndroid);
            sendMessageToDeviceList(message);
        }
        List<DeviceToken>  deviceTokensIOS = deviceTokenRepository.findDeviceTokenByUsernameAndPlatform(username,"ios");
        List<String> tokensIOS = deviceTokensIOS.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
        if (!tokensIOS.isEmpty()){
            MulticastMessage message = getIOSMulticastMessage(title,content,type,tokensIOS);
            sendMessageToDeviceList(message);
        }
    }
}
