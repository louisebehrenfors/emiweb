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

	@GetMapping("/byexactname/{name}")
	public SearchResponse findByExactName(@PathVariable String name) {
		
		QueryBuilder query = QueryBuilders.matchQuery("Name", name)
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.AND);
		
		SearchResponse response = client.prepareSearch("usmgbg_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(0).setSize(100).setExplain(true)			//return max 100 results
		        .get();	
		
		return response;
	}
	
	@GetMapping("/byfuzzyname/{name}")
	public SearchResponse findByFuzzyName(@PathVariable String name) {
		
		QueryBuilder query = QueryBuilders.matchQuery("Name", name)
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.AND)
				.fuzziness("AUTO");
		
		SearchResponse response = client.prepareSearch("usmgbg_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(0).setSize(100).setExplain(true)			//return max 100 results
		        .get();	
		
		return response;

	}
	
	@GetMapping("/byid/{id}")
	public SearchResponse findById(@PathVariable String id) {
		
		QueryBuilder query = QueryBuilders.matchQuery("ID", id );
		
		SearchResponse response = client.prepareSearch("usmgbg_index")
				.setQuery(query)
				.setFrom(0).setSize(100).setExplain(true)
				.get();
		
		return response;
	}
	
	
	@GetMapping("/allfields/{text}")
	public SearchResponse findByAllField(@PathVariable String text) {
		
		
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("ID")
						.field("Name")
						.field("Profession")
						.field("Country")
						).should(QueryBuilders.queryStringQuery("*"+text+"*"));
		
		SearchResponse response = client.prepareSearch("usmgbg_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(0).setSize(100).setExplain(true)			//return max 100 results
		        .get();	
		
		return response;
		
	}

}