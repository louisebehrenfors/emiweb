package se.emiweb.search;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticClient 
{
	void Client()
	{
		System.out.println("TEST1");		
		
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		// on startup
		try 
		{
			client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		}
		catch(IOException e){
			System.out.println("ElasticClient Error;");
			e.getLocalizedMessage();
		}
		
		
		//Insert JSON to elasticsearch
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		
		String json1 = "{" +
		        "\"user\":\"Test\"," +
		        "\"postDate\":\"2022-01-30\"," +
		        "\"message\":\"Hello\"" +
		    "}";
		
		
		IndexResponse info = client.prepareIndex("twitter", "_doc", "1")
		        .setSource(json, XContentType.JSON)
		        .get();
		
		info = client.prepareIndex("twitter", "_doc", "2")
		        .setSource(json1, XContentType.JSON)
		        .get();
		
		System.out.println("TEST2");

		
		//Get from elastic search by 1 id at the time
		/*GetResponse response = client.prepareGet("twitter", "_doc", "1").get();		
		System.out.println(response);	
		response = client.prepareGet("twitter", "_doc", "2").get();	
		System.out.println(response);*/
		
		//Get from elastic search by multiple IDs
		/*MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
			    .add("twitter", "_doc", "1", "2")                    
			    .get();
		
		for (MultiGetItemResponse itemResponse : multiGetItemResponses)
		{ 
		    GetResponse multiResponse = itemResponse.getResponse();
		    if (multiResponse.isExists()) {                      
		        System.out.println(multiResponse.getSourceAsString()); 
		    }
		}*/
		
		//search query
		SearchResponse search_response = client.prepareSearch("twitter")
				.setPostFilter(QueryBuilders.matchQuery("user", "Test"))
				.get();
		
		System.out.println(search_response);
		
		// on shutdown
		client.close();
		
	}
}

