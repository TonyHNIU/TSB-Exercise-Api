package TSB.Springboot.API.banking_api.Service;

import TSB.Springboot.API.banking_api.Model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactions.stream()
                .filter(transaction -> transaction.getAccountId().equals(accountId))
                .toList();
    }

    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
