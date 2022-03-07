package com.mobiquity.transfer.service;

import static com.mobiquity.transfer.CommonTestUtil.SAMPLE_ACCOUNT_1;
import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.repository.AccountRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @InjectMocks
  private AccountService accountService;
  @Mock
  private AccountRepository accountRepository;

  @Test
  void test_getAllAccounts_success() {
    when(accountRepository.getAccounts())
        .thenReturn(
            singletonList(
                new Account().accountNumber(SAMPLE_ACCOUNT_1).accountType(SAVINGS)));
    List<Account> accountList = accountService.getAllAccounts();
    assertThat(accountList)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);
    assertThat(accountList.get(0))
        .isNotNull()
        .isInstanceOf(Account.class);
    assertThat(accountList.get(0).getAccountNumber())
        .isNotNull()
        .isNotBlank()
        .isEqualTo(SAMPLE_ACCOUNT_1);
  }

  @Test
  void test_getAccountsById_success() {
    when(accountRepository.getAccountById(anyString()))
        .thenReturn(new Account().accountNumber(SAMPLE_ACCOUNT_1));
    Account account = accountService.getAccountById(SAMPLE_ACCOUNT_1);
    assertThat(account).isNotNull().isInstanceOf(Account.class);
    assertThat(account.getAccountNumber()).isEqualTo(SAMPLE_ACCOUNT_1);
  }

}
