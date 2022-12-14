<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="New Game">
    <jsp:body>
        <h2>
            New game
        </h2>
        <form:form modelAttribute="game" class="form-horizontal">
            <input type="hidden" name="id" value="${game.id}">
            <input type="hidden" name="host" value="${user.id}">
            <div class="form-group has-feedback">
                <cluedo:selectField name = "isPrivate" label="Private" names="${privateList}" size="2"></cluedo:selectField>
                <cluedo:selectField name = "lobbySize" label="Number of players" names="${nPlayers}" size="4"></cluedo:selectField>
            </div>
            <div class="form-group">
                <button class="btn btn-default" type="submit">Add game</button>
            </div>
        </form:form>
    </jsp:body>
</cluedo:layout>