package com.mobiquity.transfer;

import static com.mobiquity.transfer.CommonTestUtil.SAMPLE_ACCOUNT_1;
import static com.mobiquity.transfer.CommonTestUtil.SAMPLE_ACCOUNT_2;
import static com.mobiquity.transfer.model.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.transfer.exception.InternalServerException;
import com.mobiquity.transfer.model.Account;
import com.mobiquity.transfer.model.TransferRequest;
import com.mobiquity.transfer.model.TransferSuccessResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoneyTransferApplication.class)
class MoneyTransferApplicationTests {

	private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
	private static final String GET_ALL_ACCOUNTS_ENDPOINT = "/accounts";
	private static final String TRANSFER_ENDPOINT = "/transfer";

	@Autowired
	private MockMvc mvc;

	@Test
	void test_getAllAccounts_success() throws Exception {
		MvcResult mvcResult = mvc.perform(get(GET_ALL_ACCOUNTS_ENDPOINT)
				.accept(APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		List<Account> accountList = OBJECT_MAPPER.readValue(result, new TypeReference<>() {});
		assertThat(accountList).isNotNull().isNotEmpty().hasSize(4).isInstanceOf(List.class);
		assertThat(accountList.get(0)).isInstanceOf(Account.class);
	}

	@Test
	void test_getAccountsById_success() throws Exception {
		MvcResult mvcResult = mvc.perform(get(GET_ALL_ACCOUNTS_ENDPOINT + "/{id}", SAMPLE_ACCOUNT_1)
				.accept(APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		Account account = OBJECT_MAPPER.readValue(result, Account.class);
		assertThat(account).isNotNull().isInstanceOf(Account.class);
		assertThat(account.getAccountNumber()).isNotNull().isEqualTo(SAMPLE_ACCOUNT_1);
	}

	@Test
	void test_transferAmount_success() throws Exception {
		TransferRequest request = new TransferRequest()
				.accountType(SAVINGS)
				.amount(new BigDecimal(100))
				.creditAccountNumber(SAMPLE_ACCOUNT_1)
				.debitAccountNumber(SAMPLE_ACCOUNT_2);
		MvcResult mvcResult = mvc.perform(post(TRANSFER_ENDPOINT)
				.accept(APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		TransferSuccessResponse response = OBJECT_MAPPER.readValue(result, TransferSuccessResponse.class);
		assertThat(response.getUpdatedCreditBalance()).isNotNull().isEqualTo(new BigDecimal(200));
		assertThat(response.getUpdatedDebitBalance()).isNotNull().isEqualTo(new BigDecimal(900));
	}


	@Test
	void test_transferAmount_validateSufficientFunds_throwsException() throws Exception {
		TransferRequest request = new TransferRequest()
				.accountType(SAVINGS)
				.amount(new BigDecimal(100))
				.creditAccountNumber(SAMPLE_ACCOUNT_1)
				.debitAccountNumber("accountNumber3");

		MvcResult mvcResult = mvc.perform(post(TRANSFER_ENDPOINT)
				.accept(APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isNotNull().isEqualTo("insufficient balance in debit account !");
	}

	/*@Test
	void test_transferAmount_validateCreditorAccountInactive_throwsException() throws Exception {
		TransferRequest request = new TransferRequest()
				.accountType(SAVINGS)
				.amount(new BigDecimal(100))
				.creditAccountNumber("accountNumber4")
				.debitAccountNumber("accountNumber1");

		MvcResult mvcResult = mvc.perform(post(TRANSFER_ENDPOINT)
				.accept(APPLICATION_JSON)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		assertThat(result).isNotNull().isEqualTo("Creditor account is inactive !");
	}*/

}
