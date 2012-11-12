<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<strong>Customers</strong>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.name}</td>
                <td><a href="#view/${customer.id}">view</a></td>
                <td><a href="#edit/${customer.id}">edit</a></td>
                <td><a href="#delete/${customer.id}">delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

