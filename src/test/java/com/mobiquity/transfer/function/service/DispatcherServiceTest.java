package com.mobiquity.transfer.function.service;

import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mobiquity.transfer.function.model.LambdaRequest;
import com.mobiquity.transfer.function.model.LambdaResponse;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import com.mobiquity.transfer.service.TransferService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DispatcherServiceTest {

  @InjectMocks
  private DispatcherService dispatcherService;
  @Mock
  private TransferService transferService;

  @Test
  void test_processRequest() {
    LambdaResponse response = new LambdaResponse();
    LambdaRequest request = getLambdaRequest();
    when(transferService.performTransfer(any()))
        .thenReturn(new TransferSuccessResponse()
            .updatedCreditBalance(new BigDecimal(200))
            .updatedDebitBalance(new BigDecimal(900)));
    dispatcherService.processRequest(request, response);
    assertThat(response).isNotNull();
    assertThat(response.getHttpStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isNotNull();
    assertThat(((TransferSuccessResponse) response.getBody()).getUpdatedCreditBalance()).isEqualTo(new BigDecimal(200));
    assertThat(((TransferSuccessResponse) response.getBody()).getUpdatedDebitBalance()).isEqualTo(new BigDecimal(900));
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
