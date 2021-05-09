package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IADTConstDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.ADTConstEntity;
import com.adria.adriaseal.repositories.ADTConstRepository;
import org.springframework.stereotype.Service;

@Service
public class ADTConstDAOImpl implements IADTConstDAO {
    private final ADTConstRepository adtConstRepository;

    public ADTConstDAOImpl(ADTConstRepository adtConstRepository) {
        this.adtConstRepository = adtConstRepository;
    }

    @Override
    public void save(ADTConstEntity adtConstEntity) {
        adtConstRepository.save(adtConstEntity);
    }

    @Override
    public ADTConstEntity getADTConstByCode(String code) {
        return adtConstRepository.findADTConstByCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("Constant not found with code:"+code));
    }
}

