package br.com.smd.xico.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping("/login")
	public String home() {
		return "Hello World! \n Welcome to CASa Móvel API";
		
	} 
}