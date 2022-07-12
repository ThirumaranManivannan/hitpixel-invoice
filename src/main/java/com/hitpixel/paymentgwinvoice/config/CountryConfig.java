package com.hitpixel.paymentgwinvoice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "country")
@Slf4j
public class CountryConfig {
    private List<String> allowed;

    public Boolean isCountryAllowed(String iso3Code){
        return allowed.contains(iso3Code);
    }
}
