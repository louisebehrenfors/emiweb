package se.emiweb.search.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.emiweb.search.repository.UsmgbgRepository;

@RestController
@RequestMapping("/rest/search")
public class UsmgbgController {
	@Autowired
	UsmgbgRepository repository;
	
	@Autowired
	Client client;

	

	@GetMapping("/all")
	public SearchResponse findAll() {
		SearchResponse search_response = client.prepareSearch("usmgbg_index")
				.setPostFilter(QueryBuilders.matchAllQuery())
				.get();
		
		return search_response;
	} 
	
	@GetMapping("/{name}")
	public SearchResponse searchName(@PathVariable("name") String name) {
		
		SearchResponse search_response = client.prepareSearch("usmgbg_index")
		.setPostFilter(QueryBuilders.matchQuery("Name", name))
		.get();
		

		return search_response;
	}
}