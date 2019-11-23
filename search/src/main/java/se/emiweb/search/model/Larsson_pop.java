package se.emiweb.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="larsson_pop_index", type="larsson_pop_type")
public class Larsson_pop {

	@Id
	private String ID;
	private String Source, Name, Profession, Country, FileName, LastModified, OwnerID;
}
