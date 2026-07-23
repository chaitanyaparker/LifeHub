package com.example.LifeHub.Service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailSender {

    void sendOtp(String to, String Subject, String message) throws MessagingException, UnsupportedEncodingException;
}
