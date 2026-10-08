package rm.budgeting.infraestructure.http.request;

import rm.budgeting.application.input.PersistTransactionInput;
import rm.budgeting.domain.Category;

public record TransactionRequest(String description, Category category, long amount) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description(), amount, category);
    }
}
