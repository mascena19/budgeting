package rm.budgeting.infraestructure.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import rm.budgeting.domain.Category;
import rm.budgeting.infraestructure.persistence.entitty.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCategory(Category category);

}
