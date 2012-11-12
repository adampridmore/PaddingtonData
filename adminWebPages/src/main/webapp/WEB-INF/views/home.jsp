<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Customers<h1>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.name}</td>
                <td>
                    <a href="#view/${customer.id}">view</a>
                    <a href="#edit/${customer.id}">edit</a>
                    <a href="#delete/${customer.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<h1>Demo Data</h1>

<a href="demoData/reset">Reset Demo Data</a>
