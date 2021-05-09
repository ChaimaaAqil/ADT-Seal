package com.adria.adriaseal.dao;

import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;

public interface IRepertoireDAO {
    RepertoireEntity findRepertoireByContentType(TypeContenuRepertoireEnum typeContenuRepertoire);
    boolean existsByContentType(TypeContenuRepertoireEnum typeContenuRepertoire);
}
