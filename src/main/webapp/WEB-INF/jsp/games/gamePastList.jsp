<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<cluedo:layout pageName="Finished games">
    <h2>
        <c:if test="${admin}">Past games</c:if>
        <c:if test="-${admin}">My past games</c:if>
    </h2>

    <table id="gameTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Game</th>
            <th style="width: 200px;">Host</th>
            <th style="width: 120px">Size</th>
            <th style="width: 120px">Private</th>
            <th style="width: 120px">Winner</th>
            <th style="width: 120px">Time</th>
            <th style="width: 120px">Players</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>
                        <c:out value="${game.id}"/>
                    </td>
                    <td>
                        <c:out value="${game.host.username}"/>
                    </td>
                    <td>
                        <c:out value="${game.lobbySize}"/>
                    </td>
                    <td>
                        <c:out value="${game.isPrivate}"/>
                    </td>
                    <td>
                        <c:out value="${game.winner}"/>
                    </td>
                    <td>
                        <c:out value="${game.duration}"/>
                    </td>
                    <td>
                        <c:forEach items="${game.players}" var="player">
                            <c:out value="${player.id} "/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</cluedo:layout>