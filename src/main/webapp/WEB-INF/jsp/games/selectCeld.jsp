<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Select Celd">
    <jsp:body>
        <h2>
            You rolled a ${diceResult}
        </h2>
        <h2>
            Select the celd you want to move
        </h2>
        <form:form modelAttribute="celdForm" class="form-horizontal">
            
            <div class="form-group has-feedback">
                <cluedo:selectField name = "finalCeld" label="Movements" names="${movements}" size="5"></cluedo:selectField>
            </div>
            <div class="form-group">  
                <button class="btn btn-default" type="submit">Move</button>
            </div>
        </form:form>
    </jsp:body>
</cluedo:layout>