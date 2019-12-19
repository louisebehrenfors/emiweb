package se.emiweb.search.controller;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.emiweb.search.service.IndexMap;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("search/specific/")
public class SpecificController {
	
	@Autowired
	Client client;

	private int pageNumber = 0;
	
	@CrossOrigin
	@GetMapping("{index}/advanced")
	public SearchHits advancedSearch(@PathVariable String index, @RequestParam(required = false) Map<String, String> params) {

		Service service = new Service(client);
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
        }
		
		for (Entry<String, ArrayList<String>> entry : IndexMap.IndexMap.entrySet()) {
		    query = service.advanced(params, entry.getValue(), query);
		}
        
		return service.executeQuery(query, pageNumber, index);
	}
	
	@CrossOrigin
	@GetMapping("{index}/likegoogle")
	public SearchHits findByAllIndexes( @PathVariable String index,
										@RequestParam(required = false) String search,
										@RequestParam(defaultValue = "0") String page	) {
		
		Service service = new Service(client);
		pageNumber = new validatePage().check(page);     
		return service.likegoogle(search, IndexMap.IndexMap.get(index), pageNumber, index);	
	}
	
	@CrossOrigin
	@GetMapping("{index}/byid")
	public SearchHits findById( @PathVariable String index,
								@RequestParam(required = true) String Id) throws ClassNotFoundException{
		Service service = new Service(client);
		return service.getById(Id, index);
	}
}
