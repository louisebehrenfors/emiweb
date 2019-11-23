package se.emiweb.search.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.repository.Larsson_popRepository;

@RestController
@RequestMapping("/rest/search/larsson_pop")
public class Larrsson_popController {
	
	@Autowired
	Larsson_popRepository repository;
	
	@Autowired
	Client client;
	
	@GetMapping("/all")
	public SearchResponse findAll() {
		
		QueryBuilder query = QueryBuilders.matchAllQuery();
				
		SearchResponse search_response = client.prepareSearch("larsson_pop_index")
				.setPostFilter(query)
				.get();
		
		return search_response;
	} 
}
