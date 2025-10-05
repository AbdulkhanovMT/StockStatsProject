<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <p class="fs-4" style="--bs-body-font-size: 21rem;margin: 10px;">Product
                name: ${requestScope.product.name}</p>
        </div>
    </div>
    <form class="form-horizontal" method="post" enctype="multipart/form-data">
        <fieldset>
            <label class="col-md-4 control-label" for="image">Change image</label><br>
            <input onchange="PreviewImage('image','previewId');"
                   id="image"
                   name="image"
                   class="input-file" type="file">
            <script type="text/javascript">
                function PreviewImage(inputFileId, imageId) {
                    let oFReader = new FileReader();
                    oFReader.readAsDataURL(document.getElementById(inputFileId).files[0]);
                    oFReader.onload = function (oFREvent) {
                        document.getElementById(imageId).src = oFREvent.target.result;
                    };
                }
            </script>
            <div class="row">
                <div class="col-md-6">
                    <p style="margin: 10px;">Product sku:&nbsp;${requestScope.product.sku}</p>
                    <p style="margin: 10px;">Product category:&nbsp;${requestScope.product.category}</p>
                    <p style="margin: 10px;">Total amount:&nbsp;${requestScope.product.totalAmount}</p>
                    <input id="totalAmount"
                           name="totalAmount"
                           type="number"
                           value="${requestScope.product.totalAmount}"
                           placeholder="${requestScope.product.totalAmount}"
                           class="form-control input-md"
                           required="">
                    <p style="margin: 10px;">Optional parameters:&nbsp</p>
                    <br>
                    <c:forEach var="parameter" items="${requestScope.parameters}">
                        <p><c:out value="${parameter.name} : ${parameter.value}"/></p>
                        <input id="${parameter.name}"
                               name="${parameter.name}"
                               type="text"
                               value="${parameter.value}"
                               placeholder="${parameter.name}"
                               class="form-control input-md"
                               required="">
                        <br>
                    </c:forEach>
                    <br>

                </div>
                <button id="update" name="update" class="btn btn-primary">Update</button>
            </div>
        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>