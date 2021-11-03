package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.core.repositories.LendingRepository;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.DeviceToken;
import com.ftninformatika.bisis.opac.Notification;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import com.ftninformatika.bisis.opac.repository.DeviceTokenRepository;
import com.ftninformatika.bisis.opac.repository.NotificationRepository;
import com.ftninformatika.bisis.opac.service.NotificationService;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
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
import java.util.HashSet;
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
    @Autowired
    LendingRepository lendingRepository;

    @Autowired
    NotificationService notificationService;

    @Value("${membership.title}")
    String membershipTitle;
    @Value("${membership.content}")
    String membershipContent;
    final String IOS="ios";
    final String ANDROID="android";

    @Value("${lending.title}")
    String lendingTitle;
    @Value("${lending.content}")
    String lendingContent;

    @PostMapping("send")
    public ResponseEntity<Notification> sendMessage(@RequestBody Notification notification)  {
        Message iosMessage = notificationService.getIOSMessage(notification, libraryPrefixProvider.getLibPrefix() + "-" + IOS);
        notificationService.sendSingleMessageToTopic(iosMessage);
        Message androidMessage = notificationService.getAndroidMessage(notification, libraryPrefixProvider.getLibPrefix() + "-" + ANDROID);
        notificationService.sendSingleMessageToTopic(androidMessage);
        Notification savedNotification =notificationRepository.save(notification);
        return new ResponseEntity<Notification>(savedNotification, HttpStatus.OK);
    }
    @GetMapping("all")
    public Page<Notification> getNotifications(@RequestHeader("Library") String lib,
                                               @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false) final Integer pageSize){
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        return this.notificationRepository.findAllByOrderBySentDateDesc(paging);
    }

    @Scheduled(cron="0 0 14 * * * ")
    public void sendMembershipExpiredNotification() throws FirebaseMessagingException {
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime start = currentDate.atStartOfDay().plusDays(3);
        LocalDateTime end = currentDate.atStartOfDay().plusDays(4);
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileAppIsTrue();
        List<String> tokensAndroid = new ArrayList<String>();
        List<String> tokensIOS = new ArrayList<String>();
        for(LibraryConfiguration lc:libraryConfigurations) {
            List<String> membersId = memberRepository.getExpiredMemebershipForOpacUsers(start, end, lc.getLibraryName());
            for (String id : membersId) {
                LibraryMember libraryMember = libraryMemberRepository.findByIndex(id);
                if (libraryMember != null) {
                    List<DeviceToken> deviceTokensAndroid = deviceTokenRepository.findDeviceTokenByLibraryAndUsernameAndPlatform(lc.getLibraryName(), libraryMember.getUsername(),ANDROID);
                    List<String> tokensMemberAndroid = deviceTokensAndroid.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
                    tokensAndroid.addAll(tokensMemberAndroid);

                    List<DeviceToken> deviceTokensIOS = deviceTokenRepository.findDeviceTokenByLibraryAndUsernameAndPlatform(lc.getLibraryName(), libraryMember.getUsername(),IOS);
                    List<String> tokensMemberIOS = deviceTokensIOS.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
                    tokensIOS.addAll(tokensMemberIOS);
                }
            }
        }
        List<List<String>> sublistsAndroid = ListUtils.partition(tokensAndroid, 500);
        for(List l:sublistsAndroid){
            MulticastMessage message = notificationService.getAndroidMulticastMessage(membershipTitle,membershipContent,"membership",l);
            notificationService.sendMessageToDeviceList(message);
        }
        List<List<String>> sublistsIOS = ListUtils.partition(tokensIOS, 500);
        for(List l:sublistsIOS){
            MulticastMessage message =notificationService.getIOSMulticastMessage(membershipTitle,membershipContent,"membership",l);
            notificationService.sendMessageToDeviceList(message);

        }
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
    //svaki dan u 15h se trigeruje
    @Scheduled(cron="0 0 15 * * * ")
    public void sendLendingExpiredNotification() throws FirebaseMessagingException {
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime start = currentDate.atStartOfDay().plusDays(3);
        LocalDateTime end = currentDate.atStartOfDay().plusDays(4);
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileAppIsTrue();
        List<String> tokensAndroid = new ArrayList<String>();
        List<String> tokensIOS = new ArrayList<String>();
        for(LibraryConfiguration lc:libraryConfigurations) {
          libraryPrefixProvider.setPrefix(lc.getLibraryName());
          List<Lending> overdueLendings = lendingRepository.findLendingsByDeadlineBetweenAndReturnDateIsNull(convertToDateViaInstant(start),convertToDateViaInstant(end));
          for (Lending  l:overdueLendings){
              String userId = l.getUserId();
              Member member = memberRepository.getMemberByUserId(userId);
              LibraryMember libraryMember = libraryMemberRepository.findByIndex(member.get_id());
              if (libraryMember != null) {
                  List<DeviceToken> deviceTokensAndroid = deviceTokenRepository.findDeviceTokenByLibraryAndUsernameAndPlatform(lc.getLibraryName(), libraryMember.getUsername(),ANDROID);
                  List<String> tokensMemberAndroid = deviceTokensAndroid.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
                  tokensAndroid.addAll(tokensMemberAndroid);

                  List<DeviceToken> deviceTokensIOS = deviceTokenRepository.findDeviceTokenByLibraryAndUsernameAndPlatform(lc.getLibraryName(), libraryMember.getUsername(),IOS);
                  List<String> tokensMemberIOS = deviceTokensIOS.stream().map(d -> d.getDeviceToken()).collect(Collectors.toList());
                  tokensAndroid.addAll(tokensMemberIOS);
              }
          }
            List<String>distinctTokensAndroid = new ArrayList<String>(new HashSet<>(tokensAndroid));
            List<List<String>> sublistsAndroid = ListUtils.partition(distinctTokensAndroid, 500);
            for(List sl:sublistsAndroid){
                MulticastMessage message = notificationService.getAndroidMulticastMessage(lendingTitle,lendingContent,"lending",sl);
                notificationService.sendMessageToDeviceList(message);

            }

            List<String>distinctTokensIOS = new ArrayList<String>(new HashSet<>(tokensIOS));
            List<List<String>> sublistsIOS = ListUtils.partition(distinctTokensIOS, 500);
            for(List sl:sublistsIOS){
                MulticastMessage message = notificationService.getIOSMulticastMessage(lendingTitle,lendingContent,"lending",sl);
                notificationService.sendMessageToDeviceList(message);
            }
        }
    }
}
