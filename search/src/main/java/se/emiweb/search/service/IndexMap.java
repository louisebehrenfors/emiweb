package se.emiweb.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import se.emiweb.search.model.*;

public class IndexMap {	
	public static Map<String, ArrayList<String>> IndexMap;
	
	static 
	{
		IndexMap = new HashMap<String, ArrayList<String>>();
		IndexMap.put(Larsson_pop.getIndexName(),Larsson_pop.getSearchFields());
		IndexMap.put(Saka.getIndexName(),Saka.getSearchFields());
		IndexMap.put(Usmgbg.getIndexName(),Usmgbg.getSearchFields());
	}
	
	public ArrayList<String> getAllFields()
	{
		ArrayList<String> fields = new ArrayList<String>();
		for (Entry<String, ArrayList<String>> entry : IndexMap.entrySet()) {
		    fields.addAll(entry.getValue());
		}		
		return fields;
	}
}
