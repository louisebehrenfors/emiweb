package se.emiweb.search.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.service.Service;

@RestController
@RequestMapping("search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;
	
	
	
	private int pageNumber = 0;
	final int pageSize = 10;
	
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		String Name = "";
		
		
        ArrayList<String> notAllowedFields = new ArrayList<String>() { 
            { 
            	add("Id");
            	add("FileName");
            	add("LastModified");
            	add("OwnerID");
            	add("SourceCode");
            	
            } 
        }; 
		
        
        if(params.containsKey("page"))
        {
        	try {
        		pageNumber = Integer.parseInt(params.get("page"));  
        	}
        	catch(Exception e){
        		System.out.println(e);
        	}
        	 	
        }

        params.remove("page");
        
	
		
		if (params.containsKey("FirstName") || params.containsKey("LastName")) {
			if(params.containsKey("FirstName")) {
				Name += params.get("FirstName");
			}
			
			if(params.containsKey("LastName")) {
				if(Name != null) {
					Name += " ";
				}
				
				Name += params.get("LastName");
			}

			params.put("Name", Name);
		}
		
		String[] indexes = new String[]{"usmgbg_index", "larsson_pop_index"};
		return service.advanced(params, notAllowedFields, indexes, pageNumber);


	}

	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		
		Service service = new Service(client);

    	try {
    		pageNumber = Integer.parseInt(page);  
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}

       
        String[] fields = new String[]{"Profession", "Name" ,"FirstName", "LastName"};
        String[] indexes = new String[]{"usmgbg_index", "larsson_pop_index"};
        
		return service.likegoogle(search, fields, indexes, pageNumber);
		
		
	}
}
