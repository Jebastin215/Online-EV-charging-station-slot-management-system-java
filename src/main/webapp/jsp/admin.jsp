<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>EV Web</title>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

            <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800&display=swap"
                rel="stylesheet" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/open-iconic-bootstrap.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/magnific-popup.css" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aos.css" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ionicons.min.css" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datepicker.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.timepicker.css" />

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flaticon.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icomoon.css" />
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
                            <li class="nav-item active">
                                <a href="${pageContext.request.contextPath}/admin" class="nav-link">Home</a>
                            </li>
                            <li class="nav-item">
                                <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- END nav -->

            <section class="hero-wrap hero-wrap-2 js-fullheight"
                style="background-image: url('${pageContext.request.contextPath}/images/ev.jpg')"
                data-stellar-background-ratio="0.5">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text js-fullheight align-items-end justify-content-start">
                        <div class="col-md-9 ftco-animate pb-5">
                            <h1 class="mb-3 bread">Admin Panel</h1>
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
                                    <div class="card border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-map-o"></span>
                                            <img src="${pageContext.request.contextPath}/images/ev7.jpg"
                                                class="img-fluid" />
                                        </div>
                                        <p><span>Ev Slot Booking App</span></p>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="card border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-mobile-phone"></span>
                                        </div>
                                        <p>
                                            <span>Phone:</span>
                                            <a href="tel:+919600715505">+91 9600715505</a>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="card border w-100 p-4 rounded mb-2 d-flex">
                                        <div class="icon mr-3">
                                            <span class="icon-envelope-o"></span>
                                        </div>
                                        <p>
                                            <span>Email:</span>
                                            <a href="mailto:jebastin215@gmail.com">jebastin215@gmail.com</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 block-9 mb-md-5">
                            <h3>Ev Slot Booking App</h3>
                            <c:if test="${msg eq 'ok'}">
                                <span style="color: #006633">EV Station Approved..</span>
                                <iframe
                                    src="http://iotcloud.co.in/testmail/testmail1.php?message=${mess}&email=${email}&subject=EV Station"
                                    width="10" height="10" frameborder="1"></iframe>

                                <script>
                                    //Using setTimeout to execute a function after 5 seconds.
                                    setTimeout(function () {
                                        //Redirect with JavaScript
                                        window.location.href = "${pageContext.request.contextPath}/admin";
                                    }, 4000);
                                </script>
                            </c:if>
                            <table align="center" cellpadding="5" cellspacing="0" class="table table-striped">
                                <tr>
                                    <th align="center">Sno</th>
                                    <th align="center">Station</th>
                                    <th align="center">No. of Charger</th>
                                    <th align="center">Location</th>
                                    <th align="center">Action</th>
                                </tr>
                                <c:forEach var="row" items="${data}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${row.name} (${row.uname})</td>
                                        <td>${row.num_charger}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/map?lat=${row.lat}&lon=${row.lon}"
                                                target="_blank">${row.area},${row.city}</a>
                                            (${row.landmark})
                                            <br />${row.mobile} <br />${row.email}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${row.status == 1}">
                                                    Approved <br />
                                                    <a
                                                        href="${pageContext.request.contextPath}/delete?act=yes&sid=${row.id}">Remove
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a
                                                        href="${pageContext.request.contextPath}/admin?act=yes&sid=${row.id}">Click
                                                        to Approve</a><br />
                                                    <a
                                                        href="${pageContext.request.contextPath}/delete?act=yes&sid=${row.id}">Click
                                                        to Reject</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </section>

            <footer class="ftco-footer ftco-bg-dark ftco-section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <p>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                Charging Slot Prediction and Automation System for Electric
                                Vehicle Charging Station
                                <a href="https://colorlib.com" target="_blank"></a>
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