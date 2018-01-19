package com.crawler.youtube_crawler.worker.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleJobSender implements JobSender {

    @Setter(onMethod = @__(@Autowired))
    private JmsTemplate jmsTemplate;

    @Override
    public void sendJob(final JobDto jobDto) {
        try {
            jmsTemplate.convertAndSend(new ObjectMapper().writeValueAsString(jobDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
