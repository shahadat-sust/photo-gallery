<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Status</title>
</head>
<body>
	<h1>Photo Gallery</h1>
		<c:forEach var="p" items="${photoList}">
			<fieldset>
				<legend>${p.caption}</legend>
				<span>
					<img alt="${p.caption}" src="${p.url}" width="200">
				</span>
			</fieldset>
		</c:forEach>
	<br>
	<br>
	<a href="${pageContext.request.contextPath}/">Upload More</a>
</body>
</html>