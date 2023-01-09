<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Accusation List">
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
                    <c:out value="${acc.suspectCard}"/>
                </td>
                <td>
                    <c:out value="${acc.weaponCard}"/>
                </td>
                <td>
                    <c:out value="${acc.roomCard}"/>
                </td>
                <td>
                    <c:out value="${acc.turn.userGame.user.username}"/>
                </td>
                <td>
                    <c:out value="${acc.playerWhoShows}"/>
                </td>
                <td>
                    <c:if test="${{acc.turn.userGame.user.id == loggedUser.id}">
                        <c:out value="${acc.shownCard}"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</cluedo:layout>
