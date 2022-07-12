package com.hitpixel.paymentgwinvoice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CountryFilter implements Filter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CountryConfig countryConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String countryISO3 = httpServletRequest.getLocale().getISO3Country();
        if(countryConfig.isCountryAllowed(countryISO3)){
            log.debug("Country "+countryISO3+" allowed to access resource");
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            log.error("Country "+countryISO3+" not allowed to access resource, sending error response");
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("Error Code", 4006);
            errorDetails.put("Error Message", "Access not allowed on your country");
            httpServletResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(httpServletResponse.getWriter(), errorDetails);
        }
    }
}
