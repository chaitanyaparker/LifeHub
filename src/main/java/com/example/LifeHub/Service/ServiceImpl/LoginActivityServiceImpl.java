package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Response.LoginActivityResponseDTO;
import com.example.LifeHub.Entity.LoginActivity;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.LoginMethod;
import com.example.LifeHub.Enums.LoginStatus;
import com.example.LifeHub.Repository.LoginActivityRepository;
import com.example.LifeHub.Service.LoginActivityService;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ua_parser.Client;
import ua_parser.Parser;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginActivityServiceImpl implements LoginActivityService {

    private final LoginActivityRepository loginActivityRepository;
    private final DatabaseReader geoIpDatabaseReader;
    private final Parser uaParser = new Parser();

    @Override
    @Async
    public void saveLoginActivity(User user,
                                  LoginMethod loginMethod,
                                  LoginStatus loginStatus,
                                  HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        Client client = uaParser.parse(userAgent);

        String device = (client.device != null) ? client.device.family : "Unknown";
        String browser = (client.userAgent != null)
                ? client.userAgent.family + " " + nullSafe(client.userAgent.major)
                : "Unknown";
        String os = (client.os != null)
                ? client.os.family + " " + nullSafe(client.os.major)
                : "Unknown";

        String ipAddress = extractClientIp(request);

        LoginActivity loginActivity = LoginActivity.builder()
                .user(user)
                .loginAt(Instant.now())
                .ipAddress(ipAddress)
                .device(device)
                .browser(browser)
                .os(os)
                .location(resolveLocation(ipAddress))
                .loginMethod(loginMethod)
                .loginStatus(loginStatus)
                .build();

        loginActivityRepository.save(loginActivity);
    }

    @Override
    public List<LoginActivityResponseDTO> getLoginHistory(User user) {

        ZoneId zoneId = safeZoneId(user.getTimeZone());

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("MMMM dd yyyy  hh:mm a")
                .withZone(zoneId);

        return loginActivityRepository
                .findByUserOrderByLoginAtDesc(user)
                .stream()
                .map(activity -> LoginActivityResponseDTO.builder()
                        .loginId(activity.getLoginId())
                        .loginDateTime(formatter.format(activity.getLoginAt()))
                        .deviceInfo(activity.getOs() + " " + activity.getBrowser())
                        .currentSession(false)
                        .build())
                .toList();
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    private String resolveLocation(String ipAddress) {
        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            CityResponse response = geoIpDatabaseReader.city(ip);
            String city = response.getCity().getName();
            String country = response.getCountry().getName();

            if (city == null && country == null) {
                return "Unknown";
            }
            return (city != null ? city : "Unknown") + ", " + (country != null ? country : "Unknown");
        } catch (Exception e) {
            log.warn("Could not resolve location for IP {}: {}", ipAddress, e.getMessage());
            return "Unknown";
        }
    }

    private ZoneId safeZoneId(String timeZone) {
        try {
            return ZoneId.of(timeZone);
        } catch (Exception e) {
            log.warn("Invalid timezone '{}' for user, defaulting to UTC", timeZone);
            return ZoneId.of("UTC");
        }
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }
}