package com.mobiquity.transfer.function.model;

import com.mobiquity.transfer.model.AccountType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LambdaRequest {

  private String creditAccountNumber;
  private String debitAccountNumber;
  private AccountType accountType;
  private BigDecimal amount;

}
