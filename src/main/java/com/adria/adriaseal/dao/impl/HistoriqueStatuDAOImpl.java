package com.adria.adriaseal.dao.impl;

import com.adria.adriaseal.dao.IHistoriqueStatutDAO;
import com.adria.adriaseal.exception.ResourceNotFoundException;
import com.adria.adriaseal.model.entities.HistoriqueStatutEntity;
import com.adria.adriaseal.model.entities.TransactionEntity;
import com.adria.adriaseal.repositories.HistoriqueStatutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Date;

@Service
@Transactional
public class HistoriqueStatuDAOImpl implements IHistoriqueStatutDAO {
    private final HistoriqueStatutRepository historiqueStatutRepository;

    public HistoriqueStatuDAOImpl(HistoriqueStatutRepository historiqueStatutRepository) {
        this.historiqueStatutRepository = historiqueStatutRepository;
    }

    @Override
    public void save(TransactionEntity transactionEntity) {
        if(historiqueStatutRepository.existsByTransaction(transactionEntity)) {
            HistoriqueStatutEntity lastStatusHistory = historiqueStatutRepository.findFirstByTransactionOrderByDateCreationDesc(transactionEntity)
                    .orElseThrow(()-> new ResourceNotFoundException(String.format("No Status History recorded for transaction with id : %s", transactionEntity.getId())));

            if (transactionEntity.getStatut().equals(lastStatusHistory.getStatus())) return;

            setDurationLastStatusHistory(lastStatusHistory);
        }
        historiqueStatutRepository.save(HistoriqueStatutEntity
                .builder()
                .transactionEntity(transactionEntity)
                .status(transactionEntity.getStatut())
                .build());
    }

    @Override
    public void logErrorOccurred(TransactionEntity transactionEntity) {

    }
    private void setDurationLastStatusHistory(HistoriqueStatutEntity lastStatusHistory) {
        lastStatusHistory.setDuration(Duration.between(lastStatusHistory.getDateCreation().toInstant(), new Date().toInstant()));
        historiqueStatutRepository.save(lastStatusHistory);
    }
}
