package com.example.security.login.auth;

import com.example.security.service.AppUserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.security.login.auth.CustomAuthenticationFilter.getTOTPCode;
import static com.example.security.service.AppUserService.generateRandomString;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "https://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUtility authUtility;

    @Autowired
    public AuthController(AppUserService appUserService) {
        authUtility = new AuthUtility(appUserService);
    }



    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accessToken = authUtility.createJWTFromRequest(request);
            if (accessToken != null) {
                response.setContentType(APPLICATION_JSON_VALUE);
                AuthUtility.setResponseMessage(response, "accessToken", accessToken);
            }
            else {
                response.setStatus(UNAUTHORIZED.value());
                AuthUtility.setResponseMessage(response, "errorMessage", "Refresh token is missing");
            }
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            AuthUtility.setResponseMessage(response, "errorMessage", e.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie jwtCookie = new Cookie("refreshToken", "");
        jwtCookie.setMaxAge(0);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        AuthUtility.setResponseMessage(response, "Success", "Cookie removed");
    }

    @GetMapping("/submit-code/{code}")
    @CrossOrigin(origins = "https://localhost:4200/", allowCredentials = "true")
    public boolean validateCode(@PathVariable String code) {
        String secretKey = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";

        if (code.equals(getTOTPCode(secretKey))) {
            LOGGER.info("2FA: Successfully logged in");
            return true;
        } else {
            System.out.println("Invalid 2FA Code");
            LOGGER.warn("Invalid 2FA Code entered");
            return false;
        }
    }
}