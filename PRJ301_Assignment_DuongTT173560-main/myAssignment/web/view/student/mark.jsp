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
    </style>
</head>
<body>
    <h2>Student Grades</h2>
    
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
    
</body>
</html>
