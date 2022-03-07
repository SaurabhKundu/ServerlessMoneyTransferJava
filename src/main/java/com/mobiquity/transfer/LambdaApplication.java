/*
package com.mobiquity.transfer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class LambdaApplication {

  private ApplicationContext applicationContext;

  public ApplicationContext getApplicationContext(String... args) {
    if (applicationContext == null) {
      applicationContext = SpringApplication.run(LambdaApplication.class, args);
    }
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public static void main(String[] args) {
    new LambdaApplication().getApplicationContext(args);
  }
}
*/
