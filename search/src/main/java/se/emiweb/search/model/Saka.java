package se.emiweb.search.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import se.emiweb.search.service.IndexMap;

@Document(indexName="saka_index", type="saka_type")
public class Saka {
	
	@Id
	private String Page;
	private String LastName1,LastName2,FirstName,Gender,CivilStatus,BirthDate,BirthParish,BirthProvince,ImmigrationDate,EmigrationParish,EmigrationProvince,ArrivalDateThisPlace,ArrivedFromPlace,ArrivedFromCounty,ArrivedFromState,DeathDate,FamilyNr,Source,ImmigratedToPlace,ImmigratedToState,Id,BirthYear,BirthMonth,BirthDay,ImmigrationYear,ImmigrationMonth,ImmigrationDay,DeathYear,DeathMonth,DeathDay,OwnerId,LastModified;

	
	public static ArrayList<String> getSearchFields() {
		return new ArrayList<>(Arrays.asList("LastName", "LastName2", "FirstName", "Gender", "BirthDate"));
	}
	
	public static String getIndexName(){
		return "saka_index";
	}
}
