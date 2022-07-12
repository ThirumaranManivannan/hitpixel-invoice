package com.hitpixel.paymentgwinvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PaymentGWInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentGWInvoiceApplication.class, args);
	}

}
