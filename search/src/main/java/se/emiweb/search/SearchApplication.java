package se.emiweb.search;

import org.springframework.boot.SpringApplication;
import java.lang.System;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

	public static void main(String[] args) {
		System.out.println("Hello World! :D");
		SpringApplication.run(SearchApplication.class, args);
	}

}
