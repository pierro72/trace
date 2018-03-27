package com.ex.demo;


import com.ex.demo.config.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	private Environment env;

	@Autowired
	private YAMLConfig myConfig;



	@Override
	public void run(String... args) throws Exception {

		System.out.println(env.getProperty("spring.application.name"));
		System.out.println("using environment: " + myConfig.getEnvironment());

	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
