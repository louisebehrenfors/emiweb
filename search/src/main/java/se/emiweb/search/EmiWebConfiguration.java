package se.emiweb.search;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages = "se.emiweb.search.repository")
public class EmiWebConfiguration {
	
	@Bean
	public Client client() throws UnknownHostException {
		
		
		Settings settings = Settings.builder()
		        .put("cluster.name", "EmiWeb_Cluster")
		        .put("client.transport.sniff", true)
		        .put("node.name", "EmiWeb")
		        .build();
		
		TransportClient client = new PreBuiltTransportClient(settings)
		        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		
		
		
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		
		IndexResponse info = client.prepareIndex("usmgbg_index", "usmgbg_type", "1")
		        .setSource(json, XContentType.JSON)
		        .get();
		


		return client;
	}
}
