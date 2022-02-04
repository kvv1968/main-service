package ru.platform.learning.mainservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.platform.learning.mainservice.entity.LogFile;
import ru.platform.learning.mainservice.repository.LogFileRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@EnableScheduling
public class LogFileService {
    @Autowired
    private LogFileRepository fileRepository;

    @Scheduled(cron = "0 0 2 * * ?")// раз в сутки запуск 02:00
    public void loadDataBaseLogFile(){
        try {
            log.info("Start loadDataBaseLogFile");
            List<LogFile> logFiles = getLogFiles();
            if (logFiles.isEmpty()){
                final String msg = "Error LogFiles is empty";
                log.error(msg);
                throw new IOException(msg);
            }
            fileRepository.saveAll(logFiles);

        } catch (Exception ex){
            log.error(ex.getMessage());
            log.error("Stop loadDataBaseLogFile");
        }


    }

    private List<LogFile> getLogFiles() throws IOException {
        File dir = new File("logs/archived");
        File[]files = dir.listFiles();
        if (files == null || files.length == 0){
            log.error("Error Files log is null");
            return Collections.emptyList();
        }
        List<LogFile> logFiles = new ArrayList<>();
        for(File file:files){
            String nameFile = file.getName();
            byte[]bytes = IOUtils.toByteArray(new FileInputStream(file));
            Date expiredDate = createDate();
            LogFile logFile = new LogFile(nameFile, bytes, expiredDate);
            logFiles.add(logFile);
        }
        return logFiles;
    }

    private Date createDate() {
        long current = new Date().getTime();
        return new Date(current + 86400000L);
    }


}
