<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Select Celd">
    <jsp:body>
        <h2>
            You rolled a ${diceResult}
        </h2>
        <table class="table table-striped" style="border:0;">
            <tbody>
                <tr>
                    <td style="width: 60%; vertical-align: top;">
                        <div style="position: absolute">
                            <img src="/resources/images/numerated_board.jpeg" style="width:600px; height:600px;">
                            <c:forEach items="${game.players}" var="player">
                                <img src="/resources/images/pieces/${player.suspect}.png" style="width:18px; height:18px; position:absolute; z-index:2; top:${player.getY()}px; left:${player.getX()}px">
                            </c:forEach>
                        </div>
                    </td>
                    <td>
                        <div style = "padding-top: 20%">
                            <a  href="/games/${game.id}/play/accusations"  target="_blank">
                                <button class="btn btn-default" style="font-size:105%">
                                    Accusations
                                </button>
                            </a>
                            <a  href="/games/${game.id}/play/notes"  target="_blank">
                                <button class="btn btn-default" style="font-size:105%">
                                    Open New Notes
                                </button>
                            </a>
                        </div>
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
                    </td>
                </tr>
            </tbody>
        </table>  
        <h2>
            Select the celd you want to move
        </h2>
        <form:form modelAttribute="celdForm" class="form-horizontal">
            
            <div class="form-group has-feedback">
                <cluedo:selectField name = "finalCeld" label="Movements" names="${movements}" size="5"></cluedo:selectField>
            </div>
            <div class="form-group">  
                <button class="btn btn-default" type="submit">Move</button>
            </div>
        </form:form>
    </jsp:body>
</cluedo:layout>