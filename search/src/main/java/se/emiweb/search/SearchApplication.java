package se.emiweb.search;

import org.springframework.boot.SpringApplication;
import java.lang.System;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"se.emiweb.search"})
public class SearchApplication {

	public static void main(String[] args) {

		SpringApplication.run(SearchApplication.class, args);
		new ElasticClient().Client();
	}

}
