package com.mobiquity.transfer.service;

import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.valueOf;

import com.mobiquity.transfer.exception.InternalServerException;
import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.model.TransferRequest;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import com.mobiquity.transfer.repository.AccountRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

  private final AccountRepository accountRepository;

  public TransferSuccessResponse performTransfer(TransferRequest transferRequest) {
    //validateIsCreditorAccountActive(transferRequest);
    validateSufficientBalance(transferRequest.getDebitAccountNumber(), transferRequest.getAmount());
    BigDecimal creditBalance = creditAccount(transferRequest.getCreditAccountNumber(), transferRequest.getAmount());
    BigDecimal debitBalance = debitAccount(transferRequest.getDebitAccountNumber(), transferRequest.getAmount());
    return new TransferSuccessResponse()
        .updatedCreditBalance(creditBalance)
        .updatedDebitBalance(debitBalance);
  }

  private void validateIsCreditorAccountActive(TransferRequest transferRequest) {
    Account account = getAccountById(transferRequest.getCreditAccountNumber());
    if(FALSE.equals(account.getActive())) {
      throw new InternalServerException("Creditor account is inactive !");
    }
  }

  private void validateSufficientBalance(String debitAccountNumber, BigDecimal amount) {
    Account account = getAccountById(debitAccountNumber);
    if(account.getBalance().compareTo(valueOf(0)) < 0
        || account.getBalance().subtract(amount).compareTo(valueOf(0)) < 0) {
      throw new InternalServerException("insufficient balance in debit account !");
    }
  }

  private BigDecimal debitAccount(String debitAccountNumber, BigDecimal amount) {
    Account account = getAccountById(debitAccountNumber);
    return account.getBalance().subtract(amount);
  }

  private BigDecimal creditAccount(String creditAccountNumber, BigDecimal amount) {
    Account account = getAccountById(creditAccountNumber);
    return account.getBalance().add(amount);
  }

  private Account getAccountById(String accountNumber) {
    return accountRepository.getAccountById(accountNumber);
  }
}
