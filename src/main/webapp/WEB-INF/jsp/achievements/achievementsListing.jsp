<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<cluedo:layout pageName="achievements">
    <h2 style = "font-size: 150%;">All achievements</h2>
    <sec:authorize access="hasAuthority('admin')">
        <c:if test="${canCreateAndEdit}">
            <h2>
                <a  href="/achievements/new" style="float: right; padding: 5px; ">
                    <button class="btn btn-default" style="font-size:95%">
                        Create achievement
                    </button>
                </a>
            </h2>
        </c:if>
    </sec:authorize>
    <table id="achievementTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px; font-size:130%; text-align: center;">Name</th>
            <th style="width: 200px; font-size:130%; text-align: center;">Metric</th>
            <th style="width: 200px; font-size:130%; text-align: center;">Type</th>
            <th style="width: 120px; font-size:130%; text-align: center;">Goal</th>
            <th style="width: 120px; font-size:130%; text-align: center;">Description</th>
            <th style="width: 120px; font-size:130%; text-align: center;">Experience</th>
            <th style="width: 120px; font-size:130%; text-align: center;">Badge</th>
            <c:if test="${canCreateAndEdit}">
                <th style="width: 50px; font-size:130%;"></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${achievements}" var="achievement">
                <tr>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.name}"/>
                    </td>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.metric}" />
                    </td>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.badgeType}"/>
                    </td>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.goal}"/>
                    </td>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.description}"/>
                    </td>
                    <td style="font-size:115%; text-align: center;">
                        <c:out value="${achievement.xp}"/>
                    </td>
                    <td>
                        <c:if test="${achievement.imageUrl == null}">
                            <img class="img-responsive" src="https://www.shutterstock.com/image-vector/achievement-icon-logo-modern-line-260nw-551572915.jpg" style="width: 100px !important; height: 100px !important;margin-left: auto; margin-right: auto;"/>
                        </c:if>
                        <c:if test="${achievement.imageUrl != null}">
                            <img class="img-responsive" src="${achievement.imageUrl}" style="width: 100px !important; height: 100px !important; margin-left: auto; margin-right: auto;"/>
                        </c:if>
                    </td>
                    <sec:authorize access="hasAuthority('admin')">
                        <c:if test="${canCreateAndEdit}">
                            <td style="font-size:130%;">
                                <a href="/achievements/${achievement.id}/edit">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true" style="color: grey;"></span>
                                </a>
                            </td>
                        </c:if>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</cluedo:layout>