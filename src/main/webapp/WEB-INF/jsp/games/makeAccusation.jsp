<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Throw Dices">
    <jsp:body>
        <div class="container">
            <form:form  modelAttribute="accusation" class="form-horizontal">
                <input type="hidden" name="turn" value=${accusation.turn.id}>
                <input type="hidden" name="roomCard" value=${room.cardName}>
                <div class="form-group has-feedback" style="font-size:120%">
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