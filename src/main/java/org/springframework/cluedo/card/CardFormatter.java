package org.springframework.cluedo.card;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.cluedo.enumerates.CardName;

@Component
public class CardFormatter implements Formatter<Card>{

    private final CardService cardService;

	@Autowired
	public CardFormatter(CardService cardService) {
		this.cardService = cardService;
	}

	public String print(Card card,  Locale locale) {
		return card.getCardName().toString();
	}

	@Override
	public Card parse(String text, Locale locale) throws ParseException{
			try {
				return this.cardService.getCardByCardName(CardName.valueOf(text)); 
			}  catch (Exception e) {
				throw new ParseException("Card not found: " + text, 0);
			}			 
    } 
}
