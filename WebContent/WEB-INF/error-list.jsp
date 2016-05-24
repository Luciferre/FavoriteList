<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
	<c:when test="${errors != null and fn:length(errors) > 0}">
		<c:forEach var="error" items="${errors}">
			<p style="color: red">
				${error} <br />
			</p>
		</c:forEach>
	</c:when>
</c:choose>