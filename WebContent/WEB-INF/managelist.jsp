<%@ page import="databean.UserBean"%>
<%@ page import="databean.FavoriteBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="template-top.jsp" />

<p style="font-size: medium">Add a new URL:</p>

<jsp:include page="error-list.jsp" />

<p>
<form method=\ "POST\" action="addFavorite.do">
	<table>
		<tr>
			<td colspan="3"><hr /></td>
		</tr>
		<tr>
			<td style="font-size: large">URL:</td>
			<td colspan="2"><input type="text" size="40" name="url" /></td>
		</tr>
		<tr>
			<td style="font-size: large">Comment:</td>
			<td colspan="2"><input type="text" size="40" name="comment" /></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="button" value="Add to Favorite" /></td>
		</tr>
		<tr>
			<td colspan="3"><hr /></td>
		</tr>
	</table>
</form>
</p>
</br>
<p style="font-size: medium">Current URLs:</p>
<hr />
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
		<tr>
			<td>
				<form method="POST" action="delete.do">
					<input type="hidden" name="id" value="${favorite.id}" /> <input
						type="submit" value="Delete" />
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
</p>

<jsp:include page="template-bottom.jsp" />
