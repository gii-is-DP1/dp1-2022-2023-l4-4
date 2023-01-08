<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>

<cluedo:layout pageName="On Game">
    <h2>
        WAITING... IT'S THE TURN OF ${game.actualPlayer.user.username}
    </h2>
    <a  href="/games/${game.id}/play/notes" target="_blank">
        <button class="btn btn-default" style="font-size:105%">
            New table
        </button>
    </a>
                
</cluedo:layout>