package com.mobiquity.transfer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mobiquity.transfer.exception.InternalServerException;
import com.mobiquity.transfer.function.model.LambdaRequest;
import com.mobiquity.transfer.function.model.LambdaResponse;
import com.mobiquity.transfer.function.service.DispatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class LambdaHandler implements RequestHandler<LambdaRequest, LambdaResponse> {

  protected ApplicationContext applicationContext;

  @Override
  public LambdaResponse handleRequest(LambdaRequest request, Context context) {
    log.info("invoking handleRequest method of Lambda Handler with request: {}", request.toString());
    initialiseApplication();

    LambdaResponse response = new LambdaResponse();
    try {
      applicationContext.getBean(DispatcherService.class).processRequest(request, response);
    } catch (Exception e) {
      log.error("got error while processing request", e);
    }
    return response;
  }

  private void initialiseApplication(String... args) {
    try {
      if (applicationContext == null) {
        log.info("Initializing Lambda Application Context...");
        MoneyTransferApplication application = new MoneyTransferApplication();
        applicationContext = application.getApplicationContext(args);
        if (applicationContext == null) {
          log.error("Application Context initialization failed");
          throw new InternalServerException("Error in initializing Application Context");
        }
        log.info("Lambda Application Context initialized");
      }
    } catch (Exception e) {
      log.error("got exception while starting lambda handler", e);
    }
  }
}
