package com.mobiquity.transfer.controller;

import com.mobiquity.transfer.api.AccountsApi;
import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

  private final AccountService accountService;

  @Override
  public ResponseEntity<List<Account>> getAccounts() {
    return ResponseEntity.ok(accountService.getAllAccounts());
  }

  @Override
  public ResponseEntity<Account> getAccountsById(String id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }
}
