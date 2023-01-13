<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<style>
    h2{text-align: center}
</style>
<cluedo:layout pageName="chat">
    <jsp:body>
    <h2>
        GAME CHAT
    </h2>
        <c:forEach items="${messages}" var="m">
            <c:choose>
                <c:when test="${m.player.id==userNowId}">
                    <div style="text-align: right;">
                        
                        <c:out value="${m.text}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: left;">
                         <c:out value="${m.player.username}: ${m.text}"/>
                    </div>
                 </c:otherwise>
            </c:choose>
        </c:forEach>
        <form:form modelAttribute="chatMessage" class="form-horizontal">
            <div class="form-group has-feedback">                
                <cluedo:inputFieldLimited label="message" name="text" limit="100"/>
            </div>
            <input type="hidden" name="player" value="${userNowId}">
            <input type="hidden" name="game" value="${gameId}">


            <div class="form-group">
                <button class="btn btn-default"  type="submit">Enviar</button>
            </div>
        </form:form>   
    </jsp:body>
</cluedo:layout>

