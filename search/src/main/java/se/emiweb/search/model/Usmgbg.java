package se.emiweb.search.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="usmgbg_index", type="usmgbg_type")
public class Usmgbg {

	@Id
	private String ID;
	private String Source, Name, Profession, Country, FileName, LastModified, OwnerID;
	
	public static ArrayList<String> getSearchFields() {
		String[] fields = new String[] {"Source", "Name", "Profession", "Country"};
		return new ArrayList<String>(Arrays.asList(fields));
	}
	
	public static ArrayList<String> getNameFields(){
		String[] name_fields = new String[] { "Name" };	
		return new ArrayList<String>(Arrays.asList(name_fields));
	}
	
	
}
