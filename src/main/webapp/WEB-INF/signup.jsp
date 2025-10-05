<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="row mb-5" style="margin: 0px 0px 0px 0px;">
    <div class="col-md-8 col-xl-6 text-center mx-auto">
        <h2>Signup</h2>
        <p class="w-lg-50">Please signup to get access to the service</p>
    </div>
</div>
<div class="container">
    <div class="row d-flex justify-content-center" style="margin-right: 10px;margin-left: 10px;padding-top: 0px;">
        <div class="col-md-6 col-xl-4" style="padding: 0px 0px;height: 750px;">
            <div class="card mb-5">
                <div class="card-body d-flex flex-column align-items-center" style="height: 700px;">
                    <form class="text-center" method="post" action="signup" enctype="multipart/form-data">
                        <p class="text-muted">Нажмите для загрузки фото</p>
                        <!-- File Button -->
                        <div class="form-group">
                            <label for="image">
                                <img id="previewId" src="assets/img/blank-photo.png" width="250px"
                                     alt="${requestScope.user.image}">
                            </label>
                            <input onchange="PreviewImage('image','previewId');" id="image" name="image"
                                   style="visibility:hidden;"
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
                            <div class="mb-3"><input class="form-control" type="text" name="name" placeholder="Name">
                            </div>
                            <div class="mb-3"></div>
                            <input class="form-control" type="text" name="login"
                                   placeholder="login">
                            <div class="mb-3"></div>
                            <div class="mb-3"></div>
                            <div class="mb-3"></div>
                            <input class="form-control" type="password" name="password" placeholder="Password">
                            <div class="mb-3"></div>
                            <input class="form-control" type="password" name="repeatPass" placeholder="Repeat password">
                            <div class="mb-3"></div>
                            <div class="alert alert-success" role="alert"
                                 style="background: var(--bs-light);color: var(--bs-black);">
                                <span><strong>Confirm password</strong></span></div>
                            <button class="btn btn-primary d-block w-100" type="submit">Signup</button>
                            <div class="mb-3"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="parts/footer.jsp" %>