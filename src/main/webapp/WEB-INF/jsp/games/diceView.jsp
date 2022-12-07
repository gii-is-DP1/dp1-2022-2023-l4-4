<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Throw Dices">
    <jsp:body>
        <div class="container">
            <h2>
                <form:form  class="form-horizontal">
                <div class="form-group">
                    <button class="btn btn-default" type="submit">Throw Dice</button>
                </div>
            </form:form>
            </h2>
        </div>
    </jsp:body>
</cluedo:layout>