package com.crawler.youtube_crawler.worker.configuration;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.worker.activemq.props.ConsumerProps;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
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
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.worker")
@RequiredArgsConstructor
public class ConsumerMessagingConfiguration {
    private final static String QUEUE_NAME = "job_queue";

    private final ConsumerProps consumerProps;

    @Bean
    public DefaultMessageListenerContainer listenerContainer() {
        final DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(pooledConnectionFactory());
        listenerContainer.setDestination(jobQueue());
        listenerContainer.setConcurrency(consumerProps.getListenerConcurrency());
        listenerContainer.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);


        return listenerContainer;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        final PooledConnectionFactory factory = new PooledConnectionFactory(connectionFactory());
        factory.setMaxConnections(consumerProps.getMaxConnections());
        factory.setMaximumActiveSessionPerConnection(consumerProps.getMaxSessions());
        return factory;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(consumerProps.getBrokerUrl());
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

    @Bean
    public IntegrationFlow jobFlow() {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(listenerContainer()))
                .transform(new JsonToObjectTransformer(JobDto.class))
                .channel(messageJobChannel())
                .get();
    }
}
