package br.com.smd.xico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class XicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XicoApplication.class, args);
		System.out.println("Application started");
		System.out.println(new BCryptPasswordEncoder().encode("abcd1234"));
	}

}
