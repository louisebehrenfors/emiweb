package se.emiweb.search.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.TransportAddress;

import se.emiweb.search.EmiWebConfiguration;

public class Index_Names{
	
	public static String[] indices;
	
	static {
		try {
			indices = new EmiWebConfiguration().client().admin().cluster().prepareState() 
					    .execute().actionGet().getState().getMetaData().getConcreteAllIndices();
		} catch (Exception e) {
			
		}
	}
}
