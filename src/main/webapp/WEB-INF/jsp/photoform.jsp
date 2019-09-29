<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Page</title>
<script src="<c:url value="/js/jquery-3.4.1.min.js" />"></script>
</head>
<body>
	<h1>Spring Boot Photo upload example</h1>

	<form:form method="POST" modelAttribute="uploadPhotoForm" action="${pageContext.request.contextPath}/uploadPhoto" enctype="multipart/form-data">
		<div style="color: red;">
			<form:errors path="*"/>
			<br/>
			<br/>
		</div>
	    <table>
			<tr>
				<td>Photo</td>
				<td>:</td>
				<td><form:input type="file" path="file"/></td>
			</tr>
			<tr>
				<td>Caption</td>
				<td>:</td>
				<td><form:input type="text" path="caption" /></td>
			</tr>
			<tr>
				<td colspan="2"></td>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>

	<br>
	<br>
	<a href="${pageContext.request.contextPath}/photoList">Show Photo List</a>

</body>
</html>