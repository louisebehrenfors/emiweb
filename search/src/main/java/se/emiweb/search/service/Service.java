package se.emiweb.search.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;


public class Service {
	Client client;
	
	public Service(Client client) {
		this.client = client;
		
	
		
	}
	
	public SearchHits executeQuery(QueryBuilder query, int page, String... indexes) {
		int pageSize = 10;
		
		SearchResponse response = client.prepareSearch(indexes)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)
		        .setFrom(page*pageSize).setSize(pageSize).setExplain(true)
		        .get();	
		System.out.println(query.toString());
		return response.getHits();
		
	}
	
	public SearchHits likegoogle(String search, ArrayList<String>  fields, int page, String... indexes) {
		
		fields.remove("BirthDate");
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
        

		
		return executeQuery(query, page, indexes);
	}
	
	
	
	public BoolQueryBuilder advanced(Map<String, String> params, ArrayList<String> allowedFields, BoolQueryBuilder query) {
		
		
	
		BoolQueryBuilder local_query = QueryBuilders.boolQuery();
		/*
		String BirthDay = "";
		if (params.containsKey("BirthDate") && allowedFields.contains("BirthDate")){
			BirthDay = params.get("BirthDate");
			
			
			params.remove("BirthDate");	
			
			local_query.must(QueryBuilders.rangeQuery("BirthDate")
					.from("1851-05-17||/D")
					.to("1851-05-19||/D")
					.includeLower(true)
					.includeLower(false));

		}
		else if(params.containsKey("BirthDate") && !allowedFields.contains("BirthDate")){
			params.remove("BirthDate");	
		}
		*/
		

		
		for(Map.Entry<String, String> entry : params.entrySet())
		{
			if(allowedFields.contains(entry.getKey())) {

				
				String field = entry.getKey();
				String value = entry.getValue();
				
				local_query.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery(field, value).fuzziness("AUTO"))
                        								  .should(QueryBuilders.matchQuery(field, value).boost(1.5f)));
				
			}

		}
		
		
		
		return query.should(local_query);

		
	}
	
	public SearchHits getById(String id, String index) {
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.matchQuery("Id", id));
		return executeQuery(query, 0, index);
	}
	
}
