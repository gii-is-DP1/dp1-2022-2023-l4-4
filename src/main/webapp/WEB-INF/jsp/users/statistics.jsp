<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="statistics">

    <h2 style = "font-size: 150%;">User Statistics</h2>


    <table class="table table-striped">
        <tr>
            <th style = "font-size: 120%;">Total experience</th>
            <td style = "font-size: 120%;"><c:out value="${stats.xp} "/></td>
        </tr>
        <tr>
            <th style = "font-size: 120%;">Total Games</th>
            <td style = "font-size: 120%;"><c:out value="${stats.totalGames}"/></td>
        </tr>
        <tr>
            <th style = "font-size: 120%;">Total Time</th>
            <td style = "font-size: 120%;"><c:out value="${stats.totalTime} "/></td>
        </tr>
        <tr>
            <th style = "font-size: 120%;">Total Games</th>
            <td style = "font-size: 120%;"><c:out value="${stats.totalAccusations}"/></td>
        </tr>
        <tr>
            <th style = "font-size: 120%;">Victories</th>
            <td style = "font-size: 120%;"><c:out value="${stats.victories} "/></td>
        </tr>
        <tr>
            <th style = "font-size: 120%;">Afk counter</th>
            <td style = "font-size: 120%;"><c:out value="${stats.afkCounter}"/></td>
        </tr>
        <c:if test="${stats.longestGame != null}">
            <tr>
                <th style = "font-size: 120%;">Longest Game</th>
                <td style = "font-size: 120%;"><c:out value="${stats.longestGame.duration}"/></td>
            </tr>
        </c:if>
        <c:if test="${stats.shortestGame != null}">
            <tr>
                <th style = "font-size: 120%;">Shortest Game</th>
                <td style = "font-size: 120%;"><c:out value="${stats.shortestGame.duration}"/></td>
            </tr>
        </c:if>
        <tr>
            <th style = "font-size: 120%;">Total Final Accusations</th>
            <td style = "font-size: 120%;"><c:out value="${stats.totalFinalAccusations}"/></td>
        </tr>
    </table>
  

</cluedo:layout>
