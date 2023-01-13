<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Accusation">
    <jsp:body>
        <div class="container">
            <form:form  modelAttribute="cardForm" class="form-horizontal">
                <div>  
                    <cluedo:selectField name = "cardSelected" label="Card to show" names="${cards}" size="3"></cluedo:selectField>
                </div>
                <div class="form-group">
                    <button class="btn btn-default" type="submit">Show Card</button>
                </div>
            </form:form>
        </div>
    </jsp:body>
</cluedo:layout>