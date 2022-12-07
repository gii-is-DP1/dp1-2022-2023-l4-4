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
                <a href="/games/${gameId}/play/dice">
                    <button class="btn btn-default">
                    ThrowDices
                    </button>
                </a>
            </h2>
        </div>
    </jsp:body>
</cluedo:layout>