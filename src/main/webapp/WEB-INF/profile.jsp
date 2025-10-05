<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-12"><span class="fs-1" style="margin: 10px;">User Profile</span></div>
    </div>
    <div class="row">
        <div class="form-group">
            <img src="images/${user.image}" width="250" alt="${user.image}">
        </div>
        <div class="col-md-6"><span class="fs-4" style="margin: 10px;">Personel Data</span>
            <p style="margin: 10px;">Name:&nbsp;${sessionScope.get("user").getName()}</p><br>
            <p style="margin: 10px;">Connected stores:&nbsp
            <div class="container">
                <c:forEach var="store" items="${sessionScope.stores}">
                    <c:forEach var="storeKey" items="${user.getSellerApiKeyList()}">
                        <c:if test="${store.accessKey.equals(storeKey)}">
                            <a href="store-page?storeId=${store.id}"><c:out value='${store.storeName} '/> </a><br>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
            </p>
            <p style="margin: 10px;">Status:&nbsp;${user.getRole()}</p>
        </div>
        <div class="col-md-6"></div>
    </div>
</div>
<%@include file="parts/footer.jsp" %>