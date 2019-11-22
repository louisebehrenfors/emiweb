package se.emiweb.search.controller;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import se.emiweb.search.model.Usmgbg;
import se.emiweb.search.repository.UsmgbgRepository;
import se.emiweb.search.EmiWebConfiguration;

@RestController
@RequestMapping("/rest/search/usmgbg")

public class UsmgbgController {
	
	@Autowired
	UsmgbgRepository repository;
	
	@Autowired
	Client client;
	
	private  ElasticsearchOperations elasticsearchOperations;

	@GetMapping("/all")
	public SearchResponse findAll() {
		
		QueryBuilder query = QueryBuilders.matchAllQuery();
				
		SearchResponse search_response = client.prepareSearch("usmgbg_index")
				.setPostFilter(query)
				.get();
		
		return search_response;
	} 
