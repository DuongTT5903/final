<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        input[type="text"] {
            width: 50px; /* Adjust width as needed */
            text-align: center;
        }
        input[type="submit"] {
            padding: 10px;
            background-color: #4CAF50; /* Green background */
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049; /* Darker green */
        }
    </style>
</head>
<body>
    <form action="take" method="POST">
        <table>
            <tr>
                <td></td>
                <c:forEach items="${requestScope.exams}" var="e">
                    <td>
                        ${e.assessment.name} (${e.assessment.weight}) <br/>
                        ${e.from}
                    </td>
                </c:forEach>
            </tr>
            <c:forEach items="${requestScope.students}" var="s">
                <tr>
                    <td>${s.name}</td>
                    <c:forEach items="${requestScope.exams}" var="e">
                        <td>
                            <input type="text" name="score${s.id}_${e.id}"
                                   <c:forEach items="${requestScope.grades}" var="g">
                                       <c:if test="${e.id eq g.exam.id and s.id eq g.student.id}">
                                           value="${g.score}"
                                       </c:if>
                                   </c:forEach>
                                   />
                            <input type="hidden" name="gradeid" value="${s.id}_${e.id}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <input type="hidden" name="cid" value="${param.cid}" />
        <input type="submit" value="Save"/>
    </form>
</body>
</html>
