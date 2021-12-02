package br.com.ufrj.engsoft.turma37.bigdata.util.file.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetValidator implements Validator{

	   private Pattern pattern;
	   private Matcher matcher;
	 
	   private static final String PATTERN = "([^\\s]+(\\.(?i)(xls|xlsx|csv))$)";
  	   		        
	   public SheetValidator(){
		  pattern = Pattern.compile(PATTERN);
	   }
		  
	   /**
	   * Validate image with regular expression
	   * @param image image for validation
	   * @return true valid image, false invalid image
	   */
	   public boolean validate(final String image){
			  
		  matcher = pattern.matcher(image);
		  return matcher.matches();
		    	    
	   }
	
}
