package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.DeviceToken;
import com.ftninformatika.bisis.opac.Notification;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import com.ftninformatika.bisis.opac.repository.DeviceTokenRepository;
import com.ftninformatika.bisis.opac.repository.NotificationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("notifications")
@PropertySource(value = "classpath:notification.properties",encoding = "UTF-8")
public class NotificationController {
    @Autowired
    DeviceTokenRepository deviceTokenRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LibraryMemberRepository libraryMemberRepository;
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Value("${membership.title}")
    String membershipTitle;
    @Value("${membership.content}")
    String membershipContent;

    @PostMapping("send")
    public ResponseEntity<Notification> sendMessage(@RequestBody Notification notification)  {
        try{
            Message message = Message.builder()
                   .putData("title", notification.getTitle())
                   .putData("content", notification.getContent())
                   .putData("type", notification.getType())
                   .setTopic(libraryPrefixProvider.getLibPrefix())
                   .build();
            FirebaseMessaging.getInstance().send(message);
            Notification savedNotification =notificationRepository.save(notification);
            return new ResponseEntity<Notification>(savedNotification, HttpStatus.OK);
        }catch (FirebaseMessagingException exception){
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("all")
    public Page<Notification> getNotifications(@RequestHeader("Library") String lib,
                                               @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false) final Integer pageSize){
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        return this.notificationRepository.findAll(paging);
    }

    @Scheduled(cron="0 14 * * * * ")
    public void sendMembershipExpiredNotification() throws FirebaseMessagingException {
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime start = currentDate.atStartOfDay().plusDays(3);
        LocalDateTime end = currentDate.atStartOfDay().plusDays(4);
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileAppIsTrue();
        List<String> tokens = new ArrayList<String>();
        for(LibraryConfiguration lc:libraryConfigurations) {
            List<String> membersId = memberRepository.getExpiredMemebershipForOpacUsers(start, end, lc.getLibraryName());
            for (String id : membersId) {
                LibraryMember libraryMember = libraryMemberRepository.findByIndex(id);
                if (libraryMember != null) {
                    List<DeviceToken> deviceTokens = deviceTokenRepository.findDeviceTokenByLibraryAndUsername(lc.getLibraryName(), libraryMember.getUsername());
                    List<String> tokensMember = deviceTokens.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
                    tokens.addAll(tokensMember);
                }
            }
        }
        List<List<String>> sublists = ListUtils.partition(tokens, 500);
        for(List l:sublists){
            MulticastMessage message = MulticastMessage.builder()
                    .putData("title", membershipTitle)
                    .putData("content", membershipContent)
                    .putData("type","membership")
                    .addAllTokens(l)
                    .build();
            FirebaseMessaging.getInstance().sendMulticast(message);
        }
    }

}
