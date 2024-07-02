package com.startup.oda;

import com.startup.oda.service.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OdaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(OdaApplication.class, args);
		MailService bean = run.getBean(MailService.class);

	}

}
