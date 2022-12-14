<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="List of friends">
    <h2>List of friends</h2> 
    <a href="/users/{userId}/friends/add"> <button>Add friends</button> </a>
    <a href="/users/{userId}/friends/delete"> <button>Delete friends</button> </a>

    <table id="gameTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 120px;">Username</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${user}" var="user">
            <tr>

                <td>
                    <c:out value="${user.username}"/>
                </td>
               
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>