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
                            <li class="nav-item active"><a href="${pageContext.request.contextPath}/home"
                                    class="nav-link">Home</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/view"
                                    class="nav-link">View</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/report"
                                    class="nav-link">Reports</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/logout"
                                    class="nav-link">Logout</a></li>
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

                            <h1 class="mb-3 bread">View Bookings</h1>
                        </div>
                    </div>
                </div>
            </section>

            <p></p>
            <h3 align="center">Charging Station</h3>
            <div class="row">
                <div class="col-md-2">
                </div>


                <div class="col-md-8">

                    <div class="row">


                    </div>
                </div>
            </div>

            <p>&nbsp;</p>

            <div class="row">
                <div class="col-md-2">
                </div>


                <div class="col-md-8">

                    <div class="row">


                        <c:forEach var="slot" items="${sdata}">

                            <div class="col-md-3" align="center">
                                <div class="card ${slot[0] eq 'yes' ? 'slot-occupied' : 'slot-free'}">
                                    <div class="card-body">
                                        <c:choose>
                                            <c:when test="${slot[0] == 'yes'}">

                                                Car No.: ${slot[2][3]} <br>
                                                <img src="${pageContext.request.contextPath}/images/${slot[2][6]}"
                                                    width="200" height="100"><br>
                                                <c:choose>
                                                    <c:when test="${slot[2][19] == 1}">
                                                        <a
                                                            href="${pageContext.request.contextPath}/view?act=start&sid=${sid}&slot=5&rid=${slot[2][0]}">Start
                                                            Charge</a>
                                                    </c:when>
                                                    <c:when test="${slot[2][19] == 2}">
                                                        <iframe
                                                            src="${pageContext.request.contextPath}/charge1?rid=${slot[2][0]}"
                                                            frameborder="0" width="200" height="60"></iframe>
                                                    </c:when>
                                                    <c:when test="${slot[2][19] == 3}">
                                                        <span class="style2">Charge Completed</span><br />


                                                    </c:when>
                                                </c:choose>

                                                <c:if test="${slot[2][21] == 1 && slot[2][20] == 'Cash'}">
                                                    <h3 style="color:#FF0000">Payment Verification</h3>
                                                    <a
                                                        href="${pageContext.request.contextPath}/view?act=pay&rid=${slot[2][0]}">Amount
                                                        Received</a>
                                                </c:if>


                                            </c:when>
                                            <c:otherwise>
                                                <img src="${pageContext.request.contextPath}/images/slot.jpg"
                                                    width="131" height="164"> <br>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>









                    </div>
                </div>
            </div>
            <p>&nbsp;</p>


            <p>&nbsp;</p>
            <form name="form1" method="post" action="">
                <p align="center"><input type="submit" name="Submit" value="Refresh"></p>
            </form>
            <p>&nbsp;</p>


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