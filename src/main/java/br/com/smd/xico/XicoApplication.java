package br.com.smd.xico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class XicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XicoApplication.class, args);
		System.out.println("Application started");
	}

}
