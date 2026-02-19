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
            <script>
                (function () {
                    function pad2(n) { return String(n).padStart(2, '0'); }
                    function ceilToStep(value, step) { return Math.ceil(value / step) * step; }
                    function sameDateYMD(a, b) { return a === b; }

                    function rebuildSelect(selectEl, options, placeholder) {
                        const current = selectEl.value;
                        selectEl.innerHTML = '';
                        const ph = document.createElement('option');
                        ph.value = '';
                        ph.textContent = placeholder;
                        selectEl.appendChild(ph);
                        for (const opt of options) {
                            const o = document.createElement('option');
                            o.value = String(opt);
                            o.textContent = String(opt);
                            selectEl.appendChild(o);
                        }
                        // keep selection if still valid
                        if ([...selectEl.options].some(o => o.value === current)) {
                            selectEl.value = current;
                        } else {
                            selectEl.value = '';
                        }
                    }

                    function initBookingTimeFilters() {
                        const dateEl = document.querySelector('input[name="bdate"]');
                        const shEl = document.querySelector('select[name="t1"]');
                        const smEl = document.querySelector('select[name="t2"]');
                        const ehEl = document.querySelector('select[name="t3"]');
                        const emEl = document.querySelector('select[name="t4"]');
                        if (!dateEl || !shEl || !smEl || !ehEl || !emEl) return;

                        const serverNowH = parseInt('${now_h != null ? now_h : 0}', 10);
                        const serverNowM = parseInt('${now_m != null ? now_m : 0}', 10);
                        const serverToday = '${rdate}'; // YYYY-MM-DD

                        const allHours = Array.from({ length: 24 }, (_, i) => i);
                        const allMins = Array.from({ length: 12 }, (_, i) => i * 5);

                        function getMinStartMinuteForSelectedDate(selectedDateYMD, selectedHour) {
                            if (!sameDateYMD(selectedDateYMD, serverToday)) return 0;
                            const nowTotal = serverNowH * 60 + serverNowM;
                            const rounded = ceilToStep(nowTotal, 5);
                            const minHour = Math.floor(rounded / 60);
                            const minMinute = rounded % 60;
                            if (selectedHour < minHour) return 60; // no minutes valid
                            if (selectedHour > minHour) return 0;
                            return minMinute;
                        }

                        function refreshStartOptions() {
                            const selectedDate = dateEl.value;
                            let hours = allHours;
                            if (sameDateYMD(selectedDate, serverToday)) {
                                const rounded = ceilToStep(serverNowH * 60 + serverNowM, 5);
                                const minHour = Math.floor(rounded / 60);
                                hours = allHours.filter(h => h >= minHour);
                            }
                            rebuildSelect(shEl, hours, '-HH-');
                            // minutes depend on hour
                            const sh = shEl.value === '' ? null : parseInt(shEl.value, 10);
                            if (sh === null || Number.isNaN(sh)) {
                                rebuildSelect(smEl, allMins.map(pad2), '-MM-');
                                return;
                            }
                            const minM = getMinStartMinuteForSelectedDate(selectedDate, sh);
                            const mins = allMins.filter(m => m >= minM);
                            rebuildSelect(smEl, mins.map(pad2), '-MM-');
                        }

                        function refreshEndOptions() {
                            const sh = shEl.value === '' ? null : parseInt(shEl.value, 10);
                            const sm = smEl.value === '' ? null : parseInt(smEl.value, 10);
                            if (sh === null || sm === null || Number.isNaN(sh) || Number.isNaN(sm)) {
                                rebuildSelect(ehEl, allHours, '-HH-');
                                rebuildSelect(emEl, allMins.map(pad2), '-MM-');
                                return;
                            }
                            const endHours = allHours.filter(h => h >= sh);
                            rebuildSelect(ehEl, endHours, '-HH-');
                            const eh = ehEl.value === '' ? sh : parseInt(ehEl.value, 10);
                            let mins;
                            if (eh === sh) {
                                mins = allMins.filter(m => m > sm);
                            } else {
                                mins = allMins;
                            }
                            rebuildSelect(emEl, mins.map(pad2), '-MM-');
                        }

                        dateEl.addEventListener('change', function () {
                            refreshStartOptions();
                            refreshEndOptions();
                        });
                        shEl.addEventListener('change', function () {
                            refreshStartOptions();
                            refreshEndOptions();
                        });
                        smEl.addEventListener('change', refreshEndOptions);
                        ehEl.addEventListener('change', refreshEndOptions);

                        refreshStartOptions();
                        refreshEndOptions();
                    }

                    if (document.readyState === 'loading') {
                        document.addEventListener('DOMContentLoaded', initBookingTimeFilters);
                    } else {
                        initBookingTimeFilters();
                    }
                })();
            </script>
            <script language="javascript">
                function checkMode() {
                    if (document.form1.pay_mode.value == "Bank") {
                        document.getElementById("x1").style.display = "block";
                    }
                    else {
                        document.getElementById("x1").style.display = "none";
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
                            <li class="nav-item active"><a href="${pageContext.request.contextPath}/userhome"
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
            <!-- END nav -->

            <section class="hero-wrap hero-wrap-2 js-fullheight"
                style="background-image: url('${pageContext.request.contextPath}/images/ev.jpg');"
                data-stellar-background-ratio="0.5">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text js-fullheight align-items-end justify-content-start">
                        <div class="col-md-9 ftco-animate pb-5">

                            <h1 class="mb-3 bread">Book Slot</h1>
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
                                            <img src="${pageContext.request.contextPath}/images/ev7.jpg"
                                                class="img-fluid">
                                        </div>
                                        <p><span>Ev Slot Booking App</span></p>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="col-md-8 block-9 mb-md-5">
                            <h3>Booking</h3>
                            <c:if test="${msg eq 'fail'}">
                                <span style="color:#FF0000">Already Booked</span>
                            </c:if>
                            <c:if test="${not empty msg && msg ne 'fail'}">
                                <span style="color:#FF0000">${msg}</span>
                            </c:if>


                            <form action="${pageContext.request.contextPath}/book" method="post"
                                class="bg-light p-5 contact-form">

                                <div class="form-group">
                                    <label>Car No.</label>
                                    <input type="text" name="carno" class="form-control" required>
                                </div>
                                <div class="form-group">

                                    <!--
			 	<select name="t1" class="form-control">
						  <option value="">-HH-</option>
						  <option value="8">8 AM</option>
						  <option value="9">9 AM</option>
						  <option value="10">10 AM</option>
						  <option value="11">11 AM</option>
						  <option value="12">12 PM</option>
						  <option value="13">1 PM</option>
						  <option value="14">2 PM</option>
						  <option value="15">3 PM</option>
						  <option value="16">4 PM</option>
						  <option value="17">5 PM</option>
						  </select>-->
                                    <select class="form-control" name="reserve">
                                        <option value="1">Parking & Charging</option>
                                        <option value="2">Charge only</option>

                                    </select>
                                    <input type="hidden" name="sid" value="${sid}">
                                    <input type="hidden" name="slot" value="${slot}">
                                </div>

                                <div class="form-group">
                                    <label>Booking Date</label>
                                    <input type="date" name="bdate" class="form-control" value="${rdate}" required>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputUsername1">Start Time</label>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <select name="t1" class="form-control" required>
                                                <option value="">-HH-</option>
                                                <c:forEach var="t1Value" items="${tarr}">
                                                    <option>${t1Value}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <select name="t2" class="form-control" required>
                                                <option value="">-MM-</option>
                                                <option>00</option>
                                                <option>05</option>
                                                <option>10</option>
                                                <option>15</option>
                                                <option>20</option>
                                                <option>25</option>
                                                <option>30</option>
                                                <option>35</option>
                                                <option>40</option>
                                                <option>45</option>
                                                <option>50</option>
                                                <option>55</option>
                                            </select>
                                        </div>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="exampleInputUsername1">End Time</label>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <select name="t3" class="form-control" required>
                                                <option value="">-HH-</option>
                                                <c:forEach var="t2Value" items="${tarr}">
                                                    <option>${t2Value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <select name="t4" class="form-control" required>
                                                <option value="">-MM-</option>
                                                <option>00</option>
                                                <option>05</option>
                                                <option>10</option>
                                                <option>15</option>
                                                <option>20</option>
                                                <option>25</option>
                                                <option>30</option>
                                                <option>35</option>
                                                <option>40</option>
                                                <option>45</option>
                                                <option>50</option>
                                                <option>55</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="submit" value="Booking" class="btn btn-primary py-3 px-5">
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