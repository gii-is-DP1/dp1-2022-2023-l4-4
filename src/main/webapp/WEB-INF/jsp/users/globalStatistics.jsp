<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="global">

    <table class="table table-striped" style="border:0;">
        <tbody>
            <tr>
                <td style="width: 70%; vertical-align: top;">
                    <table class="table table-striped">
                        <thead>
                            <th colspan="2" style = "font-size: 120%; text-align: center;">Global Statistics</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th style="font-size:120%">
                                    Total played games
                                </th>
                                <td style="font-size:120%">
                                    <c:out value="${stats.totalGames()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th style="font-size:120%">
                                    Total accusations
                                </th>
                                <td style="font-size:120%">
                                    <c:out value="${stats.totalAccusations()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th style="font-size:120%">
                                    Total final accusations
                                </th>
                                <td style="font-size:120%">
                                    <c:out value="${stats.totalFinalAccusations()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th style="font-size:120%">
                                    Average minutes
                                </th>
                                <td style="font-size:120%">
                                    <c:out value="${stats.averageDuration()}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    <table class="table table-striped">
                        <thead>
                            <th colspan="2" style = "font-size: 120%; text-align: center;">Ranking</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th style="font-size:120%">1. </th>
                                <td style="font-size: 120%; text-align: center"><c:out value="${stats.top3Wins().get(0).getUsername()}"></c:out></td>
                            </tr>
                            <tr>
                                <th style="font-size:120%">2. </th>
                                <td style="font-size: 120%; text-align: center"><c:out value="${stats.top3Wins().get(1).getUsername()}"></c:out></td>
                            </tr>
                            <tr>
                                <th style="font-size:120%">3. </th>
                                <td style="font-size: 120%; text-align: center"><c:out value="${stats.top3Wins().get(2).getUsername()}"></c:out></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>

</cluedo:layout>