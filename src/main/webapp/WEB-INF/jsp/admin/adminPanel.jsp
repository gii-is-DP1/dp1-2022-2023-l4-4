<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="admin_panel">
    <h2>ADMIN PANEL</h2>
    <a href="/games/allNotFinishedGames">View All Not Finished Games</a>
    <a href="/games/allFinishedGames">View All Finished Games</a>
    <a href="/games/lobbies">View All Listed Lobbies</a>
   
</petclinic:layout>