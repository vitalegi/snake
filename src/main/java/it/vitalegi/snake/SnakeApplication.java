package it.vitalegi.snake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnakeApplication {

	static {
		System.setProperty("java.awt.headless", String.valueOf(false));
		LoggerFactory.getLogger(SnakeApplication.class).info("Headless? {}", System.getProperty("java.awt.headless"));
	}

	public static void main(String[] args) {

		SpringApplication.run(SnakeApplication.class, args);
	}

	static Logger log = LoggerFactory.getLogger(SnakeApplication.class);
}
