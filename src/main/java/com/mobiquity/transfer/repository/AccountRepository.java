package com.mobiquity.transfer.repository;

import static com.mobiquity.transfer.model.AccountType.CURRENT;
import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.mobiquity.transfer.model.Account;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AccountRepository {

  private static final List<Account> accounts = new ArrayList<>();
  private static final Map<String, Account> accountMap = new HashMap<>();

  static {
    accounts.add(new Account().accountNumber("accountNumber1").accountType(SAVINGS).balance(new BigDecimal(100)).active(TRUE));
    accounts.add(new Account().accountNumber("accountNumber2").accountType(SAVINGS).balance(new BigDecimal(1000)).active(TRUE));
    accounts.add(new Account().accountNumber("accountNumber3").accountType(SAVINGS).balance(new BigDecimal(90)).active(TRUE));
    accounts.add(new Account().accountNumber("accountNumber4").accountType(CURRENT).balance(new BigDecimal(300)).active(FALSE));

    accounts.forEach(account -> accountMap.put(account.getAccountNumber(), account));
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  private AccountRepository() {
    //empty
  }

  public Account getAccountById(String id) {
    return accountMap.get(id);
  }
}
