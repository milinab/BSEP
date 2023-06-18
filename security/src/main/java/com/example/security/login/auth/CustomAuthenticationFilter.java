package com.example.security.login.auth;

import com.auth0.jwt.JWT;
import com.example.security.model.AppUser;
import com.example.security.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.security.service.AppUserService.generateRandomString;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        String secretKey = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
        String companyName = "Company";
        String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, username, companyName);
            createQRCode(barCodeUrl, "QRCode.png", 400, 400);
        if (!appUserService.isBlocked(username)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        } else {
            throw new AppUserIsBlockedException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
            throws IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))    // 15 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities().stream().
                        map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(AuthUtility.getAlgorithm());
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))    // 14 days
                .withIssuer(request.getRequestURL().toString())
                .sign(AuthUtility.getAlgorithm());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        Cookie jwtCookie = new Cookie("refreshToken", refreshToken);
        jwtCookie.setMaxAge(14 * 24 * 60 * 60); // 14 days
        jwtCookie.setSecure(true);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        Map<String, String> error = new HashMap<>();
        if (failed instanceof DisabledException) {
            error.put("errorMessage", "Email address not confirmed");
        } else if (failed instanceof AppUserIsBlockedException) {
            error.put("errorMessage", "User is blocked");
        }
        else {
            error.put("errorMessage", "Incorrect email or password");
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        LOGGER.info("TOTP code generated");
        return TOTP.getOTP(hexKey);
    }

    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
    public static void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }
}