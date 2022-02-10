package ru.platform.learning.mainservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.platform.learning.mainservice.dto.CompilerResult;
import ru.platform.learning.mainservice.dto.CompilerTask;

@Service
@Slf4j
public class CompilerClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Value("${compiler.base-url}")
    private String rootUrl;


    public CompilerResult startCompiler(CompilerTask compilerTask) {
        HttpEntity<CompilerTask> httpEntity = new HttpEntity<>(compilerTask, httpHeaders);
        return restTemplate.postForObject(
                rootUrl + "/api/comp",
                httpEntity,
                CompilerResult.class
        );
    }

    public byte[] getCurrentLogFile() {
        return restTemplate.getForObject(
                rootUrl + "/api/comp/file.zip",
                byte[].class
        );
    }

    public byte[] getArchivedLogFiles() {
        return restTemplate.getForObject(
                rootUrl + "/api/comp/files.zip",
                byte[].class
        );
    }

}
