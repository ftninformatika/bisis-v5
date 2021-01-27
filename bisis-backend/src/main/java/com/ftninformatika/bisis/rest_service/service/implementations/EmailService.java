package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.Texts;
import com.ftninformatika.bisis.rest_service.config.YAMLConfig;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.StringUtils;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author badf00d21  24.7.19.
 */
@Service
@PropertySource(value = "classpath:opac-secret.properties")
public class EmailService {

    private Configuration fmConfig;
    private YAMLConfig yamlConfig;
    private Logger log = Logger.getLogger(EmailService.class);
    private static final String ACTIVATE_ACC_URL_CHUNK = "user/activate-account/";
    private static final String LIB_URL_CHUNK = "lib/";

    @Value("${opac.email.username}")
    private String opacUsername;
    @Value("${opac.email.password}")
    private String opacPassword;

    @Autowired
    public EmailService(Configuration fmConfig, YAMLConfig yamlConfig) {
        this.fmConfig = fmConfig;
        this.yamlConfig = yamlConfig;
    }

    @Bean("gmail")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername(opacUsername);
        javaMailSender.setPassword(opacPassword);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");


        return javaMailSender;
    }

    public void sendSimpleMail(String addressTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(addressTo);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender().send(message);
    }

    public void sendOpacWelcomeTemplate(LibraryMember libraryMember, LibraryConfiguration libraryConfiguration) {
        MimeMessage message = javaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        if (libraryConfiguration == null || yamlConfig.getOpacOrigin() == null || libraryMember == null || libraryMember.getActivationToken() == null) {
            System.out.println("Activation email not sent: libraryConfiguration == null || opacUrl == null || libraryMember == null || libraryMember.getActivationToken() == null");
            log.error("Activation email not sent: libraryConfiguration == null || opacUrl == null || libraryMember == null || libraryMember.getActivationToken() == null");
            return;
        }

        String opacUrl = yamlConfig.getOpacOrigin() + LIB_URL_CHUNK + libraryConfiguration.getLibraryName();
        String activateAccountUrl = yamlConfig.getOpacOrigin() + ACTIVATE_ACC_URL_CHUNK;

        fmConfig.setClassForTemplateLoading(this.getClass(), "/");
        try {

            Map<String, Object> root = new HashMap<>();
            String body1 = MessageFormat.format(Texts.getString("OPAC.WELCOME.MAIL.BODY.1"), LatCyrUtils.toCyrillic(libraryConfiguration.getLibraryName()).toUpperCase());
            root.put("body1", StringUtils.convertToHtmlUtf8(body1));
            root.put("body2", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.BODY.2")));
            root.put("body3", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.BODY.3")));
            root.put("catalogue", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.CATALOGUE")));
            root.put("dear", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.DEAR")));
            root.put("here", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.HERE")));
            root.put("webaddr", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.WEBADDR")));
            root.put("locationAddress", StringUtils.convertToHtmlUtf8(libraryConfiguration.getLocationAddress() != null ? libraryConfiguration.getLocationAddress() : ""));
            root.put("locationCity", StringUtils.convertToHtmlUtf8(libraryConfiguration.getLocationCity() != null ? libraryConfiguration.getLocationCity() : ""));
            root.put("locationZip", StringUtils.convertToHtmlUtf8(libraryConfiguration.getLocationZip() != null ? libraryConfiguration.getLocationZip() : ""));
            root.put("websiteUrl", libraryConfiguration.getWebsiteUrl() != null ? libraryConfiguration.getWebsiteUrl() : "");
            root.put("activationLink", activateAccountUrl + libraryMember.getActivationToken());
            root.put("opacLibAddr", opacUrl);
            root.put("libraryFullName", StringUtils.convertToHtmlUtf8(libraryConfiguration.getLibraryFullName()));

            Template t = fmConfig.getTemplate("opac-welcome-template.ftl");
            libraryConfiguration.setLibraryFullName(StringUtils.convertToHtmlUtf8(libraryConfiguration.getLibraryFullName()));
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, root);
            helper.setTo(libraryMember.getUsername());
            helper.setText(text, true);
            helper.setSubject(Texts.getString("OPAC.WELCOME.MAIL.SUBJECT"));
            javaMailSender().send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


    }

    public void sendReservationConfirmation(String sendTo, String bookTitle, String deadline, LibraryConfiguration libConf) {
        MimeMessage message = javaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String websiteUrl = "https://opac.bisis.rs";

        if (libConf != null && yamlConfig.getOpacOrigin() != null) {
            websiteUrl = yamlConfig.getOpacOrigin() + LIB_URL_CHUNK + libConf.getLibraryName();
        }

        fmConfig.setClassForTemplateLoading(this.getClass(), "/");
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("dear", StringUtils.convertToHtmlUtf8(Texts.getString("OPAC.WELCOME.MAIL.DEAR")));

            String body1 = MessageFormat.format(Texts.getString("RESERVATION_CONFIRMED_BODY.1"),
                    "\"" + LatCyrUtils.toCyrillic(bookTitle) + "\"");
            root.put("body1", StringUtils.convertToHtmlUtf8(body1));
            root.put("body2", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_CONFIRMED_BODY.2")));

            String body3 = MessageFormat.format(Texts.getString("RESERVATION_CONFIRMED_BODY.3"), LatCyrUtils.toCyrillic(deadline));
            root.put("body3", StringUtils.convertToHtmlUtf8(body3));
            root.put("body4", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_CONFIRMED_BODY.4")));
            root.put("opac", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_OPAC_LINK_TITLE")));
            root.put("websiteUrl", websiteUrl);
            root.put("body5", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_CONFIRMED_BODY.5")));
            root.put("body6", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_CONFIRMED_BODY.6")));
            root.put("body7", StringUtils.convertToHtmlUtf8(Texts.getString("RESERVATION_CONFIRMED_BODY.7")));

            Template t = fmConfig.getTemplate("opac-reservation-template.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, root);
            helper.setTo(sendTo);
            helper.setText(text, true);
            helper.setSubject(Texts.getString("RESERVATION_CONFIRMED_HEADING"));
            javaMailSender().send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            log.error("(sendReservationConfirmation) email nije poslat clanu: " + sendTo + ", za knjigu: " + bookTitle);
            e.printStackTrace();
        }
    }
}