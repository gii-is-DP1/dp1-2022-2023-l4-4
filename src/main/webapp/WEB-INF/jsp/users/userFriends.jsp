<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<style type="text/css">
    th,td {font-size: 110%}
</style>

<petclinic:layout pageName="List of friends">
    <h2 style="font-size: 150%">List of friends</h2> 
    <a href="/users/{userId}/friends/add"> <button style="font-size: 110%">Add friends</button> </a>
    <a href="/users/{userId}/friends/delete"> <button style="font-size: 110%">Delete friends</button> </a>

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
                    <span style="color:grey"><c:out value="${user.tag}"/></span>              
                </td>          
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>