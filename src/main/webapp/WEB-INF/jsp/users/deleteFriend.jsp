<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cluedo" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<cluedo:layout pageName="addFriends">

    <h2>Find friends</h2>

    
    <form:form modelAttribute="UsernameForm" class="form-horizontal">
            <div class="form-group has-feedback">
                <cluedo:inputField label="Username" name="username" />
            </div>
            <div class="form-group">
                <button class="btn btn-default" type="submit">Delete friend</button> 
            </div>
        </form:form>

    <br/> 
	
    </cluedo:layout>
