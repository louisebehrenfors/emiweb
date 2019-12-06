package se.emiweb.search.service;

import java.util.Map;

public class NameExtractor {
	
	public String insertNameToMap(Map<String, String> params) {
		
		String Name = "";
		
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
		
		return Name;
	}
}
