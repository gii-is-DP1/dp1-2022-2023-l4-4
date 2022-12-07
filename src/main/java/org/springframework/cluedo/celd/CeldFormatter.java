package org.springframework.cluedo.celd;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.exceptions.DataNotFound;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class CeldFormatter implements Formatter<Celd>{

    private final CeldService celdService;

	@Autowired
	public CeldFormatter(CeldService celdService) {
		this.celdService = celdService;
	}

	
	public String print(Celd celd, Locale locale) {
		return celd.getId().toString();
	}

	
	public Celd parse(String text, Locale locale) throws ParseException{
		System.out.println("EL TEXTO --------------------->"+text);
			try {
				Celd res = this.celdService.getById(Integer.valueOf(text));
				return res;	
			}  catch (DataNotFound e) {
				throw new ParseException("Celd not found: " + text, 0);
			}
					
    }
}
