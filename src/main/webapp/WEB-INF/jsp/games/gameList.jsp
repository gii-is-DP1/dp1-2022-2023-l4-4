<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="games">
    <h2>
        Active games
    </h2>
    <div class="container">
    <h2>
        <a href="/games/new">
            <button class="btn btn-default">
            New Game
            </button>
        </a>
    </h2>
    <h2>
        <a href="/games/past">
            <button class="btn btn-default">
            Finished games
            </button>
        </a>
    </h2>
    </div>
    <table id="gameTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Game</th>
            <th style="width: 200px;">Host</th>
            <th style="width: 120px">Size</th>
            <th style="width: 120px">Private</th>
            <th style="width: 120px">Status</th>
            <th style="width: 120px">Winner</th>
            <th style="width: 120px">Time</th>
            <th style="width: 120px">Players</th>
            <th style="width: 120px">Join</th>
            
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
                    <c:out value="${game.status}"/>
                </td>
                <td>
                    <c:out value="${game.winner}"/>
                </td>
                <td>
                    <c:out value="${game.duration}"/>
                </td>
                <td>
                    <c:forEach items="${game.lobby}" var="player">
                        <c:out value="${player.username}"/>
                    </c:forEach>
                </td>
                <td>
                    <a  href="/games/${game.id}" >
                    <button class="btn btn-default">
                        Join Game
                    </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>