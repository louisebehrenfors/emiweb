package se.emiweb.search.controller;


import java.util.ArrayList;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.service.Generator;
import se.emiweb.search.service.NameExtractor;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;
	
	private int pageNumber = 0;
	final int pageSize = 10;
	NameExtractor nameExtractor = new NameExtractor();
	
	public static ArrayList<String> allFields = new Generator().generateFieldList();
	static String[] allIndexes = new Generator().generateIndexList();
	
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		nameExtractor.insertNameToMap(params);
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
		
		return service.advanced(params, allFields, allIndexes, pageNumber);
	}

	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		

		Service service = new Service(client);

		pageNumber = new validatePage().check(page);
    	        
		return service.likegoogle(search, allFields, allIndexes, pageNumber);	
	}
	
	
}
