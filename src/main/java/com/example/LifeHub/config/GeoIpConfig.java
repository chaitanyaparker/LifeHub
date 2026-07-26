package com.example.LifeHub.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class GeoIpConfig {

    @Value("${geoip.database.path}")
    private String geoIpDatabasePath;

    @Bean
    public DatabaseReader geoIpDatabaseReader() throws IOException {
        File database = new File(geoIpDatabasePath);
        return new DatabaseReader.Builder(database).build();
    }
}