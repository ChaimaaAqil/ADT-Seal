package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.ADTConstEntity;

public interface IADTConstDAO {
    void save(ADTConstEntity adtConstEntity);
    ADTConstEntity getADTConstByCode(String code);
}
