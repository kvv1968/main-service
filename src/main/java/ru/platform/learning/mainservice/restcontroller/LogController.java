package ru.platform.learning.mainservice.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.learning.mainservice.client.CompilerClient;
import ru.platform.learning.mainservice.client.DatadaseHandlerClient;
import ru.platform.learning.mainservice.service.LogService;

@Slf4j
@RestController("/admin")
public class LogController {

    private final LogService logService;
    private final CompilerClient compilerClient;
    private final DatadaseHandlerClient handlerClient;

    @Autowired
    public LogController(LogService logService, CompilerClient compilerClient, DatadaseHandlerClient handlerClient) {
        this.logService = logService;
        this.compilerClient = compilerClient;
        this.handlerClient = handlerClient;
    }

    @GetMapping("/file.zip")
    public byte[] getCurrentLog(){
        return logService.getCurrentLogFile();
    }

    @GetMapping("/files.zip")
    public byte[] getArchivedLog(){
        return logService.getArchivedDir();
    }

    @GetMapping("/comp/file.zip")
    public byte[] getCurrentLogToCompiler(){
        return compilerClient.getCurrentLogFile();
    }

    @GetMapping("/comp/files.zip")
    public byte[] getArchivedLogToCompiler(){
        return compilerClient.getArchivedLogFiles();
    }

    @GetMapping("/hand/file.zip")
    public byte[] getCurrentLogToHandler(){
        return handlerClient.getCurrentLogFile();
    }

    @GetMapping("/hand/files.zip")
    public byte[] getArchivedLogToHandler(){
        return handlerClient.getArchivedLogFiles();
    }
}
