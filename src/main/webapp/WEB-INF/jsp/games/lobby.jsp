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
            </tr>
            <tr>
                <td>
                    <img class="img-responsive" src="${player.imageurl}"/>  
                </td>
            </tr>
        </c:forEach>
    </table>
</cluedo:layout>