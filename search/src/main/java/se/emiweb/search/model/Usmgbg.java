package se.emiweb.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(indexName="usmgbg_index", type="usmgbg_type")
public class Usmgbg {

	@Id
	private String ID;
	private String Source, Name, Profession, Country, FileName, LastModified, OwnerID;
	
	
}
