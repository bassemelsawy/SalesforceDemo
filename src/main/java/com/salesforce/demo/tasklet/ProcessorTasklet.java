package com.salesforce.demo.tasklet;

import com.salesforce.demo.model.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessorTasklet implements Tasklet {

    @Autowired
    ReaderTasklet readerTasklet;

    private Root processorData;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        System.out.println("Processor data "+ readerTasklet.getReaderData() );
        Root readerData = readerTasklet.getReaderData();
        readerData.setTotalSize(10);
        processorData = readerData;

        return RepeatStatus.FINISHED;
    }

    public Root getProcessorData(){
        return this.processorData;
    }

}
