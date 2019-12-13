package se.emiweb.search.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.service.Generator;
import se.emiweb.search.service.IndexMap;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;

	
	private int pageNumber = 0;
	
	private static ArrayList<String> allFields = new Generator().generateFieldList();
	private static String[] allIndexes = new Generator().generateIndexList();
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
		
		for (Entry<String, ArrayList<String>> entry : IndexMap.indexmap.entrySet()) {
		    query = service.advanced(params, entry.getValue(), query);
			
			System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
        
		String [] indexes = {"usmgbg_index", "larsson_pop_index"};
		return service.executeQuery(query, indexes,  pageNumber);
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
