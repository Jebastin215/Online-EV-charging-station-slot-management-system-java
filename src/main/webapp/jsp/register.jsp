<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>EV Web</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

            <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
                rel="stylesheet">

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/open-iconic-bootstrap.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/magnific-popup.css">

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aos.css">

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ionicons.min.css">

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datepicker.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.timepicker.css">


            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flaticon.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icomoon.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=3.0">
            <script language="javascript">
                function validate() {
                    if (document.form1.name.value == "") {
                        alert("Enter the Name");
                        document.form1.name.focus();
                        return false;
                    }



                    if (document.form1.mobile.value == "") {
                        alert("Enter the Mobile Number");
                        document.form1.mobile.focus();
                        return false;
                    }
                    if (isNaN(document.form1.mobile.value)) {
                        alert("Invalid Mobile Number!");
                        document.form1.mobile.select();
                        return false;
                    }
                    if (document.form1.mobile.value.length != 10) {
                        alert("Mobile Number must be 10 digits");
                        document.form1.mobile.select();
                        return false;
                    }
                    if (document.form1.email.value == "") {
                        alert("Enter the E-mail");
                        document.form1.email.focus();
                        return false;
                    }
                    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(document.form1.email.value)) {
                        //return (true)  
                    }
                    else {
                        alert("You have entered an invalid email address!");
                        document.form1.email.select();
                        return false;
                    }

                    if (document.form1.bank.selectedIndex == 0) {
                        alert("Select the Bank");
                        document.form1.bank.focus();
                        return false;
                    }
                    ///////////
                    if (document.form1.account.value == "") {
                        alert("Enter the Account No.");
                        document.form1.account.focus();
                        return false;
                    }
                    if (isNaN(document.form1.account.value)) {
                        alert("Invalid Account Number!");
                        document.form1.account.select();
                        return false;
                    }
                    if (document.form1.account.value.length < 10) {
                        alert("Account Number must be 10 to 16 digits");
                        document.form1.account.select();
                        return false;
                    }
                    /////
                    if (document.form1.card.value == "") {
                        alert("Enter the Card No.");
                        document.form1.card.focus();
                        return false;
                    }
                    if (isNaN(document.form1.card.value)) {
                        alert("Invalid Card Number!");
                        document.form1.card.select();
                        return false;
                    }
                    if (document.form1.card.value.length != 16) {
                        alert("Card Number must be 16 digits");
                        document.form1.card.select();
                        return false;
                    }
                    /////
                    if (document.form1.uname.value == "") {
                        alert("Enter the Username");
                        document.form1.uname.focus();
                        return false;
                    }
                    if (document.form1.pass.value == "") {
                        alert("Enter the Password");
                        document.form1.pass.focus();
                        return false;
                    }

                    // Strong Password Validation
                    var pass = document.form1.pass.value;
                    var strongRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/;
                    if (!strongRegex.test(pass)) {
                        alert("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character (!@#$%^&*)");
                        document.form1.pass.focus();
                        return false;
                    }

                    if (document.form1.repass.value == "") {
                        alert("Enter the Re-type Password");
                        document.form1.repass.focus();
                        return false;
                    }
                    if (document.form1.repass.value != document.form1.pass.value) {
                        alert("Password mismatched");
                        document.form1.repass.select();
                        return false;
                    }

                    return true;
                }

                function togglePassword(inputId, iconId) {
                    var x = document.getElementById(inputId);
                    var icon = document.getElementById(iconId);
                    if (x.type === "password") {
                        x.type = "text";
                        icon.classList.remove("oi-eye");
                        icon.classList.add("oi-eye-off");
                    } else {
                        x.type = "password";
                        icon.classList.remove("oi-eye-off");
                        icon.classList.add("oi-eye");
                    }
                }
            </script>
        </head>

        <body>

            <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
                <div class="container">
                    <a class="navbar-brand" href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EV
                        <span>WEB</span></a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav"
                        aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="oi oi-menu"></span> Menu
                    </button>

                    <div class="collapse navbar-collapse" id="ftco-nav">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
                            </li>
                            <li class="nav-item active"><a href="${pageContext.request.contextPath}/login"
                                    class="nav-link">User</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/login2"
                                    class="nav-link">Charging Station</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- END nav -->

            <section class="hero-wrap hero-wrap-2 js-fullheight"
                style="background-image: url('${pageContext.request.contextPath}/images/ev.jpg');"
                data-stellar-background-ratio="0.5">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text js-fullheight align-items-end justify-content-start">
                        <div class="col-md-9 ftco-animate pb-5">

                            <h1 class="mb-3 bread">Register</h1>
                        </div>
                    </div>
                </div>
            </section>

            <section class="ftco-section contact-section">
                <div class="container">
                    <div class="row d-flex mb-5 contact-info">
                        <div class="col-md-4">
                            <div class="row mb-5">
                                <div class="col-md-12">
                                    <div class="border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-map-o"></span>
                                        </div>
                                        <p><span>Ev Slot Booking App</span></p>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-mobile-phone"></span>
                                        </div>
                                        <p><span>Phone:</span> <a href="tel:+919600715505">+91 9600715505</a></p>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-envelope-o"></span>
                                        </div>
                                        <p><span>Email:</span> <a
                                                href="mailto:jebastin215@gmail.com">jebastin215@gmail.com</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 block-9 mb-md-5">
                            <h3>User Registration</h3>
                            <span style="color:#FF0000">${msg}</span>
                            <form name="form1" action="${pageContext.request.contextPath}/register" method="post"
                                class="bg-light p-5 contact-form" onsubmit="return validate()">
                                <div class="form-group">
                                    <input type="text" name="name" class="form-control" placeholder="Name">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="address" class="form-control" placeholder="Address">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="mobile" maxlength="10" class="form-control"
                                        placeholder="Mobile No.">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="email" class="form-control" placeholder="Email">
                                </div>
                                <div class="form-group">
                                    <select class="form-control" name="bank">
                                        <option value="">-Bank-</option>
                                        <option>SBI</option>
                                        <option>Indian Bank</option>
                                        <option>IOC</option>
                                        <option>KVB</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="account" maxlength="16" class="form-control"
                                        placeholder="Account No.">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="card" maxlength="16" class="form-control"
                                        placeholder="Card No.">
                                </div>
                                <div class="form-group">
                                    <input type="text" name="uname" class="form-control" placeholder="Username">
                                </div>
                                <div class="form-group password-container">
                                    <input type="password" name="pass" id="password" class="form-control"
                                        placeholder="Password" required>
                                    <span class="password-toggle oi oi-eye" id="togglePasswordIcon"
                                        onclick="togglePassword('password', 'togglePasswordIcon')"></span>
                                </div>
                                <div class="form-group password-container">
                                    <input type="password" name="repass" id="repassword" class="form-control"
                                        placeholder="Re-type Password" required>
                                    <span class="password-toggle oi oi-eye" id="toggleRePasswordIcon"
                                        onclick="togglePassword('repassword', 'toggleRePasswordIcon')"></span>
                                </div>

                                <div class="form-group">
                                    <input type="submit" value="Register" class="btn btn-primary py-3 px-5">
                                </div>
                            </form>

                        </div>
                    </div>

                </div>
            </section>


            <footer class="ftco-footer ftco-bg-dark ftco-section">
                <div class="container">

                    <div class="row">
                        <div class="col-md-12 text-center">

                            <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                © 2025 EV Charging Management System | Developed by Jebastin Augustin Ponraj. All Rights
                                Reserved. <a href="https://colorlib.com" target="_blank"></a>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            </p>
                        </div>
                    </div>
                </div>
            </footer>


            <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery-migrate-3.0.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.easing.1.3.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.waypoints.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.stellar.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/aos.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.animateNumber.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
            <script src="${pageContext.request.contextPath}/js/jquery.timepicker.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/scrollax.min.js"></script>
            <script
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
            <script src="${pageContext.request.contextPath}/js/google-map.js"></script>
            <script src="${pageContext.request.contextPath}/js/main.js"></script>

        </body>

        </html>