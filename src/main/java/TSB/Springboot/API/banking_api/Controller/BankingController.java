package TSB.Springboot.API.banking_api.Controller;

import TSB.Springboot.API.banking_api.Model.Account;
import TSB.Springboot.API.banking_api.Model.Transaction;
import TSB.Springboot.API.banking_api.Model.TransferRequest;
import TSB.Springboot.API.banking_api.Service.AccountService;
import TSB.Springboot.API.banking_api.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BankingController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts/{customerId}")
    public List<Account> getAccounts(@PathVariable String customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }

    @GetMapping("/transactions/{accountId}")
    public List<Transaction> getTransactions(@PathVariable String accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest request) {
        Account fromAccount = accountService.getAccounts().stream()
                .filter(account -> account.getId().equals(request.getFromAccountId()))
                .findFirst().orElse(null);
        Account toAccount = accountService.getAccounts().stream()
                .filter(account -> account.getId().equals(request.getToAccountId()))
                .findFirst().orElse(null);

        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.badRequest().body("Invalid account(s)");
        }

        if (fromAccount.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        Transaction outgoingTransaction = new Transaction();
        outgoingTransaction.setId(UUID.randomUUID().toString());
        outgoingTransaction.setAccountId(fromAccount.getId());
        outgoingTransaction.setAmount(request.getAmount());
        outgoingTransaction.setType("TRANSFER_OUT");
        transactionService.saveTransaction(outgoingTransaction);

        Transaction incomingTransaction = new Transaction();
        incomingTransaction.setId(UUID.randomUUID().toString());
        incomingTransaction.setAccountId(toAccount.getId());
        incomingTransaction.setAmount(request.getAmount());
        incomingTransaction.setType("TRANSFER_IN");
        transactionService.saveTransaction(incomingTransaction);

        return ResponseEntity.ok("Transfer successful");
    }
}
