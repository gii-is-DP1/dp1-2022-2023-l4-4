<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="Final Decision">
    <jsp:body>
        <div class="container">
            <h2>
                <a  href="/games/${game.id}/play/finalAccusation" >
                    <button class="btn btn-default" style="font-size:105%">
                        Final Accusation
                    </button>
                </a>
                <form:form  class="form-horizontal">
                    <div class="form-group">
                        <button class="btn btn-default" type="submit">Finish Turn</button>
                    </div>
                </form:form>
            </h2>
        </div>
    </jsp:body>
</cluedo:layout>