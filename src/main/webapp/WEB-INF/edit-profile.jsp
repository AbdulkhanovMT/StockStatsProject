<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<%@ page import="com.javarush.abdulkhanov.entity.Role" %>
<div class="container">
    <form class="form-horizontal" method="post" enctype="multipart/form-data">
        <fieldset>

            <!-- Form Name -->
            <legend>Edit user:</legend>
            <!-- File Button -->
            <div class="form-group">
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
            </div>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Name</label>
                <div class="col-md-4">
                    <input
                            id="name"
                            name="name"
                            type="text"
                            value="${requestScope.user.name}"
                            placeholder="your name"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">min 3 symbols</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input
                            id="login"
                            name="login"
                            type="text"
                            value="${requestScope.user.login}"
                            placeholder="your login"
                            class="form-control input-md"
                            required="">
                    <span class="help-block">min 3 symbols</span>
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           value="${requestScope.user.password}"
                           placeholder="your password"
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 8 symbols</span>
                </div>
            </div>


            <!-- Select Basic -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <c:forEach var="role" items="${applicationScope.roles}">
                            <option value="${role}" ${role==requestScope.user.role?"selected":""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="create">Operation</label>
                <div class="col-md-8">
                    <c:if test="${sessionScope.user==null}">
                        <button id="create" name="create" class="btn btn-success">Create</button>
                    </c:if>
                    <c:if test="${sessionScope.user!=null}">
                        <button id="update" name="update" class="btn btn-primary">Update</button>
                    </c:if>

                </div>
            </div>

        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>