package com.mobiquity.transfer.controller;

import com.mobiquity.transfer.api.TransferApi;
import com.mobiquity.transfer.model.TransferRequest;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import com.mobiquity.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController implements TransferApi {

  private final TransferService transferService;

  @Override
  public ResponseEntity<TransferSuccessResponse> transferBalance(TransferRequest transferRequest) {
    return ResponseEntity.ok(transferService.performTransfer(transferRequest));
  }
}
