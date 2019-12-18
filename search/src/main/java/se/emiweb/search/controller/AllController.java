package se.emiweb.search.controller;


import java.util.ArrayList;

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

import se.emiweb.search.service.IndexMap;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("search/allindexes")
public class AllController {
	
	@Autowired
	Client client;

	
	private int pageNumber = 0;
	
	//private static ArrayList<String> allFields = new Generator().generateFieldList();
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
		
		for (Entry<String, ArrayList<String>> entry : IndexMap.IndexMap.entrySet()) {
		    query = service.advanced(params, entry.getValue(), query);
		}
        
		String [] indexes = {"usmgbg_index", "larsson_pop_index"};
		return service.executeQuery(query, pageNumber, indexes);
	}

	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		
		Service service = new Service(client);
		pageNumber = new validatePage().check(page);     
		return service.likegoogle(search, new IndexMap().getAllFields(), pageNumber, IndexMap.IndexMap.keySet().toArray(new String[0]));	
	}
	
	
	
	
	
}
