package se.emiweb.search.controller;


import java.util.ArrayList;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
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
import se.emiweb.search.service.NameExtractor;
import se.emiweb.search.service.*;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;
	
	private int pageNumber = 0;
	private int pageSize = 10;
	
	private static ArrayList<String> allFields = new Generator().generateFieldList();
	private static String[] allIndexes = new Generator().generateIndexList();
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
        
        System.out.println("1 " + params.keySet());
        query = new Usmgbg_service().build(query, params);
        System.out.println("2 " + params.keySet());
        query = new Larsson_popService().build(query, params);
        System.out.println("3 " + params.keySet());
        
        System.out.println("LAST qury");
        System.out.println(query.toString());
        
        SearchResponse response = client.prepareSearch("larsson_pop_index", "usmgbg_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)
		        .setFrom(pageNumber*pageSize).setSize(pageSize).setExplain(true)
		        .get();	

		return response.getHits();
		
	/*	Service service = new Service(client);
		nameExtractor.insertNameToMap(params);
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
		
		return service.advanced(params, allFields, allIndexes, pageNumber);*/
		
		//return null;
	}
//dfsd
	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		
		Service service = new Service(client);
		pageNumber = new validatePage().check(page);     
		return service.likegoogle(search, allFields, allIndexes, pageNumber);
	}
	
	
}
