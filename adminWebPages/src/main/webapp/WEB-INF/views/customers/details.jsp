<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h3>Customer</h3>

<table>
  <tr>
    <td>Id</td>
    <td>${customer.id}</td>
  </tr>
  <tr>
    <td>Name</td>
    <td>${customer.name}</td>
  </tr>
</table>

<h3>Assets</h3>

<table>
    <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="asset" items="${assets}">
            <tr>
                <td>${asset.id}</td>
                <td>${asset.name}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
