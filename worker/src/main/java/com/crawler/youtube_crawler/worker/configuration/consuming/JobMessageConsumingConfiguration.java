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
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.worker")
public abstract class JobMessageConsumingConfiguration extends MessageConsumingConfiguration{
    final static String QUEUE_NAME = "job_queue";

    public JobMessageConsumingConfiguration() {
        super(QUEUE_NAME);
    }
}
