<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<strong>Hello World</strong>

<ul>

</ul>

<c:forEach var="customer" items="${customers}">
    <li>${customer.name}</li>
</c:forEach>


