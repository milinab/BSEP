package com.example.security.log;

import com.example.security.email.EmailSender;
import com.example.security.enums.AppUserRole;
import com.example.security.model.AppUser;
import com.example.security.service.AppUserService;
import com.example.security.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.websocket.Session;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LogScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LogScheduler.class);
    private static Session session;
    private final AppUserService appUserService;
    private final EmailSender emailSender;
    LocalDateTime startTime = LocalDateTime.now();
    private int linesProcessed = 0;

    private final WebSocketService webSocketService;

    @Autowired
    public LogScheduler(AppUserService appUserService, EmailSender emailSender, WebSocketService webSocketService) {
        this.appUserService = appUserService;
        this.emailSender = emailSender;
        this.webSocketService = webSocketService;
    }

    @Scheduled(fixedRate = 5000) // 5 seconds = 5,000 milliseconds
    public void readLogFile() {

        String logFilePath = "src/main/resources/logs/application.log";
        Path path = Paths.get(logFilePath);

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> newLines = lines.subList(linesProcessed, lines.size());

            for (String line : newLines) {
                if(line.length() > 22) {
                    if(logContainsDate(line)) {
                        String logDateTimeString = line.split("\\|")[0].trim();
                        LocalDateTime logTime = LocalDateTime.parse(logDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

                        if (logTime.isAfter(startTime)) {
                            if (line.contains("WARN")) {
                                triggerAlert(line);
                            }
                        }
                    }
                }
            }
            linesProcessed = lines.size();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean logContainsDate(String logLine) {
        String shortLogLine = logLine.substring(0, 23);
        return shortLogLine.matches(".*\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}.*");
    }

    private void triggerAlert(String logLine) {
        //System.out.println("Critical event detected: " + logLine);
        webSocketService.sendMessageToChannel("/socket-publisher", logLine);

        List<AppUser> appUser = appUserService.getUsersByUserRole(AppUserRole.ADMIN);
        AppUser admin = appUser.get(0);
        String email = admin.getEmail();
        String message = "POTENTIAL ISSUES DETECTED!\n\n" +
                "\n\n" +
                "Dear " + admin.getFirstName() + ",\n\n" +
                "\n\n" +
                "We would like to inform you about potential issues detected in the system.\n\n" +
                "\n\n" +
                "Our monitoring system has identified the following warning event: \n\n" +
                logLine + "\n\n";

        emailSender.send(email, message);

//        if (session != null && session.isOpen()) {
//            try {
//                session.getBasicRemote().sendText(logLine);
//            } catch (IOException e) {
//                e.printStackTrace();
//                // Handle the exception appropriately
//            }
//        }
    }

//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("WebSocket client connected: " + session.getId());
//        LogScheduler.session = session;
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        System.out.println("WebSocket client disconnected: " + session.getId());
//        LogScheduler.session = null;
//    }
//
//    public static void main(String[] args) {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        String uri = "ws://localhost:8080/push-notification"; // Specify the WebSocket server URI
//        try {
//            container.connectToServer(LogScheduler.class, URI.create(uri));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
