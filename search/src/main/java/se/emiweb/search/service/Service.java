package se.emiweb.search.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;


public class Service {
	Client client;
	
	public Service(Client client) {
		this.client = client;
		
	
		
	}
	
	public SearchHits executeQuery(QueryBuilder query, String[] indexes, int page) {
		int pageSize = 10;
		
		SearchResponse response = client.prepareSearch(indexes)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)
		        .setFrom(page*pageSize).setSize(pageSize).setExplain(true)
		        .get();	
		
		return response.getHits();
		
	}
	
	public SearchHits likegoogle(String search, ArrayList<String>  fields, String[] indexes, int page) {
		String[] fieldsAsArray = fields.toArray(new String[fields.size()]);
		
        QueryBuilder query = QueryBuilders.boolQuery()
        		.should(QueryBuilders.multiMatchQuery(search, fieldsAsArray)
        				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
        				.field("FirstName", 1.4f)
        				.field("LastName", 1.4f)
        				.fuzziness("AUTO")
        				.type("most_fields"))
        		.should(QueryBuilders.multiMatchQuery(search, fieldsAsArray)
        				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
        				.field("FirstName", 1.4f)
        				.field("LastName", 1.4f)
        				.type("most_fields")
        				.boost(2));
        

		
		return executeQuery(query, indexes, page);
	}
	
	
	
	public BoolQueryBuilder advanced(Map<String, String> params, ArrayList<String> allowedFields, BoolQueryBuilder query) {
		

		BoolQueryBuilder local_query = QueryBuilders.boolQuery();
		
		for(Map.Entry<String, String> entry : params.entrySet())
		{
			if(allowedFields.contains(entry.getKey())) {

				
				String field = entry.getKey();
				String value = entry.getValue();
				
				System.out.println(value + " " + field);
				
				local_query.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(field, value).fuzziness("AUTO"))
                        								  .should(QueryBuilders.matchQuery(field, value).boost(1.5f)));
				
			}
			
		
		
		}
		

	
			return query.should(local_query);

		
	}
	
	
	/*
		public SearchHits advanced(Map<String, String> params, ArrayList<String> allowedFields, String[] indexes,  int page) {
		
		boolean isValidQuery = false;
		
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
		for(Map.Entry<String, String> entry : params.entrySet())
		{
			if(allowedFields.contains(entry.getKey())) {
				
				isValidQuery = true;
				
				String field = entry.getKey();
				String value = entry.getValue();
				
				System.out.println(value + " " + field);
				
				query.should(QueryBuilders.matchQuery(field, value).fuzziness("AUTO"));	
				
			}
		
		}
		

		
		
		if(isValidQuery)
			return executeQuery(query, indexes, page);
		else
			return null;

		
	}
	 
	*/
	
}