<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Inventory view</title>
		<link rel="icon" type="image/x-icon" href="webresources/favicon.ico" />
		<link rel="stylesheet" href="webresources/inv.css"/>
	</head>

	<body>
		<div class="options">
		Inventory 
		
		
		
			<c:forEach var="items" items="${items}">
				<c:out value = "${items}"/> <br>
			</c:forEach>	
		
		
			Inventory Size = <c:out value="${size}" escapeXml="false"/> /35
		
		
		<br>
		<br>
		<br>
			<div>
				<form method = "get" action="/OrganTrail/linear">
					<button type="submit" name="submit" class="btn btn-primary btn-block btn-large">Go Back</button>
				</form>
			</div>
		</div>
	</body>
</html>
