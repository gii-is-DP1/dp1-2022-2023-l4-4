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
                Will you make a FINAL ACCUSATION this turn?
            </h2>
            <div>
                <h3 style="padding-top: 5%;">
                    View all accusations made
                </h3>
                <a  href="/games/${game.id}/play/accusations"  target="_blank">
                    <button class="btn btn-default" style="font-size:105%;">
                        Accusations
                    </button>
                </a>
            </div>
            <h3 style="padding-top: 5%;">Make the FINAL ACCUSATION</h3>
            <a  href="/games/${game.id}/play/finalAccusation" >
                <button class="btn btn-default" style="font-size:105%">
                    Final Accusation
                </button>
            </a>
            <h3 style="padding-top: 5%;">Finish the turn</h3>
            <form:form  class="form-horizontal">
                <div class="form-group">
                    <button class="btn btn-default" type="submit">Conclude turn</button>
                </div>
            </form:form>
        </div>
    </jsp:body>
</cluedo:layout>