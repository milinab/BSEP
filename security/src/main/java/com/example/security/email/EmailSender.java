package com.example.security.email;

public interface EmailSender {
    void send(String to, String email);
    void sendDeny(String email, String subject, String message);

}
