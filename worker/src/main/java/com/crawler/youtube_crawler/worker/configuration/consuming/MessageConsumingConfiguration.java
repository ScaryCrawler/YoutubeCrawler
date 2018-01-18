package com.crawler.youtube_crawler.worker.configuration.consuming;

import com.crawler.youtube_crawler.core.dto.JobDto;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.messaging.MessageChannel;

import javax.jms.Queue;
import javax.jms.Session;

@Configuration
@RequiredArgsConstructor
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.worker")
public class MessageConsumingConfiguration {
    private final static String QUEUE_NAME = "job_queue";

    @Value("${activemq.broker.url}")
    private String brokerUrl;

    @Value("${activemq.broker.max.connections}")
    private int maxConnections;

    @Value("${activemq.broker.max.sessions}")
    private int maxSessions;

    @Value("${consumer.concurrency}")
    private String listenerConcurrency;

    @Bean
    public IntegrationFlow jobFlow() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(listenerContainer()))
                .transform(new JsonToObjectTransformer(JobDto.class))
                .channel(messageJobChannel())
                .get();
    }

    @Bean
    public DefaultMessageListenerContainer listenerContainer() {
        final DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(pooledConnectionFactory());
        listenerContainer.setDestination(jobQueue());
        listenerContainer.setConcurrency(listenerConcurrency);
//        listenerContainer.setAutoStartup(false);
        listenerContainer.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);


        return listenerContainer;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        final PooledConnectionFactory factory = new PooledConnectionFactory(connectionFactory());
        factory.setMaxConnections(maxConnections);
        factory.setMaximumActiveSessionPerConnection(maxSessions);
        return factory;
    }


    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        return connectionFactory;
    }

    @Bean
    public Queue jobQueue() {
        return new ActiveMQQueue(QUEUE_NAME);
    }

    @Bean
    public MessageChannel messageJobChannel() {
        return MessageChannels.direct().get();
    }
}
