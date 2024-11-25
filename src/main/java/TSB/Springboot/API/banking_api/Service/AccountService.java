package TSB.Springboot.API.banking_api.Service;

import TSB.Springboot.API.banking_api.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private DataLoader dataLoader;

    public List<Account> getAccountsByCustomerId(String customerId) {
        return dataLoader.getAccounts().stream()
                .filter(account -> account.getCustomerId().equals(customerId))
                .toList();
    }

    public List<Account> getAccounts() {
        return dataLoader.getAccounts();
    }
}
