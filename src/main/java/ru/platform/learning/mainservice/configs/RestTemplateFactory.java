package ru.platform.learning.mainservice.configs;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


@Component
public class RestTemplateFactory {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .basicAuthentication("ADMIN", "c33455d0-d50a-3e8f-a153-b7b05312826d")
                .build();
    }
    @Bean
    HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String auth = "ADMIN:c33455d0-d50a-3e8f-a153-b7b05312826d";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
            add("Content-Type", "application/json");
        }};
    }

}
