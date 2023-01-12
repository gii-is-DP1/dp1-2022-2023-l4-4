<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Accusation">
    <jsp:body>
        <div class="container">
            <h2>
                Make your accusation!
                You are in the ${room.cardName}
            </h2>
            <div>
                <a  href="/games/${game.id}/play/accusations"  target="_blank">
                    <button class="btn btn-default" style="font-size:105%;">
                        Accusations
                    </button>
                </a>
                <a  href="/games/${game.id}/play/notes"  target="_blank">
                    <button class="btn btn-default" style="font-size:105%;">
                        Open New Notes
                    </button>
                </a>
            </div>
            <form:form  modelAttribute="accusation" class="form-horizontal">
                <input type="hidden" name="turn" value=${accusation.turn.id}>
                <input type="hidden" name="roomCard" value=${room.cardName}>
                <div class="form-group has-feedback" style="font-size:120%;padding-top: 5%;">
                    <cluedo:selectField name = "suspectCard" label="Suspect" names="${suspects}" size="6"></cluedo:selectField>
                    <cluedo:selectField name = "weaponCard" label="Weapons" names="${weapons}" size="6"></cluedo:selectField>
                </div>
                
                <div class="form-group">
                    <button class="btn btn-default" type="submit" style="margin-left: 10%;">Make Accusation</button>
                </div>
            </form:form>
            
        </div>
    </jsp:body>
</cluedo:layout>