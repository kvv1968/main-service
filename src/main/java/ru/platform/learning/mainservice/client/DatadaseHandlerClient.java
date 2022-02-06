package ru.platform.learning.mainservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.platform.learning.mainservice.dto.CompilerResult;
import ru.platform.learning.mainservice.dto.CompilerTask;

@Service
@Slf4j
public class DatadaseHandlerClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Value("${handler.base-url}")
    private String rootUrl;


    public byte[] getCurrentLogFile() {
        return restTemplate.getForObject(
                rootUrl + "/api/hand/file.zip",
                byte[].class
        );
    }

    public byte[] getArchivedLogFiles() {
        return restTemplate.getForObject(
                rootUrl + "/api/hand/files.zip",
                byte[].class
        );
    }
}
