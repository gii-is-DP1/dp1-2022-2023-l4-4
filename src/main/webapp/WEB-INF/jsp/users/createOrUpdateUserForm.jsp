<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="users">
    <form:form modelAttribute="user" class="form-horizontal" id="add-owner-form" enctype="multipart/form-data">
        <div class="form-group has-feedback">
            <cluedo:inputField label="Username" name="username" />
            <cluedo:inputField label="E-mail" name="email" />
            <cluedo:inputField label="Password" name="password" />
            <input type="hidden" name="enabled" value="${user.enabled}">
            <h4>Photo:</h4>
            <spring:url value="/resources/images/jake.png" htmlEscape="true" var="jake"/>
            <input type="radio" id="jakeImage" name="imageurl" value="<c:out value='${jake}'/>">
            <label for="jakeImage"><img class="img-responsive" src="${jake}" width='100px'/></label>
            <spring:url value="/resources/images/elefante.jpg" htmlEscape="true" var="elefante"/>
            <input type="radio" id="elefanteImage" name="imageurl" value="<c:out value='${elefante}'/>">
            <label for="elefanteImage"><img class="img-responsive" src="${elefante}" width='100px'/> </label>
            
        </div>
        <div class="form-group">
            <c:if test="${!user['new']}">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit" formaction="/users/${user.id}/edit">Update User</button>
                </div>
            </c:if>
            <c:if test="${user['new']}">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit" formaction="/users/new">Create User</button>
                </div>
            </c:if>
        </div>
    </form:form>
</cluedo:layout>