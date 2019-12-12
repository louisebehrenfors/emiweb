package se.emiweb.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import se.emiweb.search.model.Usmgbg;

import org.elasticsearch.index.query.BoolQueryBuilder;

public class Usmgbg_service {
	public BoolQueryBuilder build(BoolQueryBuilder query, Map<String, String> params)
	{
		Map<String, String> temp_map = new HashMap<String, String>();
		temp_map.putAll(params);
		
		convertName(temp_map);
		System.out.println("temp " + temp_map.keySet());
		
		return new Service().advanced(temp_map, Usmgbg.getSearchFields(), query);
	}
	
	private void convertName(Map<String, String> temp_map) {
			
			String Name = "";
			
			if (temp_map.containsKey("FirstName") || temp_map.containsKey("LastName")) {
				if(temp_map.containsKey("FirstName")) {
					Name += temp_map.get("FirstName");
					temp_map.remove("FirstName");
				}
				
				if(temp_map.containsKey("LastName")) {
					if(Name != null) {
						Name += " ";
					}
					
					Name += temp_map.get("LastName");
					temp_map.remove("LastName");
				}
						
				temp_map.put("Name", Name);
			}
		}
}
