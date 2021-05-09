package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IHistoriqueStatutDAO;
import com.adria.adriaseal.dao.ITransactionDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.model.enums.StatutTransactionEnum;
import com.adria.adriaseal.repositories.TransactionRepository;
import com.adria.adriaseal.util.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionDAOImpl implements ITransactionDAO {
    private final TransactionRepository transactionRepository;
    private final IHistoriqueStatutDAO historiqueStatutDAO;


    @Override
    public TransactionEntity init() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .statut(StatutTransactionEnum.RECEIVED)
                .sourceIpAddress(EnvUtil.getClientIpAddressIfItExists())
                .userAgent(EnvUtil.getUserAgentIfItExists())
                .build();
        transactionEntity = transactionRepository.save(transactionEntity);
        historiqueStatutDAO.save(transactionEntity);
        return transactionEntity;
    }

    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) {

        transactionEntity = transactionRepository.save(transactionEntity);
        historiqueStatutDAO.save(transactionEntity);
        return transactionEntity;
    }

    @Override
    public TransactionEntity findTransactionById(UUID transactionID) {

        return transactionRepository.findById(transactionID).orElseThrow(() -> new ResourceNotFoundException(String.format("%s with id:%s not found !", TransactionEntity.class.getSimpleName(), transactionID)));
    }
}
