package rm.budgeting.application;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import rm.budgeting.application.output.TransactionOutput;
import rm.budgeting.domain.Category;
import rm.budgeting.domain.TransactionRepository;

import java.util.List;

@Service
public class ListTransactionCategoryUseCase {

    private final TransactionRepository transactionRepository;
    public ListTransactionCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "list-transaction-by-category", description = "Lista transações financeiras por categoria")
    public List<TransactionOutput> execute(@ToolParam(description = "Categoria de uma transação") Category category) {
        return transactionRepository.findAllByCategory(category).stream().map(TransactionOutput::from).toList();
    }
}
