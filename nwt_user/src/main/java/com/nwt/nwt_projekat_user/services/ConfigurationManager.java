package com.nwt.nwt_projekat_user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ConfigurationManager {

    @Value("${spring.application.name}")
    private String springApplicationName;

    public String getSpringApplicationName() {
        return springApplicationName;
    }
}
