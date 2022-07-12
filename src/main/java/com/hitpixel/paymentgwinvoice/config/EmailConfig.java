package com.hitpixel.paymentgwinvoice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "email")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfig {
    private String username;
    private String password;
    private Boolean enabled;
}
