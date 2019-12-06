package se.emiweb.search.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import se.emiweb.search.model.Larsson_pop;
import se.emiweb.search.model.Usmgbg;
import se.emiweb.search.repository.Larsson_popRepository;
import se.emiweb.search.repository.UsmgbgRepository;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;
import se.emiweb.search.EmiWebConfiguration;
import se.emiweb.search.service.Generator;
import se.emiweb.search.service.Service;

@RestController
@RequestMapping("/search/larsson_pop")
public class Larrsson_popController {
	
	@Autowired
	Larsson_popRepository repository;
	
	@Autowired
	Client client;
	
	private  ElasticsearchOperations elasticsearchOperations;
	int pageNumber = 0;
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		int pageNumber = 0;
		
		String Name = "";
	       
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
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
		
		return service.advanced(params, Larsson_pop.getSearchFields(), new String[]{"larsson_pop_index"} , pageNumber);
	}
	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		

		Service service = new Service(client);

		pageNumber = new validatePage().check(page);
    	        
		return service.likegoogle(search, Larsson_pop.getSearchFields(), new String[]{"larsson_pop_index"}, pageNumber);	
	}
}
