<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>EV Web - Reschedule</title>
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
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/userhome"
                                    class="nav-link">Home</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/station"
                                    class="nav-link">Station</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/tariff"
                                    class="nav-link">Tariff</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/history"
                                    class="nav-link">History</a></li>
                            <li class="nav-item"><a href="${pageContext.request.contextPath}/logout"
                                    class="nav-link">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <section class="hero-wrap hero-wrap-2 js-fullheight"
                style="background-image: url('${pageContext.request.contextPath}/images/ev.jpg');"
                data-stellar-background-ratio="0.5">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text js-fullheight align-items-end justify-content-start">
                        <div class="col-md-9 ftco-animate pb-5">
                            <h1 class="mb-3 bread">Reschedule</h1>
                        </div>
                    </div>
                </div>
            </section>

            <section class="ftco-section">
                <div class="container">
                    <div class="reschedule-card fade-in">
                        <span style="font-size: 3rem;">🔄</span>
                        <h3>Slot Rescheduling Required</h3>
                        <p class="reschedule-time">${retime}</p>

                        <div class="btn-group-reschedule">
                            <a href="${pageContext.request.contextPath}/page2?act=ok&rid2=${rid2}"
                                class="btn btn-electric">✓ Reschedule</a>
                            <a href="${pageContext.request.contextPath}/page2?act=cancel&rid2=${rid2}"
                                class="btn btn-electric-danger">✕ Cancel</a>
                        </div>
                    </div>

                    <c:if test="${msg eq 'go'}">
                        <div class="text-center fade-in" style="margin-top: 30px;">
                            <div class="alert-electric alert-electric-success" style="justify-content: center;">
                                ✓ Rescheduled! Redirecting in 5 seconds...
                            </div>
                            <div class="spinner-electric"></div>
                        </div>
                        <script>
                            setTimeout(function () {
                                window.location.href = '${pageContext.request.contextPath}/page3';
                            }, 5000);
                        </script>
                    </c:if>
                </div>
            </section>

            <footer class="ftco-footer ftco-bg-dark ftco-section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <p>© 2025 EV Charging Management System | Developed by Jebastin Augustin Ponraj. All Rights
                                Reserved.</p>
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
            <script src="${pageContext.request.contextPath}/js/main.js"></script>
        </body>

        </html>
