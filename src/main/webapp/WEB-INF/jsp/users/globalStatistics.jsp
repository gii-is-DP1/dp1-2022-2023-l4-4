<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="global">

    <table class="table table-striped" style="border:0;">
        <tbody>
            <tr>
                <td style="width: 50%; vertical-align: top;">
                    <table class="table table-striped">
                        <thead>
                            <th>Global Statistics</th>
                            <th></th>
                        </thead>
                        <tbody>
                            <tr>
                                <th>
                                    Total played games
                                </th>
                                <td>
                                    <c:out value="${stats.totalGames()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    Total accusations
                                </th>
                                <td>
                                    <c:out value="${stats.totalAccusations()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    Total final accusations
                                </th>
                                <td>
                                    <c:out value="${stats.totalFinalAccusations()}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    Total time
                                </th>
                                <td>
                                    <c:out value="${stats.averageDuration()}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
                <td>
                    <table class="table table-striped">
                        <thead>
                            <th>Ranking</th>
                            <th></th>
                        </thead>
                        <tbody>
                            <tr>
                                <th>1. </th>
                                <td><c:out value="${stats.top3Wins().get(0).getUsername()}"></c:out></td>
                            </tr>
                            <tr>
                                <th>2. </th>
                                <td><c:out value="${stats.top3Wins().get(1).getUsername()}"></c:out></td>
                            </tr>
                            <tr>
                                <th>3. </th>
                                <td><c:out value="${stats.top3Wins().get(2).getUsername()}"></c:out></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>

</cluedo:layout>