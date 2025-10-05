<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-12"><span class="fs-1" style="text-align: center;">Store Page</span></div>
        </div>
        <div class="row">
            <div class="col-md-6"><span class="fs-4" style="margin: 10px;">Store Data</span>
                <a href="create-product?storeId=${requestScope.store.id}" class="btn btn-primary ms-md-2">Create product</a>
                <p style="margin: 10px;">Name:&nbsp;${requestScope.store.storeName}</p>
                <p style="margin: 10px;">Products:
                <c:forEach var="product" items="${requestScope.store.products}">
                    <br> Open <a href="product-page?productId=${product.id}">${product.name}</a> <br>
                </c:forEach>
                </p>
                <p style="margin: 10px;">Count of products:&nbsp;</p><a href="#" style="margin: 6px;">List of products</a>
            </div>
            <div class="col-md-6">
                <picture><img src="images/${store.image}" alt="images/${store.image}" width="300" height="300" style="padding-center: 0px;"></picture>
            </div>
        </div>
    </div>
<%@include file="parts/footer.jsp" %>