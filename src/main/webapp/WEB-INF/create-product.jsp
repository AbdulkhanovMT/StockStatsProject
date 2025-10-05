<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data">
        <fieldset>
            <div class="row">
                <div class="col-md-12">
                    <p class="fs-4" style="--bs-body-font-size: 21rem;margin: 10px;">Name</p>
                    <input id="name"
                           name="name"
                           type="text"
                           value=""
                           placeholder="product name"
                           class="form-control input-md"
                           required="">
                </div>
            </div>
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
                    <p style="margin: 10px;">Product sku</p>
                    <input id="sku"
                           name="sku"
                           type="text"
                           value=""
                           placeholder="sku"
                           class="form-control input-md"
                           required="">
                    <label class="col-md-4 control-label" for="role">Product category</label>
                    <div class="col-md-4">
                        <select id="category" name="category" class="form-control">
                            <c:forEach var="category" items="${applicationScope.categories}">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <p style="margin: 10px;">Total amount</p>
                    <input id="totalAmount"
                           name="totalAmount"
                           type="number"
                           value="${requestScope.product.totalAmount}"
                           placeholder="${requestScope.product.totalAmount}"
                           class="form-control input-md"
                           required="">
                </div>
                <button id="fillParameters" name="fillParameters" class="btn btn-primary">Fill other parameters</button>
            </div>
        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>