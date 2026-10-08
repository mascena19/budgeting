package rm.budgeting.domain;

import java.util.List;

public interface TransactionRepository {
    rm.budgeting.domain.Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory(Category category);
}
