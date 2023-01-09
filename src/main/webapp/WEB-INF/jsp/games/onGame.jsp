<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<style>
    h2{text-align: center}
</style>
<cluedo:layout pageName="On Game">
    <div>
        <c:if test="${phase == 'DICE'}">
            <h2>
                ${game.actualPlayer.user.username} IS THROWING DICE!
            </h2>
        </c:if>
        <c:if test="${phase == 'MOVEMENT'}">
            <h2>
                ${game.actualPlayer.user.username} IS THINKING WHERE TO MOVE...
            </h2>
        </c:if>
        <c:if test="${phase == 'ACCUSATION'}">
            <c:if test="${playerWhoShows!=null}">
                <c:if test="${!notAccuser}">
                    <h2>
                        ${playerWhoShows.user.username} WILL GIVE YOU A CARD!
                    </h2>
                </c:if>
                <c:if test="${notAccuser}">
                    <h2>
                        ${playerWhoShows.user.username} WILL GIVE A CARD TO ${game.actualPlayer.user.username}!
                    </h2>
                </c:if>
            </c:if>
            <c:if test="${playerWhoShows==null}">
                <h2>
                    ${game.actualPlayer.user.username} IS MAKING AN ACCUSATION!
                </h2>
            </c:if>
        </c:if>
        
        <c:if test="${phase == 'FINAL'}">
            <h2>
                ${game.actualPlayer.user.username} IS CONCLUDING THE TURN!
            </h2>
        </c:if>

    </div>

    <table class="table table-striped" style="border:0;">
        <tbody>
            <tr>
                <td style="width: 60%; vertical-align: top;">
                    <div> 
                        <img src="/resources/images/tablero.jpeg" style="width:600px;height:600px;">
                    </div>
                </td>
                <td>
                    <div style = "padding-top: 20%">
                        <h3>
                            YOUR CARDS:
                        </h3>
                        <c:forEach items="${cards}" var="card">
                            <p>
                                <c:out value="${card.cardName}"/>
                            </p>
                        </c:forEach>
                    </div>
                    <div style = "padding-top: 20%">
                        <h3>
                            PLAYERS LIST:
                        </h3>
                        <c:forEach items="${game.players}" var="player">
                                <p>
                                    <c:out value="[${player.suspect}] ${player.user.username} "/>
                                    <c:if test="${player.isEliminated==true}">
                                        <c:out value="(Eliminated)"/>
                                    </c:if>
                                </p>
                            
                        </c:forEach>
                    </div>
                    <div style = "padding-top: 20%">
                        <a  href="/games/${game.id}/play/accusations"  target="_blank">
                            <button class="btn btn-default" style="font-size:105%">
                                Accusations
                            </button>
                        </a>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>  
</cluedo:layout>