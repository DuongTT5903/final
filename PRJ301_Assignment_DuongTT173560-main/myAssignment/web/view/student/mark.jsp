<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Grades</title>
    <style>
        /* Add your CSS styles here */
        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ccc;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2 style="text-align: center;">Student Grades</h2>

    <form action="${pageContext.request.contextPath}/student/mark" method="post">
        <label for="courseSelect">Select Course:</label>
        <select id="courseSelect" name="cid">
            <c:forEach items="${courses}" var="course">
                <option value="${course.id}">${course.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="View Grades">
    </form>

    <c:if test="${not empty grades}">
        <table>
            <thead>
                <tr>
                    <th>Exam ID</th>
                    <th>Assessment Name</th>
                    <th>Score</th>
                    <th>Weight</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${grades}" var="grade">
                    <tr>
                        <td>${grade.exam.id}</td>
                        <td>${grade.assessment.name}</td>
                        <td>${grade.score}</td>
                        <td>${grade.assessment.weight}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="2"><strong>Average Grade:</strong></td>
                    <td colspan="2">${averageGrade}</td>
                </tr>
            </tbody>
        </table>
    </c:if>
</body>
</html>
