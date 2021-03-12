package com.salesforce.demo.tasklet;

import com.salesforce.demo.model.Root;
import com.salesforce.demo.service.SalesforceAPIServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ReaderTasklet implements Tasklet {

    @Autowired
    SalesforceAPIServiceImpl salesforceAPIService;

    private Root salesForceData;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        fetchAccountDataFromAPI();
        System.out.println("Read data " + this.salesForceData);
        return RepeatStatus.FINISHED;
    }

    private void fetchAccountDataFromAPI() {
        System.out.println("Read the data");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + salesforceAPIService.login().getAccess_token());
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Root> response = restTemplate.exchange(salesforceAPIService.login().getInstance_url()+"/services/data/v22.0/query?q=select Id, Name from Account", HttpMethod.GET, request, Root.class);
        this.salesForceData = response.getBody();
    }

    public Root getReaderData(){
        return this.salesForceData;
    }
}
