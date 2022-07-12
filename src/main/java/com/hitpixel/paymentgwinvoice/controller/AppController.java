package com.hitpixel.paymentgwinvoice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo(HttpServletRequest servletRequest){
        RequestContext requestContext = new RequestContext(servletRequest);
        Locale locale = requestContext.getLocale();
        String country = locale.getISO3Country();
        Map<String, String> appInfo = new HashMap<>();
        if(country.equals("ARE")) {
            appInfo.put("App Name", "Payment Gateway Invoice App");
            appInfo.put("Version", "1.0.0");
            appInfo.put("Status", "Running");

        }else{
            appInfo.put("Message", "Service Not accessible outside UAE");
        }
        return new ResponseEntity<>(appInfo, HttpStatus.OK);
    }
}
