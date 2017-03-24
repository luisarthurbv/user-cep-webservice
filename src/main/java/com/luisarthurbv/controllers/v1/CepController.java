package com.luisarthurbv.controllers.v1;

import com.luisarthurbv.cases.address.RetrieveAddress;
import com.luisarthurbv.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cep/v1")
public class CepController {

    private RetrieveAddress retrieveAddress;

    @Autowired
    public CepController(RetrieveAddress retrieveAddress) {
        this.retrieveAddress = retrieveAddress;
    }

    @RequestMapping(value = "/getAddress", method = RequestMethod.POST)
    public Map retrieveAddress(@RequestBody RetrieveAddressRequest r) {
        try {
            return MapUtils.convertObject(retrieveAddress.retrieveAddress(r.cep));
        } catch (RetrieveAddress.CepNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", String.format("Couldn't find cep: %s", r.cep));
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Map<String, String> response = new HashMap<>();
            response.put("error", String.format("Couldn't find cep: %s", r.cep));
            return response;
        }
    }

    public static class RetrieveAddressRequest {

        private String cep;

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

    }

}
