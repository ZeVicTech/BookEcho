package com.zerolab.bookecho.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@ConfigurationProperties(prefix = "zero")
public class AppConfig {

    private byte[] jwtkey;

    public void setJwtkey(String jwtkey) {
        this.jwtkey = Base64.getDecoder().decode(jwtkey);
    }
}
