package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IRepertoireDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.RepertoireEntity;
import com.adria.adriaseal.model.enums.TypeContenuRepertoireEnum;
import com.adria.adriaseal.repositories.RepertoireRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RepertoireDAOImpl implements IRepertoireDAO {
    private final RepertoireRepository repertoireRepository;

    public RepertoireDAOImpl(RepertoireRepository repertoireRepository) {
        this.repertoireRepository = repertoireRepository;
    }

    @Override
    public RepertoireEntity findRepertoireByContentType(TypeContenuRepertoireEnum typeContenuRepertoire) {
        return repertoireRepository.findRepertoireEntityByContentType(typeContenuRepertoire)
                .orElseThrow( ()-> new ResourceNotFoundException(String.format("RepertoireEntity with Content type %s not found !", typeContenuRepertoire.name())));
    }

    @Override
    public boolean existsByContentType(TypeContenuRepertoireEnum typeContenuRepertoire) {
        return repertoireRepository.existsByContentType(typeContenuRepertoire);
    }
}
