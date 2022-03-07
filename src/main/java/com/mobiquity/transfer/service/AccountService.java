package com.mobiquity.transfer.service;

import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public List<Account> getAllAccounts() {
    return accountRepository.getAccounts();
  }

  public Account getAccountById(String id) {
    return accountRepository.getAccountById(id);
  }
}
