package com.crawler.youtube_crawler.worker;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.worker.producer.JobSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableAsync
@EnableMongoRepositories(basePackages = "com.crawler.youtube_crawler.*")
public class Application {
    public static void main(final String[] arguments) throws Exception {
       ApplicationContext context = SpringApplication.run(Application.class, arguments);
//       context.getBean(JobSender.class).sendJob(new JobDto());
    }
}
