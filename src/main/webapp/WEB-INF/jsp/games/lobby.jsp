<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="games">
    <h2>
        Lobby
    </h2>

    <table id="gameTable" class="table table-striped">
        <c:forEach items="${lobby.lobby}" var="player">
            <tr>
                <th>
                    Player <c:out value="${player.username}"/>
                </th>
                <th>
                    <c:if test="${player.imageurl != null}">
                        <img class="img-responsive" src="${player.imageurl}" style="width: 50px !important; height: 50px !important;"/>
                    </c:if>
                    <c:if test="${player.imageurl == null}">
                        <img class="img-responsive" src="https://www.softzone.es/app/uploads/2018/04/guest.png" style="width: 50px !important; height: 50px !important;"/>
                    </c:if>
                </th>
            </tr>
        </c:forEach>
    </table>
    <form:form  class="form-horizontal">
        <div class="form-group">
            <button class="btn btn-default" type="submit">Start Game</button>
        
            <a  href="/games/${lobby.id}/leave" >
                <button class="btn btn-default">
                    Leave lobby
                </button>
            </a>
        </div>
    </form:form>
    
</cluedo:layout>