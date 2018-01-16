package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SimpleHandler implements Handler {
    //TODO: implement logic of data retrieval

    private final ApplicationContext applicationContext;
    private final List<String> processorNames;

    public SimpleHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.processorNames = Arrays.asList(applicationContext.getBeanNamesForType(Processor.class));
    }

    @Override
    public String accept(JobDto jobDto) {
        for (String processorName : processorNames) {
            Processor processor = applicationContext.getBean(processorName, Processor.class);
            if (processor.canProcess(jobDto)) {
                return processor.accept(jobDto);
            }
        }
        return JobStatus.FAILED;
    }
}
