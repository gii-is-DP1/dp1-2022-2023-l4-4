<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="New Achievement">
    <jsp:body>
        <h2 style = "font-size: 150%;">
            <c:if test="${achievement['new']}">New achievement</c:if>
            <c:if test="${!achievement['new']}">Edit achievement</c:if>
        </h2>
        <form:form modelAttribute="achievement" class="form-horizontal">
            <input type="hidden" name="id" value="${achievement.id}">
            <div class="form-group has-feedback" style="font-size:115%">
                <cluedo:inputField label="Name" name="name"></cluedo:inputField>
                <cluedo:inputField label="Description " name="description"></cluedo:inputField>
                <cluedo:inputField label="Experience " name="xp"></cluedo:inputField>
                <cluedo:selectField name = "metric" label="Metric" names="${metric}" size="${metric.size()}"></cluedo:selectField>
                <cluedo:inputField label="Goal" name="goal"></cluedo:inputField>
                <cluedo:selectField name = "badgeType" label="Type" names="${badge}" size="${badge.size()}"></cluedo:selectField>
            </div>
            <div class="form-group">
                <button class="btn btn-default" type="submit"  style="font-size:130%;">Save achievement</button>
            </div>
        </form:form>
    </jsp:body>
</cluedo:layout>