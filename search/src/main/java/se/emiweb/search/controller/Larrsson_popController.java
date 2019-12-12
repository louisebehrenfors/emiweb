/*package se.emiweb.search.controller;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.model.Larsson_pop;
import se.emiweb.search.repository.Larsson_popRepository;
import se.emiweb.search.service.Service;
import se.emiweb.search.service.validatePage;

@RestController
@RequestMapping("/search/larsson_pop")
public class Larrsson_popController {
	
	@Autowired
	Larsson_popRepository repository;
	
	@Autowired
	Client client;
	
	int pageNumber = 0;
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		Service service = new Service(client);
		int pageNumber = 0;
	
	       
        if(params.containsKey("page")){
        	pageNumber = new validatePage().check(params.get("page"));
        	params.remove("page");
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
}*/
