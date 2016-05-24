<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="databean.FavoriteBean"%>
<jsp:include page="template-top.jsp" />
<p>
<table>
	<c:forEach var="favorite" items="${favorites}">
		<tr>
			<td style="font-size: large">URL:</td>
			<td><a href="updateCount.do?id=${favorite.id}">${favorite.url}</a></td>
		</tr>
		<tr>
			<td style="font-size: large">Comment:</td>
			<td>${favorite.comment}</td>
		</tr>
		<tr>
			<td style="font-size: large">Click Counts:</td>
			<td>${favorite.clickCount}</td>
		</tr>
	</c:forEach>
</table>
</p>

<jsp:include page="template-bottom.jsp" />
