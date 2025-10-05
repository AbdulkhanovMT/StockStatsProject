<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div>
    <c:forEach var="user" items="${requestScope.users}">
        <img src="images/${user.image}" alt="images/${user.image}" width="100px">
        Edit user <a href="edit-profile?id=${user.id}">${user.name}</a> <br> <br>
    </c:forEach>
</div>
<%@include file="parts/footer.jsp" %>