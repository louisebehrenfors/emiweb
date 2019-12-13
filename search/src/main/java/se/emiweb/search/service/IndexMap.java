package se.emiweb.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.emiweb.search.model.Larsson_pop;
import se.emiweb.search.model.Usmgbg;

public class IndexMap {
	
	public static Map<String, ArrayList<String>>indexmap;
	
	static {
		indexmap = new HashMap<String, ArrayList<String>>();
		indexmap.put("usmgbg_index", Usmgbg.getSearchFields());
		indexmap.put("Larsson_pop_index", Larsson_pop.getSearchFields());
	}
	
	
}
