package se.emiweb.search.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;
	
	@CrossOrigin
	@GetMapping("/{text}")
	public SearchResponse findByAllIndexes(@PathVariable String text) {
		
		/*
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("Id")
						.field("Name").boost(4)
						.field("FirstName")
						.field("LastName")
						).should(QueryBuilders.queryStringQuery("*"+text+"*"));
						
		*/
		
		/*
		 QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.queryStringQuery(text)
				.lenient(true)
				.field("Name")
				).should(QueryBuilders.queryStringQuery("*"+text+"*"))
		.should(QueryBuilders.queryStringQuery(text)
				.defaultOperator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
				.lenient(true)
				.field("FirstName")
				.field("LastName")
				).should(QueryBuilders.queryStringQuery("*"+text+"*"));
		 
		 */
		/*		QueryBuilder query = QueryBuilders.matchQuery("Name", name)
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.AND)
				.fuzziness("AUTO");
		*/
		/*
  		QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.multiMatchQuery(text, "Name", "Profession")
				.fuzziness("AUTO")
				).should(QueryBuilders.queryStringQuery("*"+text+"*"))
		.should(QueryBuilders.multiMatchQuery(text, "FirstName", "LastName", "Profession")
				.type("cross_fields")
				.fuzziness("AUTO")
				).should(QueryBuilders.queryStringQuery("*"+text+"*"));
		*/
  		
		
		 QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.queryStringQuery(text)
				.defaultOperator(MatchQueryBuilder.DEFAULT_OPERATOR.AND)
				.lenient(true)
				.field("Name").boost(5)
				.field("FirstName").boost(5)
				.field("LastName").boost(10)
				).should(QueryBuilders.multiMatchQuery(text, "Profession", "Country").fuzziness("AUTO")).should(QueryBuilders.queryStringQuery("*"+text+"*"));
		

		
		SearchResponse response = client.prepareSearch("usmgbg_index", "larsson_pop_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(0).setSize(10).setExplain(true)		//return max 100 results
		        .get();	
			
		
		return response;
	}
}