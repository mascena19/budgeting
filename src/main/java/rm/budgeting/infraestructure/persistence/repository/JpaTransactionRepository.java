package rm.budgeting.infraestructure.persistence.repository;

import org.springframework.stereotype.Repository;
import rm.budgeting.domain.Category;
import rm.budgeting.domain.Transaction;
import rm.budgeting.domain.TransactionRepository;
import rm.budgeting.infraestructure.persistence.entitty.TransactionEntity;

import java.util.List;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionEntity.from(transaction);
        return transactionEntityRepository.save(entity).toDomain();

    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
      return transactionEntityRepository.findAllByCategory(category)
               .stream()
               .map(TransactionEntity::toDomain)
               .toList();
    }
}
