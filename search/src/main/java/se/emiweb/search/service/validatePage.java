package se.emiweb.search.service;

public class validatePage {
	public int check(String page) {
			
			int pageNumber;
			
	    	try {
	    		pageNumber = Integer.parseInt(page);  
	    	}
	    	catch(Exception e){
	    		System.out.println(e);
	    		return 0;
	    		
	    	}
			
			if(pageNumber >= 0) {
				return pageNumber;
			}
			else {
				return 0;
			}
			
	}
}
