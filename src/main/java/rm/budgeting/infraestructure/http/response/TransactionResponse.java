package rm.budgeting.infraestructure.http.response;

import rm.budgeting.application.output.TransactionOutput;

import java.math.BigDecimal;

public record TransactionResponse(String id, String description, String category, double amount) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(
                output.id(),
                output.description(),
                output.category(),
                output.value()
        );
    }
}
