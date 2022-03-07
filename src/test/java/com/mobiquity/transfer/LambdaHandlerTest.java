package com.mobiquity.transfer;

import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;

import com.mobiquity.transfer.function.model.LambdaRequest;
import com.mobiquity.transfer.function.model.LambdaResponse;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LambdaHandlerTest {

  @InjectMocks
  private LambdaHandler lambdaHandler;

  @Test
  void test_handleRequest_success() {
    LambdaRequest request = getLambdaRequest();
    LambdaResponse response = lambdaHandler.handleRequest(request, null);
    assertThat(response).isNotNull();
    assertThat((TransferSuccessResponse) response.getBody()).isNotNull();
    assertThat(((TransferSuccessResponse) response.getBody()).getUpdatedDebitBalance()).isEqualTo(new BigDecimal(900));
    assertThat(((TransferSuccessResponse) response.getBody()).getUpdatedCreditBalance()).isEqualTo(new BigDecimal(200));
  }

  private LambdaRequest getLambdaRequest() {
    return LambdaRequest.builder()
        .accountType(SAVINGS)
        .creditAccountNumber("accountNumber1")
        .debitAccountNumber("accountNumber2")
        .amount(new BigDecimal(100))
        .build();
  }
}
