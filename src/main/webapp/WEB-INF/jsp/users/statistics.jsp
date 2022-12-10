<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="statistics">

    <h2>User Statistics</h2>


    <table class="table table-striped">
        <tr>
            <th>Total experience</th>
            <td><c:out value="${stats.xp} "/></td>
        </tr>
        <tr>
            <th>Total Games</th>
            <td><c:out value="${stats.totalGames}"/></td>
        </tr>
        <tr>
            <th>Total Time</th>
            <td><c:out value="${stats.totalTime} "/></td>
        </tr>
        <tr>
            <th>Total Games</th>
            <td><c:out value="${stats.totalAccusations}"/></td>
        </tr>
        <tr>
            <th>Victories</th>
            <td><c:out value="${stats.victories} "/></td>
        </tr>
        <tr>
            <th>Afk counter</th>
            <td><c:out value="${stats.afkCounter}"/></td>
        </tr>
        <c:if test="${stats.longestGame != null}">
            <tr>
                <th>Longest Game</th>
                <td><c:out value="${stats.longestGame.duration}"/></td>
            </tr>
        </c:if>
        <c:if test="${stats.shortestGame != null}">
            <tr>
                <th>Shortest Game</th>
                <td><c:out value="${stats.shortestGame.duration}"/></td>
            </tr>
        </c:if>
        <tr>
            <th>Total Final Accusations</th>
            <td><c:out value="${stats.totalFinalAccusations}"/></td>
        </tr>
    </table>
  

</cluedo:layout>
