package se.emiweb.search.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	//Need configuration
	//Talk about how field should prioritize
	@GetMapping("/allfields/{text}")
	public SearchResponse findByAllField(@PathVariable String text) {

		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("Id")
						.field("FirstName")
						.field("LastName")
						.field("Profession")
						.field("HomeLocation")
						.field("HomeParish")
						.field("HomeProvice")
						).should(QueryBuilders.queryStringQuery("*"+text+"*")); //If first letter match, that should be more important than if match is in middle of word
		
		SearchResponse response = client.prepareSearch("larsson_pop_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(0).setSize(100).setExplain(true)			//return max 100 results
		        .get();	
		
		return response;
		
	}
}
