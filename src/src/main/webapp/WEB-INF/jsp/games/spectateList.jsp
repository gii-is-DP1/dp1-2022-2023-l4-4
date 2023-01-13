<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<style type="text/css">
    th {font-size: 110%;}
    td {font-size:105%}
</style>
<petclinic:layout pageName="spectateList">
    <h2 style="font-size: 150%">
        Spectate games
    </h2>
    <div >
        <table class="table">
            <th style="border:none">
                <a href="/games">
                    <button class="btn btn-default" style="float: right;font-size: 105%">
                    Lobby List
                    </button>
                </a>
            </th>
            <th style="border:none">
                <a href="/games/spectate">
                    <button class="btn btn-default" style="font-size: 105%">
                    Refresh
                    </button>
                </a>
            </th>
        </table>
    
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
                        <br/>
                    </c:forEach>
                </td>
                <td>
                    <a  href="/games/${game.id}/spectate" >
                        <button class="btn btn-default" style="font-size:105%">
                            Spectate
                        </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>