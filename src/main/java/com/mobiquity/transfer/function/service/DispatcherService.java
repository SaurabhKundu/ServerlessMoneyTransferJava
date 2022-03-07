package com.mobiquity.transfer.function.service;

import com.mobiquity.transfer.function.model.LambdaRequest;
import com.mobiquity.transfer.function.model.LambdaResponse;
import com.mobiquity.transfer.model.TransferRequest;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import com.mobiquity.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatcherService {

  private final TransferService transferService;

  public void processRequest(LambdaRequest request, LambdaResponse response) {
    TransferRequest transferRequest = getTransferRequest(request);
    TransferSuccessResponse transferSuccessResponse = transferService.performTransfer(transferRequest);
    processResponse(response, transferSuccessResponse);
  }

  private void processResponse(LambdaResponse response, TransferSuccessResponse transferSuccessResponse) {
    response.setBody(transferSuccessResponse);
    response.setErrors(null);
    response.setHttpStatusCode(200);
  }

  private TransferRequest getTransferRequest(LambdaRequest request) {
    return new TransferRequest()
        .creditAccountNumber(request.getCreditAccountNumber())
        .debitAccountNumber(request.getDebitAccountNumber())
        .amount(request.getAmount())
        .accountType(request.getAccountType());
  }
}
