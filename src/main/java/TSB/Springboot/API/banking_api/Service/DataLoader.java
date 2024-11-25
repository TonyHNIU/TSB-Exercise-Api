package TSB.Springboot.API.banking_api.Service;

import TSB.Springboot.API.banking_api.Model.Account;
import TSB.Springboot.API.banking_api.Model.Customer;
import TSB.Springboot.API.banking_api.Model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataLoader {
    private List<Account> accounts;
    private List<Customer> customers;
    private List<Transaction> transactions;

    public DataLoader() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        accounts = mapper.readValue(new File("src/main/resources/data/accounts.json"), new TypeReference<List<Account>>() {});
        customers = mapper.readValue(new File("src/main/resources/data/customers.json"), new TypeReference<List<Customer>>() {});
        transactions = mapper.readValue(new File("src/main/resources/data/transactions.json"), new TypeReference<List<Transaction>>() {});
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
