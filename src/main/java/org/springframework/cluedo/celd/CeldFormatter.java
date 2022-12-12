package org.springframework.cluedo.celd;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cluedo.enumerates.CeldType;
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
		switch(celd.getCeldType()){
			case CORRIDOR:	return celd.getId().toString();
			case DINNINGHALL: return "DINNING HALL"; 
			case GUESTROOM: return "GUEST ROOM";
			case LIVINGROOM: return "LIVING ROOM";
			default: return celd.getCeldType().toString();
		}
	}

	@Override
	public Celd parse(String text, Locale locale) throws ParseException,NumberFormatException{
			try {
				Integer i=Integer.parseInt(text);
				return this.celdService.getById(i); 
			}  catch (DataNotFound e) {
				throw new ParseException("Celd not found: " + text, 0);
			}	catch(NumberFormatException e){ 
				System.out.println("ASI SE QUEDA:----->"+text.replaceAll(" ", ""));
				return this.celdService.getByCeldType(CeldType.valueOf(text.replaceAll(" ", "")));
			}
					 
    } 

	public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
}
