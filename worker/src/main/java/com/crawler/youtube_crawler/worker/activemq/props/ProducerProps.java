package com.crawler.youtube_crawler.worker.activemq.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class ProducerProps {
    @Value("${producer.activemq.broker.url}")
    private String brokerUrl;

    @Value("${producer.activemq.task.processing.limit}")
    private Long taskProcessingTimeLimit;
}
