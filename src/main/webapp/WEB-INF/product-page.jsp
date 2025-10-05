<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <p class="fs-4" style="--bs-body-font-size: 21rem;margin: 10px;">Product
                name: ${requestScope.product.name}</p>
        </div>
        <a href="edit-product?productId=${requestScope.product.id}" class="btn btn-primary ms-md-2">Edit product</a>
    </div>
    <div class="row">
        <div class="col-md-6">
            <p style="margin: 10px;">Product sku:&nbsp;${requestScope.product.sku}</p>
            <p style="margin: 10px;">Product category:&nbsp;${requestScope.product.category}</p>
            <p style="margin: 10px;">Total amount:&nbsp;${requestScope.product.totalAmount}</p>
            <p style="margin: 10px;">Optional parameters:&nbsp;
                <br>
                <c:forEach var="parameter" items="${requestScope.parameters}">
            <p><c:out value="${parameter.name} : ${parameter.value}"/></p>
            </c:forEach>
            <br>
            </p>
        </div>
        <div class="col-md-6">
            <picture><img src="images/${product.image}" alt="images/${product.image}"
                          class="img-fluid d-flex d-xl-flex justify-content-center align-items-center justify-content-xl-center"
                          width="1000" height="1000"></picture>
        </div>
    </div>
</div>
<%@include file="parts/footer.jsp" %>