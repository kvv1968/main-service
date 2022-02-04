package ru.platform.learning.mainservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.platform.learning.mainservice.model.TaskData;

@Service
@Slf4j
public class UserTaskClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Value("${compiler.base-url}")
    private String rootUrl;


    public Long startCompiler(TaskData taskData) {
        HttpEntity<?> httpEntity = new HttpEntity<>(taskData, httpHeaders);
        return restTemplate.postForObject(
                rootUrl + "/api/comp",
                httpEntity,
                Long.class
        );
    }
}
