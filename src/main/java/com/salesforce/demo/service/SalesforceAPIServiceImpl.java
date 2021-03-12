package com.salesforce.demo.service;

import com.salesforce.demo.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SalesforceAPIServiceImpl implements SalesforceAPIService{

    @Value("${USERNAME}")
    private String username;
    @Value("${PASSWORD}")
    private String password;
    @Value("${CLIENT_SECRET}")
    private String client_secret;
    @Value("${CLIENT_ID}")
    private String client_id;
    @Value("${GRANT_TYPE}")
    private String grant_type;

    public AuthenticationResponse login(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();

        params.add("username", username);
        params.add("password", password);
        params.add("client_secret", client_secret);
        params.add("client_id", client_id);
        params.add("grant_type",grant_type);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.postForEntity("https://login.salesforce.com/services/oauth2/token", request, AuthenticationResponse.class);
        return (AuthenticationResponse) response.getBody();
    }
}
