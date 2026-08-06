package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.Service.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendOtp(String to, String subject, String message) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("chaitanyaparker08@gmail.com", "LifeHub");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, false);

        javaMailSender.send(mimeMessage);
    }
}
