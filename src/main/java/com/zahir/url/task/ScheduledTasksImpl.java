package com.zahir.url.task;

import com.zahir.url.repository.URLRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Slf4j
@PropertySource("classpath:app.properties")
public class ScheduledTasksImpl implements IScheduledTasks{

    @Autowired
    private URLRepository urlRepository;

    @Override
    @Scheduled(initialDelayString = "${initialDelay.in.milliseconds}",fixedDelayString = "${fixedDelay.in.milliseconds}")
    @Transactional
    public void runTasksWithFixedDelay() {
        log.info("Running Scheduled Task - runTasksWithFixedDelay...");
        urlRepository.deleteByExpirationDateLessThan(LocalDateTime.now());
    }
}
