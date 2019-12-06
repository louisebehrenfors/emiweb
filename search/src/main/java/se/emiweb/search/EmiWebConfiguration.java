package se.emiweb.search;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
		
		
		return client;
	}
	
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}
