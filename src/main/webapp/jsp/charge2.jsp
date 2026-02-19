<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>

        <c:choose>
            <c:when test="${csec <= 60}">

                Charging ${cmin}:${csec}
                <script>
                    setTimeout(function () {
                        window.location.href = '${pageContext.request.contextPath}/charge2?act1=chr&rid=${rid}';
                    }, 1000);
                </script>

            </c:when>
            <c:otherwise>

                <span class=style2>Charging Completed</span>

            </c:otherwise>
        </c:choose>
