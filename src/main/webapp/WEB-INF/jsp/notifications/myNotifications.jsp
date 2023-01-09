<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Notifications">
    <h2 style="font-size: 150%">Notifications</h2> 

    <table id="gameTable" class="table table-striped">
    
        <tbody>
        <c:forEach items="${notification}" var="noti">
            <tr>
                <td>
                <c:choose>
                <c:when test="${noti.link==null}">
                    <c:out value="${noti.text}"/>
                    <c:out value="${noti.timestamp}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${noti.text}"/>
                    <a  href="/games/${noti.link}" >
                        <button class="btn btn-default" style="font-size:90%">
                            Join Game
                        </button>
                    </a>
                    <c:out value="${noti.timestamp}"/>               
                 </c:otherwise>
            </c:choose>



                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>

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