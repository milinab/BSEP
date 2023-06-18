package com.example.security.service;

import com.example.security.email.EmailSender;
import com.example.security.enums.RegistrationStatus;
import com.example.security.model.AppUser;
import com.example.security.registration.EmailValidator;
import com.example.security.registration.RegistrationRequest;
import com.example.security.registration.token.ConfirmationToken;
import com.example.security.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final  KeyStoreService keyStoreService;
    public String register(RegistrationRequest request) throws Exception {
        boolean isValidEmail =  emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }

        AppUser existingUser = (AppUser) appUserService.loadUserByUsername(request.getEmail());

        if (existingUser == null) {
            throw new IllegalStateException("User not found");
        }

        existingUser.setRegistrationStatus(RegistrationStatus.PENDING);
        appUserService.saveUser(existingUser);

        String token = generateConfirmationToken(existingUser);
        String link = "http://localhost:8082/api/v1/registration/confirm?token=" + token;

        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link)
        );
        return token;
    }

    public String encryptData(String data, String alias, String username) throws Exception {
/*        SecretKey key = keyStoreService.generateKey();
        String encryptedData = keyStoreService.encrypt(data, key);
        keyStoreService.addKey(alias, username, key);
        return encryptedData;*/
        SecretKey secretKey = keyStoreService.getKey(alias, username);
        if (secretKey == null) {
            secretKey = keyStoreService.generateKey();
            keyStoreService.addKey(alias, username, secretKey);
        }
        return keyStoreService.encrypt(data, secretKey);
    }

    private String generateConfirmationToken(AppUser user) {
        String token = generateToken(); // Use a different method name for generating the token
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1800), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken.getToken();
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String pendingRegister(RegistrationRequest request) throws Exception {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        String alias = "a7gT9pK2eR5dL1jF";
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String encryptedLastName = encryptData(request.getLastName(), alias, request.getUsername());
        String encryptedFirstName = encryptData(request.getFirstName(), alias, request.getUsername());
        String encryptedEmail = encryptData(request.getEmail(), alias, request.getUsername());
        String encryptedAddress = encryptData(request.getAddress(), alias, request.getUsername());
        String encryptedPhone = encryptData(request.getPhone(), alias, request.getUsername());

        String token = appUserService.signUpUser(
                new AppUser(
                        encryptedFirstName,
                        encryptedLastName,
                        encryptedEmail,
                        request.getPassword(),
                        encryptedPhone,
                        encryptedAddress,
                        request.getUsername(),
                        request.getAppUserRole(),
                        RegistrationStatus.PENDING
                )
        );
        return token;

/*
        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getPhone(),
                        request.getAddress(),
                        request.getUsername(),
                        request.getAppUserRole(),
                        RegistrationStatus.PENDING
                )
        );
        return token;*/
    }

    @Transactional
    public void denyRegistration(Long userId, String denialReason) {
        AppUser user = appUserService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        user.setRegistrationStatus(RegistrationStatus.DENIED);
        appUserService.saveUser(user);

        String email = user.getEmail();
        String subject = "Registration Denied";
        String message = "Dear " + user.getFirstName() + ",\n\n" +
                "We regret to inform you that your registration request has been denied.\n" +
                "Reason: " + denialReason + "\n" +
                "If you have any questions, please feel free to contact us.\n\n" +
                "Best regards,\n" +
                "Your Application Team";

        emailSender.sendDeny(email, subject, message);
    }


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
     /*   appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());*/
        return "confirmed";
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
