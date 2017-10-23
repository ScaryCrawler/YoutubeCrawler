package com.crawler.youtube_crawler.rest_api.configuration;

import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
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
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.util.Collections;

@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.*")
public class MessagingConfiguration {
    public final static String QUEUE_NAME = "job_queue";

    @Value("${activemq.broker.url}")
    private String brokerUrl;

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
        return IntegrationFlows.from(messageJobChannel())
                .transform(new ObjectToJsonTransformer())
                .handle(Jms.outboundAdapter(jmsTemplate())
                        .destination(jobQueue()))
                .get();
    }
}
