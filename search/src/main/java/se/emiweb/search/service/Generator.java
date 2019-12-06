package se.emiweb.search.service;

import java.util.ArrayList;

import se.emiweb.search.model.Larsson_pop;
import se.emiweb.search.model.Usmgbg;

public class Generator {
	
	
	public ArrayList<String> generateFieldList() {
		ArrayList<String> all_fields = new ArrayList<String>();
		
		join(all_fields, Usmgbg.getSearchFields());
		join(all_fields, Larsson_pop.getSearchFields());
		
		
		
		return all_fields;
	}
	
	private void join(ArrayList<String> dest, ArrayList<String> source) {
		for(String field : source) {
			if(!dest.contains(field)) {
				dest.add(field);
			}
		}
	}
	
	public String[] generateIndexList(){
		return new String[]{"usmgbg_index", "larsson_pop_index"};
	}
}
