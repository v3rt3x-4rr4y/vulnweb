package com.jukka.vulnweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.jukka")
@EnableJpaRepositories(basePackages = "com.jukka.vulnweb.persistence.repo") 
@EntityScan("com.jukka.vulnweb.persistence.model")
public class VulnwebApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(VulnwebApplication.class, args);
	}
}
