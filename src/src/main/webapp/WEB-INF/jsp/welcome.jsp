<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <h2> Project ${title}</h2>
        <p><h2> Group ${group}</h2></p>
        <p><ul>
            <c:forEach items="${persons}" var="person">
                <li>${person.firstName}&nbsp;${person.lastName}</li>
            </c:forEach>
        </ul></p>
    </div>
</petclinic:layout>
