package com.kamenskiy.io.emailnotification.persistence.repository;

import com.kamenskiy.io.emailnotification.persistence.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity,Long> {
    ProcessedEventEntity findByMessageId(String messageId);
}
