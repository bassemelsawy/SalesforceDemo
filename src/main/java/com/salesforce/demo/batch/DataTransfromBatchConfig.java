package com.salesforce.demo.batch;


import com.salesforce.demo.tasklet.ProcessorTasklet;
import com.salesforce.demo.tasklet.ReaderTasklet;
import com.salesforce.demo.tasklet.WriterTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DataTransfromBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ReaderTasklet readerTasklet;

    @Autowired
    private ProcessorTasklet processorTasklet;

    @Autowired
    private WriterTasklet writerTasklet;


    @Bean
    protected Step readTask() {
        return stepBuilderFactory
                .get("readLines")
                .tasklet(readerTasklet)
                .build();
    }

    @Bean
    protected Step processTask() {
        return stepBuilderFactory
                .get("processLines")
                .tasklet(processorTasklet)
                .build();
    }

    @Bean
    protected Step writeTask() {
        return stepBuilderFactory
                .get("writeLines")
                .tasklet(writerTasklet)
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("taskletJob")
                .start(readTask())
                .next(processTask())
                .next(writeTask())
                .build();
    }
}
