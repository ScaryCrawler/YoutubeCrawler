package com.crawler.youtube_crawler.worker.activemq.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class ConsumerProps {
    @Value("${consumer.activemq.broker.url}")
    private String brokerUrl;

    @Value("${consumer.activemq.broker.max.connections}")
    private int maxConnections;

    @Value("${consumer.activemq.broker.max.sessions}")
    private int maxSessions;

    @Value("${consumer.consumer.concurrency}")
    private String listenerConcurrency;
}
