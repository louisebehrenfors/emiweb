package se.emiweb.search;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticClient {
	void Client()
	{
		System.out.println("TEST1");		
		
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		// on startup
		try {
			client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		}
		catch(IOException e){
			System.out.println("ElasticClient Error;");
			e.getLocalizedMessage();
		}
		
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		
		
		IndexResponse response = client.prepareIndex("twitter", "_doc")
		        .setSource(json, XContentType.JSON)
		        .get();
		
		System.out.println("TEST2");
		// on shutdown

		//client.close();
		
	}
}
