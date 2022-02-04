package ru.platform.learning.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.mainservice.entity.LogFile;


public interface LogFileRepository extends JpaRepository<LogFile, Long> {




}
