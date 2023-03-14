package ru.meklaw.autocontroller;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class AutoControllerApplication {

    @Value("${client.zoneId}")
    private String zoneId;

    public static void main(String[] args) {
        SpringApplication.run(AutoControllerApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ZoneId localZoneId() {
        return ZoneId.of(zoneId);
    }

}
