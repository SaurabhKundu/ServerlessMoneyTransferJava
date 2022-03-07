package com.mobiquity.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MoneyTransferApplication {

	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferApplication.class, args);
	}

	public ApplicationContext getApplicationContext(String... args) {
		if (applicationContext == null) {
			applicationContext = SpringApplication.run(MoneyTransferApplication.class, args);
		}
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
