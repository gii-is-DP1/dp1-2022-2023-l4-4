<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<cluedo:layout pageName="achievements">
    <h2>All achievements</h2>
    <h2>
        <a  href="/achievements/new" >
            <button class="btn btn-default">
                Create achievement
            </button>
        </a>
    </h2>
    <table id="achievementTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Metric</th>
            <th style="width: 200px;">Type</th>
            <th style="width: 120px">Goal</th>
            <th style="width: 120px">Description</th>
            <th style="width: 120px">Experience</th>
            <th style="width: 120px">Badge</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${achievements}" var="achievement">
                <tr>
                    <td>
                        <c:out value="${achievement.name}"/>
                    </td>
                    <td>
                        <c:out value="${achievement.metric}"/>
                    </td>
                    <td>
                        <c:out value="${achievement.badgeType}"/>
                    </td>
                    <td>
                        <c:out value="${achievement.goal}"/>
                    </td>
                    <td>
                        <c:out value="${achievement.description}"/>
                    </td>
                    <td>
                        <c:out value="${achievement.xp}"/>
                    </td>
                    <td>
                        <c:if test="${achievement.imageUrl == null}">
                            <img class="img-responsive" src="https://www.shutterstock.com/image-vector/achievement-icon-logo-modern-line-260nw-551572915.jpg" style="width: 100px !important; height: 100px !important;"/>
                        </c:if>
                        <c:if test="${achievement.imageUrl != null}">
                            <img class="img-responsive" src="${achievement.imageUrl}" style="width: 100px !important; height: 100px !important;"/>
                        </c:if>
                    </td>
                    <td>
                        <a href="/achievements/${achievement.id}/edit">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</cluedo:layout>