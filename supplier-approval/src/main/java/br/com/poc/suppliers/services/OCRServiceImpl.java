package br.com.poc.suppliers.services;

import br.com.poc.suppliers.model.OCRRequest;
import br.com.poc.suppliers.model.OCRResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OCRServiceImpl implements OCRService {

    private final RestTemplate restTemplate;
    private final String URI = "https://back-prd.azurewebsites.net/api/camunda";
    private final String API_KEY = "771295D4-09F3-480C-AD9B-44D700C1448D";

    @Override
    public OCRResult processDocument(OCRRequest ocrRequest) {
        //Set the headers you need send
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);
        headers.set("Accept", "application/json");
        final HttpEntity<OCRRequest> request = new HttpEntity<OCRRequest>(ocrRequest, headers);

        return restTemplate.postForEntity(URI, request, OCRResult.class).getBody();
    }

    private HttpEntity<String> buildHttpEntity(Map<String,Object> headerParams) {
        HttpHeaders headers = new HttpHeaders();
        headerParams.forEach((k,v)->headers.set(k, v.toString()));
        return new HttpEntity<String>(headers);
    }
}
