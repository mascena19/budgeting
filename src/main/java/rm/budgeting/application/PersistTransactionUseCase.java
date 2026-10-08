package rm.budgeting.application;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import rm.budgeting.application.input.PersistTransactionInput;
import rm.budgeting.application.output.TransactionOutput;
import rm.budgeting.domain.Category;
import rm.budgeting.domain.Transaction;
import rm.budgeting.domain.TransactionRepository;

@Service

public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));

        return TransactionOutput.from(transaction);
    }
}
