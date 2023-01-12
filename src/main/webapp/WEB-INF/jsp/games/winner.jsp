<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="On Game">

    <c:if test="${game.winner!=null}">
        <c:if test="${game.winner==loggedUser}">
            <h2>CONGRATULATIONS! YOU WIN! </h2>
        </c:if>
        <c:if test="${game.winner!=loggedUser}">
            <h2>YOU LOSE! THE WINNER WAS ${game.winner.username}</h2>
        </c:if>
    </c:if>
    <c:if test="${game.winner==null}">
        <h2>THE GAME FINISH WITH A DRAW!</h2>
    </c:if>
</cluedo:layout>