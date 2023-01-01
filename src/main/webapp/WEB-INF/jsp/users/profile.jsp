<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<style type="text/css">
    th { font-size: 111%;}
    td { font-size: 110%;}
</style>

<petclinic:layout pageName="users">

    <h2 style = "font-size: 150%;">User Information</h2>

    <table class="table table-striped">
        <tr>
            <th>Username</th>
            <td><b><c:out value="${user.username} "/></b></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${user.email}"/></td>
        </tr>
        <tr>
            <th>Tag</th>
            <td><c:out value="${user.tag}"/></td>
        </tr>
        <tr>
            <th>Profile image</th>
            <td>
                <c:if test="${user.imageurl != null}">
                        <img class="img-responsive" src="${user.imageurl}" style="width: 75px !important; height: 75px !important;"/>
                    </c:if>
                    <c:if test="${user.imageurl == null}">
                        <img class="img-responsive" src="https://www.softzone.es/app/uploads/2018/04/guest.png" style="width: 75px !important; height: 75px !important;"/>
                    </c:if>
            </td>
        </tr>
      
    </table>

    <spring:url value="/profile/edit" var="editUrl">
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default" style="font-size:110%">Edit Profile</a>

    <br/>
    <br/>
    <br/>
  

</petclinic:layout>

