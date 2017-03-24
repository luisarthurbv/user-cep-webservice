package com.luisarthurbv.cases.address;

import com.luisarthurbv.models.CepAddress;
import com.luisarthurbv.services.CepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetrieveAddress {

    private CepService cepService;

    @Autowired
    public RetrieveAddress(CepService cepService) {
        this.cepService = cepService;
    }

    public CepAddress retrieveAddress(String cep) throws CepNotFoundException {
        try {
            return cepService.findAddressByCep(cep);
        } catch (CepService.CepNotFoundException ignore) {
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new CepNotFoundException(cep);
    }

    public static class CepNotFoundException extends RuntimeException {

        public CepNotFoundException(String cep) {
            super(String.format("Couldn't find cep=%s", cep));
        }

    }

}
