<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
            <title>Alert</title>
        </head>

        <body>
            <c:if test="${msg1 eq '1'}">
                <h4>Waiting for Car - ${vno}</h4>
            </c:if>

            <c:if test="${msg2 eq '2'}">
                <h2>${mess}</h2>
                <iframe
                    src="http://iotcloud.co.in/testsms/sms.php?sms=emr&name=${name_q}&mess=${mess_q}&mobile=${mobile}"
                    width="10" height="10"></iframe>
            </c:if>

            <c:if test="${msg1 eq '3'}">
                <p align="center">Car: ${vno}
                    <br>
                    <a href="${pageContext.request.contextPath}/page2?rid2=${rid2}">Reschedule</a>
                </p>
            </c:if>


            <c:if test="${msg1 eq '4'}">
                <p align="center">Car: ${vno}
                    <br>
                    <a href="${pageContext.request.contextPath}/page2?rid2=${rid2}">Reschedule</a>
                </p>
            </c:if>
            <script>
                //Using setTimeout to execute a function after 5 seconds.
                setTimeout(function () {
                    //Redirect with JavaScript
                    window.location.href = '${pageContext.request.contextPath}/page';
                }, 10000);
            </script>
        </body>

        </html>
