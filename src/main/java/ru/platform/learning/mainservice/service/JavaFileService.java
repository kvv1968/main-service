package ru.platform.learning.mainservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.platform.learning.mainservice.entity.JavaFile;
import ru.platform.learning.mainservice.repository.JavaFileRepository;

@Service
@Slf4j
public class JavaFileService {
    @Autowired
    private JavaFileRepository repository;

    public JavaFile add(JavaFile jarFile){
        if(jarFile == null){
            final String msg = "Error JarFile is null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return repository.save(jarFile);
    }

    public JavaFile findById(Long id){
        if(id == null){
            final String msg = "Error id is null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id){
        if(id == null){
            final String msg = "Error id is null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
         repository.deleteById(id);
    }
}
