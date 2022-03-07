package com.mobiquity.transfer.service;

import static com.mobiquity.transfer.CommonTestUtil.SAMPLE_ACCOUNT_1;
import static com.mobiquity.transfer.CommonTestUtil.SAMPLE_ACCOUNT_2;
import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mobiquity.transfer.exception.InternalServerException;
import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.model.TransferRequest;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import com.mobiquity.transfer.repository.AccountRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

  @InjectMocks
  private TransferService transferService;
  @Mock
  private AccountRepository accountRepository;

  @Test
  void test_performTransfer_success() {
    TransferRequest request = new TransferRequest()
        .accountType(SAVINGS)
        .amount(new BigDecimal(100))
        .creditAccountNumber(SAMPLE_ACCOUNT_1)
        .debitAccountNumber(SAMPLE_ACCOUNT_2);
    when(accountRepository.getAccountById(SAMPLE_ACCOUNT_1))
        .thenReturn(new Account().accountNumber(SAMPLE_ACCOUNT_1).accountType(SAVINGS).balance(new BigDecimal(100)));
    when(accountRepository.getAccountById(SAMPLE_ACCOUNT_2))
        .thenReturn(new Account().accountNumber(SAMPLE_ACCOUNT_2).accountType(SAVINGS).balance(new BigDecimal(1000)));

    TransferSuccessResponse response = transferService.performTransfer(request);
    assertThat(response.getUpdatedCreditBalance()).isNotNull().isEqualTo(new BigDecimal(200));
    assertThat(response.getUpdatedDebitBalance()).isNotNull().isEqualTo(new BigDecimal(900));
  }

  @Test
  void test_performTransfer_validateSufficientBalance_throwsException() {
    TransferRequest request = new TransferRequest()
        .accountType(SAVINGS)
        .amount(new BigDecimal(100))
        .creditAccountNumber(SAMPLE_ACCOUNT_1)
        .debitAccountNumber(SAMPLE_ACCOUNT_2);
    when(accountRepository.getAccountById(SAMPLE_ACCOUNT_2))
        .thenReturn(new Account().accountNumber(SAMPLE_ACCOUNT_2).accountType(SAVINGS).balance(new BigDecimal(90)));
    /*when(accountRepository.getAccountById(SAMPLE_ACCOUNT_1))
        .thenReturn(new Account().accountNumber(SAMPLE_ACCOUNT_1).accountType(SAVINGS).balance(new BigDecimal(200)));*/

   assertThrows(InternalServerException.class, () -> transferService.performTransfer(request));
  }

  /*@Test
  void test_performTransfer_validateIsAccountActive_throwsException() {
    TransferRequest request = new TransferRequest()
        .accountType(SAVINGS)
        .amount(new BigDecimal(100))
        .creditAccountNumber("accountNumber4")
        .debitAccountNumber(SAMPLE_ACCOUNT_2);

    when(accountRepository.getAccountById("accountNumber4"))
        .thenReturn(new Account().accountNumber("accountNumber4").accountType(SAVINGS).balance(new BigDecimal(100)).active(FALSE));
    assertThrows(InternalServerException.class, () -> transferService.performTransfer(request));
  }*/
}
