package se.emiweb.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search/allindexes")
public class allIndexes {
	
	@Autowired
	Client client;
	private int pageNumber = 0;
	final int pageSize = 10;
	
	
	
	
	@CrossOrigin
	@GetMapping("/advanced")
	public SearchHits advancedSearch(@RequestParam(required = false) Map<String, String> params) {
		
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		String Name = "";
		
		boolean isValidQuery = false;
		
		
		
		
        ArrayList<String> notAllowedFields = new ArrayList<String>() { 
            { 
            	add("Id");
            	add("FileName");
            	add("LastModified");
            	add("OwnerID");
            	add("SourceCode");
            	
            } 
        }; 
		
        
        if(params.containsKey("page"))
        {
        	try {
        		pageNumber = Integer.parseInt(params.get("page"));  
        	}
        	catch(Exception e){
        		System.out.println(e);
        	}
        	 	
        }

        
        
        params.remove("page");
        
	
		
		if (params.containsKey("FirstName") || params.containsKey("LastName")) {
			if(params.containsKey("FirstName")) {
				Name += params.get("FirstName");
			}
			
			if(params.containsKey("LastName")) {
				if(Name != null) {
					Name += " ";
				}
				
				Name += params.get("LastName");
			}

			params.put("Name", Name);
		}
		
		
				
		

		for(Map.Entry<String, String> entry : params.entrySet())
		{
			if(!notAllowedFields.contains(entry.getKey())) {
				
				isValidQuery = true;
				
				String field = entry.getKey();
				String value = entry.getValue();

				
				query.should(QueryBuilders.matchQuery(field, value).fuzziness("AUTO"));	
			}

			

		}
		
		if(!isValidQuery) {
			return null;
		}
		

		SearchResponse response = client.prepareSearch("usmgbg_index", "larsson_pop_index")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(pageNumber*pageSize).setSize(pageSize).setExplain(true)
		        .get();	
			
		
		
		return response.getHits();
	}

	
	@CrossOrigin
	@GetMapping("/likegoogle")
	public SearchHits findByAllIndexes( @RequestParam(required = false) String search,
											@RequestParam(defaultValue = "0") String page	) {
		

    	try {
    		pageNumber = Integer.parseInt(page);  
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
		/*
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("Name", 10)
						.field("FirstName")
						.field("LastName")
						.field("Profession")
						
						);
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
		
		/*
 		QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.queryStringQuery("*"+text+"*")
				.field("Name")
				.field("Profession")
				
				);
		
		 */
		//SPLIT SEARCHWORD INTO SEPERATE WORDS???
		//Case sensetive
		/*
		 System.out.println(text);
		 QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.multiMatchQuery(text, "Profession", "Name" ,"FirstName", "LastName")
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
				.fuzziness("AUTO"));
		 
		 		System.out.println(text);
		 QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.multiMatchQuery(text, "Profession", "Name", "FirstName", "LastName")
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
				.fuzziness("AUTO"))
		.should(QueryBuilders.multiMatchQuery(text, "FirstName", "LastName")
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.AND)
				.fuzziness("AUTO")	
				.type("cross_fields"))
		 */
		 //alla ord tolkas för sig, separeas med ' ', gör en querystringquery för FirstName och Lastname med cross fields för att söka dem som ett fält??

		QueryBuilder query = QueryBuilders.boolQuery()
		.should(QueryBuilders.multiMatchQuery(search, "Profession", "Name" ,"FirstName", "LastName")
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
				.fuzziness("AUTO")
				.type("most_fields"))
		.should(QueryBuilders.multiMatchQuery(search, "Profession", "Name" ,"FirstName", "LastName")
				.operator(MatchQueryBuilder.DEFAULT_OPERATOR.OR)
				.type("most_fields")
				
				);
		 

			
		SearchResponse response = client.prepareSearch("usmgbg_index", "larsson_pop_index") //, "larsson_pop_index"
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(query)	//term to match
		        .setFrom(pageNumber*pageSize).setSize(pageSize).setExplain(true)		//return max 100 results
		        .get();	
			
		
		return response.getHits();
	}
}
