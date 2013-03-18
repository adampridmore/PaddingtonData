<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Demo Data</h1>
<ul>
    <li>
        <a href="demoData/syncCustomerStatistics">Sync customer stats</a>
    </li>
    <li>
        <a href="demoData/reset">Reset Demo Data</a>
    </li>
    <li>
        <a href="demoData/clearAllData">Clear All Data</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=1000">Create 1,000 random routes</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=10000">Create 10,000 random routes</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000">Create 100,000 random routes</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-01">Create 100,000 random routes for date 2012-01-01</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-02">Create 100,000 random routes for date 2012-01-02</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-03">Create 100,000 random routes for date 2012-01-03</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-04">Create 100,000 random routes for date 2012-01-04</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-05">Create 100,000 random routes for date 2012-01-05</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-06">Create 100,000 random routes for date 2012-01-06</a>
    </li>
    <li>
        <a href="demoData/simulateLoad?numberToCreate=100000&date=2012-01-07">Create 100,000 random routes for date 2012-01-07</a>
    </li>
</ul>

<h1>Customers<h1>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Asset Count</th>
            <th>RouteResult Count</th>
            <th>Database</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.numberOfAssets}</td>
                <td>${customer.numberOfRouteResults}</td>
                <td>TBD</td>
                <td>
                    <a href="customers/details/${customer.id}">view</a>
                    <a href="#edit/${customer.id}">edit</a>
                    <a href="#delete/${customer.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
