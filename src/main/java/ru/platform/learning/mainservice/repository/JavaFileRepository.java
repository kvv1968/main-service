package ru.platform.learning.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.mainservice.entity.JavaFile;



public interface JavaFileRepository extends JpaRepository<JavaFile, Long> {
}
