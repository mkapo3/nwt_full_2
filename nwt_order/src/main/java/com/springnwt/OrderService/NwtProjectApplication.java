package com.springnwt.OrderService;

import com.springnwt.OrderService.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class NwtProjectApplication {
	@Autowired
	private EmailService emailService;
	public static void main(String[] args) {
		SpringApplication.run(NwtProjectApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendEmail(){
		emailService.sendEmail("azra99201@gmail.com", "test", "test");
	}

}
