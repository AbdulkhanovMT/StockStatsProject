<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div>
    <c:forEach var="store" items="${sessionScope.stores}">
        Open <a href="store-page?storeId=${store.id}">${store.storeName}</a> <br> <br>
    </c:forEach>
</div>
<%@include file="parts/footer.jsp" %>