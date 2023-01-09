<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="games">
    <h2 style="font-size:150%">
        Lobby
    </h2>
    <div>
        <c:if test="${isHost}">
            <form:form  class="form-horizontal" style="padding-bottom: 45px"> 
                <button class="btn btn-default" type="submit" style="font-size:110%;float: right; ">
                    Start Game
                </button>
            </form:form>
        </c:if>
    </div>
    <table id="gameTable" class="table table-striped">
        <c:forEach items="${lobby.lobby}" var="player">
            <tr>
                <th style="font-size: 120%">
                    Player <c:out value="${player.username}"/>
                </th>
                <th>
                    <c:if test="${player.imageurl != null}">
                        <img class="img-responsive" src="${player.imageurl}" style="width: 75px !important; height: 75px !important;"/>
                    </c:if>
                    <c:if test="${player.imageurl == null}">
                        <img class="img-responsive" src="https://www.softzone.es/app/uploads/2018/04/guest.png" style="width: 75px !important; height: 75px !important;"/>
                    </c:if>
                </th>
            </tr>
        </c:forEach>
    </table>
    <div>
        <a  href="/games/${lobby.id}/leave" >
            <button class="btn btn-default" style="font-size:110%;">
                Leave lobby
            </button>
        </a>
        <a  href="/notifications/${lobby.id}/invite" ><button class="btn btn-default">Invite a friend</button></a>

    </div>
</cluedo:layout>