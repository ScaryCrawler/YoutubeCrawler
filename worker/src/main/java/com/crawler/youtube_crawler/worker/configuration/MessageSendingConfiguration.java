package com.crawler.youtube_crawler.worker.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import java.util.Collections;

@Configuration
public class MessageSendingConfiguration {
    @Value("${activemq.broker.url}")
    private String brokerUrl;

    private final static String QUEUE_NAME = "job_queue";

    @Value("${activemq.task.processing.limit}")
    private Long taskProcessingTimeLimit;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setTrustedPackages(Collections.singletonList("com.websystique.spring"));
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        return connectionFactory;
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        final RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setInitialRedeliveryDelay(taskProcessingTimeLimit);
        return policy;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(QUEUE_NAME);
        return template;
    }

}
