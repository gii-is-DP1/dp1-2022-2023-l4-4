<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Accusation">
    <jsp:body>
        <div class="container">
            
            <h2>Make your FINAL ACCUSATION</h2>
            <div>
                <h3 style="padding-top: 3%;">
                    View all accusations made
                </h3>
                <a  href="/games/${game.id}/play/accusations"  target="_blank">
                    <button class="btn btn-default" style="font-size:105%;">
                        Accusations
                    </button>
                </a>
                <a  href="/games/${game.id}/play/finish">
                    <button class="btn btn-default" style="font-size:105%;">
                        Go back
                    </button>
                </a>
            </div>
            <form:form  modelAttribute="finalAccusation" class="form-horizontal">
                <input type="hidden" name="turn" value=${finalAccusation.turn.id}>
                <div class="form-group has-feedback" style="font-size:120%;padding-top: 3%;">
                    <cluedo:selectField name = "roomCard" label="Room" names="${rooms}" size="6"></cluedo:selectField>
                    <cluedo:selectField name = "suspectCard" label="Suspect" names="${suspects}" size="6"></cluedo:selectField>
                    <cluedo:selectField name = "weaponCard" label="Weapons" names="${weapons}" size="6"></cluedo:selectField>
                </div>
                <div class="form-group">
                    <button class="btn btn-default" type="submit">Make Accusation</button>
                </div>
            </form:form>
        </div>
    </jsp:body>
</cluedo:layout>