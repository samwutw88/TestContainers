package com.testcontainers.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

class CustomerServiceTest {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:15-alpine"
  );

  CustomerService customerService;

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @BeforeEach
  void setUp() {
    DBConnectionProvider connectionProvider = new DBConnectionProvider(
      postgres.getJdbcUrl(),
      postgres.getUsername(),
      postgres.getPassword()
    );
    customerService = new CustomerService(connectionProvider);
  }

  @Test
  void shouldGetCustomers() {
    customerService.createCustomer(new Customer(1L, "George"));
    customerService.createCustomer(new Customer(2L, "John"));

    List<Customer> customers = customerService.getAllCustomers();
    assertEquals(2, customers.size());
  }
  
  @Test
  void shouldGetCustomers_2000() {
	  long i =300;
	  // 延長時間
	  while (i > 3){
		    customerService.createCustomer(new Customer(i, "George" +  Long.toString(i)  ));
		    i--;
	}  
    System.out.println(i);
    List<Customer> customers = customerService.getAllCustomers();
    assertEquals(299, customers.size());
  }
}