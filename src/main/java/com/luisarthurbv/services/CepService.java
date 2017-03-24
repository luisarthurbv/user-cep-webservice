package com.luisarthurbv.services;

import com.luisarthurbv.models.CepAddress;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CepService {

    private String host;
    private String path;

    @Autowired
    public CepService(@Value("${cepwebservice.host}") String host,
                      @Value("${cepwebservice.path}") String path) {
        this.host = host;
        this.path = path;
    }

    public CepAddress findAddressByCep(String cep) throws CepNotFoundException {
        JSONObject parameters = new JSONObject();
        parameters.put("cep", cep);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(parameters.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<CepServiceResponse> response = restTemplate.exchange(
                    getEndpoint(), HttpMethod.POST, entity, CepServiceResponse.class
            );
            if(response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().getAddress();
            }
            throw new CepNotFoundException(cep);
        } catch (Exception e) {
            log.error(String.format("Error finding cep: %s. Message: %s", cep, e.getMessage()), e);
            throw new CepNotFoundException(cep);
        }
    }


    public static class CepServiceResponse {
        private CepAddress address;

        public CepAddress getAddress() {
            return address;
        }

        public void setAddress(CepAddress address) {
            this.address = address;
        }
    }

    private String getEndpoint() {
        return host + path;
    }

    public static class CepNotFoundException extends RuntimeException {

        public CepNotFoundException(String cep) {
            super(String.format("Couldn't find address for cep=%s", cep));
        }

    }

}
