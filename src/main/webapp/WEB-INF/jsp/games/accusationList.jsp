<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Accusation List">
    <a href="/games/${game.id}/play/accusations">
        <button class="btn btn-default" style="font-size: 105%">
        Refresh
        </button>
    </a>
    <br></br>
    <table id="gameTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Turn</th>
            <th style="width: 200px;">Suspect card</th>
            <th style="width: 120px">Weapon card</th>
            <th style="width: 120px">Room card</th>
            <th style="width: 120px">Player who makes</th>
            <th style="width: 120px">Player who shown</th>
            <th style="width: 120px">Shown Card</th>
            
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${accusations}" var="acc">
            <tr>
                <td>
                    <c:out value="${acc.turn.id}"/>
                </td>
                <td>
                    <c:out value="${acc.suspectCard.cardName}"/>
                </td>
                <td>
                    <c:out value="${acc.weaponCard.cardName}"/>
                </td>
                <td>
                    <c:out value="${acc.roomCard.cardName}"/>
                </td>
                <td>
                    <c:out value="${acc.turn.userGame.user.username}"/>
                </td>
                <td>
                    <c:out value="${acc.playerWhoShows.user.username}"/>
                </td>
                <td>
                    <c:if test="${acc.turn.userGame.user.id == loggedUser.id}">
                        <c:out value="${acc.shownCard.cardName}"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</cluedo:layout>
