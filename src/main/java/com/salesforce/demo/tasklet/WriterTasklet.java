package com.salesforce.demo.tasklet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesforce.demo.model.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class WriterTasklet implements Tasklet {

    @Autowired
    ProcessorTasklet processorTasklet;

    @Value("${DIR_OUTPUT_FILE}")
    private String outputFileDirectory;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        System.out.println("Writer data " + processorTasklet.getProcessorData());
        Root processorData = processorTasklet.getProcessorData();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputFileDirectory), processorData);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return RepeatStatus.FINISHED;
    }
}
