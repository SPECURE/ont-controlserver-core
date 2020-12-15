package com.specture.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specture.core.service.JiraApiService;
import com.specture.core.config.JiraApiConfig;
import com.specture.core.model.internal.JiraHttpRequestEntity;
import com.specture.core.model.internal.JiraRequestFieldsValue;
import com.specture.core.utils.BasicAuthHeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class JiraApiServiceImpl implements JiraApiService {

    private static final String serviceDeskApiRequest =  "/rest/servicedeskapi/request";
    private static final String authorization =  "Authorization";

    private final RestTemplate restTemplate;
    private final JiraApiConfig jiraApiConfig;

    @Override
    public void createIssue(String summary, String description) {

        final String url = jiraApiConfig.getUrl() + serviceDeskApiRequest;

        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();

        headers.add(authorization, BasicAuthHeaderUtil.getHeader(jiraApiConfig.getUsername(), jiraApiConfig.getPassword()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String body = mapper.writeValueAsString(getBodyRequestData(summary, description));
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);
        } catch (ResourceAccessException | HttpServerErrorException | JsonProcessingException | HttpClientErrorException e) {
            System.out.println(e.getMessage());
            // TODO: notify admin about this error
            // it's not critical, use logger here
        }
    }

    private JiraHttpRequestEntity getBodyRequestData(String summary, String description) {
        JiraRequestFieldsValue requestFieldsValue = JiraRequestFieldsValue.builder()
                .summary(summary)
                .description(description)
                .build();
        return JiraHttpRequestEntity.builder()
                .requestFieldValues(requestFieldsValue)
                .requestTypeId(jiraApiConfig.getRequestTypeId())
                .serviceDeskId(jiraApiConfig.getServiceDeskId())
                .build();
    }
}
