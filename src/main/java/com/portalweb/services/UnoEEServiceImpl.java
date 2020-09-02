package com.portalweb.services;

import com.portalweb.repositories.UnoEERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnoEEServiceImpl implements UnoEEService{

    @Autowired
    private UnoEERepository unoEERepositoryObj;


    @Override
    public String findRowIdCliente(String nit) {
        return unoEERepositoryObj.findRowidCliente(nit);
    }
}
