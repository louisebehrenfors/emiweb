package se.emiweb.search;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import se.emiweb.search.service.IndexMap;




@SpringBootApplication
@ComponentScan(basePackages = {"se.emiweb.search"})
public class SearchApplication {

	public static void main(String[] args) {

		SpringApplication.run(SearchApplication.class, args);
		
		

		
		

	}

}
