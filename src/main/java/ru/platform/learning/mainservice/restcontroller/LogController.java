package ru.platform.learning.mainservice.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.platform.learning.mainservice.client.CompilerClient;
import ru.platform.learning.mainservice.client.DatadaseHandlerClient;
import ru.platform.learning.mainservice.service.LogService;

import java.awt.*;

@Slf4j
@RestController
@RequestMapping("/admin")
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

    @GetMapping(value = "/file.zip")
    public ResponseEntity<?> getCurrentLog(){
        byte[]bytes = logService.getCurrentLogFile();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/files.zip")
    public ResponseEntity<?> getArchivedLog(){
        byte[]bytes = logService.getArchivedDir();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/comp/file.zip")
    public ResponseEntity<?> getCurrentLogToCompiler(){
        byte[]bytes = compilerClient.getCurrentLogFile();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/comp/files.zip")
    public ResponseEntity<?> getArchivedLogToCompiler(){
        byte[]bytes = compilerClient.getArchivedLogFiles();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/hand/file.zip")
    public ResponseEntity<?> getCurrentLogToHandler(){
        byte[]bytes = handlerClient.getCurrentLogFile();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/hand/files.zip")
    public ResponseEntity<?> getArchivedLogToHandler(){
        byte[]bytes = handlerClient.getArchivedLogFiles();
        if (bytes == null || bytes.length == 0){
            final String msg = "Error log file is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(bytes);
    }
}
