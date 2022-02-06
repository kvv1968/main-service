package ru.platform.learning.mainservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class LogService {
    @Value("${spring.application.name}")
    private String nameApp;

    @Nullable
    public byte[] getArchivedDir(){
        try {
            File dir = new File("dev/logs/archived");
            File[]files = dir.listFiles();
            if (files == null || files.length == 0){
                log.error("Error Files log is null");
                return null;
            }
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ZipOutputStream zipOut= new ZipOutputStream(bo);
            for(File file:files){
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);
                zipOut.write(IOUtils.toByteArray(new FileInputStream(file)));
                zipOut.closeEntry();
            }
            zipOut.close();
            return bo.toByteArray();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Nullable
    public byte[] getCurrentLogFile(){
        try {
            File fileToZip = new File("dev/logs/" + nameApp + ".log");
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ZipOutputStream zipOut= new ZipOutputStream(bo);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            zipOut.write(IOUtils.toByteArray(new FileInputStream(fileToZip)));
            zipOut.closeEntry();
            zipOut.close();
            return bo.toByteArray();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
