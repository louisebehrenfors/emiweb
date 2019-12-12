package se.emiweb.search.service;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;

import se.emiweb.search.model.Larsson_pop;

public class Larsson_popService {
	public BoolQueryBuilder build(BoolQueryBuilder query, Map<String, String> params)
	{
		Map<String, String> temp_map = new HashMap<String, String>();
		temp_map.putAll(params);
		
		if(temp_map.containsKey("Location"))
		{
			temp_map.put("HomeProvince", temp_map.get("Location"));
			temp_map.put("HomeLocation", temp_map.get("Location"));
			temp_map.remove("Location");
		}
		
		return new Service().advanced(temp_map, Larsson_pop.getSearchFields(), query);
	}
}
