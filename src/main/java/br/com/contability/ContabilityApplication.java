package br.com.contability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

// Mexi Aqui
/*@Configuration
@ComponentScan
//@EnableAutoConfiguration
*/@SpringBootApplication
public class ContabilityApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ContabilityApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ContabilityApplication.class, args);
	}
}
