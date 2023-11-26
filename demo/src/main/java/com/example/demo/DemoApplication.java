package com.example.demo;

import com.example.demo.keystores.KeyStoreGenerate;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication()
public class DemoApplication {

	private static ApplicationContext context;
	private static KeyStoreGenerate keyStoreGenerate;


	public static void main(String[] args) {

		context = (ApplicationContext) SpringApplication.run(DemoApplication.class, args);
		keyStoreGenerate = (KeyStoreGenerate) context.getBean("keyStoreGenerate");
		keyStoreGenerate.GenerateInitialData();

	}
}
