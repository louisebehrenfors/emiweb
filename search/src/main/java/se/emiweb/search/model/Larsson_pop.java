package se.emiweb.search.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="larsson_pop_index", type="larsson_pop_type")
public class Larsson_pop {

	@Id
	private String Id;
	private String LastName, FirstName, Profession, HomeLocation, HomeParish, HomeProvince, SourceCode, LetterDate, Notes, LastModified, OwnerID;
		
	public static ArrayList<String> getSearchFields() {
		String[] fields = new String[] {"LastName", "FirstName", "Profession", "HomeLocation", "HomeParish", "HomeProvince", "LetterDate", "Notes"};
		return new ArrayList<String>(Arrays.asList(fields));
	}
	
}
