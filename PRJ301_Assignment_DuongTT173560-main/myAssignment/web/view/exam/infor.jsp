<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Student Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 50%;
            max-width: 600px;
            text-align: left;
        }
        .form-container h1 {
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .form-container input[type="text"],
        .form-container input[type="email"],
        .form-container input[type="date"],
        .form-container input[type="tel"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container .readonly-field {
            margin-bottom: 10px;
            border: none;
            background-color: #f4f4f4;
            padding: 10px;
        }
        .form-container hr {
            margin: 20px 0;
        }
        .form-container .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .form-container button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container button.edit {
            background-color: #ff9800;
        }
    </style>
    <script>
        // JavaScript function to toggle edit mode
        function toggleEdit() {
            var inputs = document.querySelectorAll('.form-container input[type="text"], .form-container input[type="email"], .form-container input[type="date"], .form-container input[type="tel"]');
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].readOnly = !inputs[i].readOnly;
                if (!inputs[i].readOnly) {
                    inputs[i].classList.remove('readonly-field');
                } else {
                    inputs[i].classList.add('readonly-field');
                }
            }
        }
    </script>
</head>
<body>
    <div class="form-container">
        <h1>Edit Lecturer Information</h1>
        <form action="/myAssignment/exam/update" method="post">
            <c:forEach var="lecturer" items="${lecturerInfo}">
                <input type="hidden" name="id" value="${lecturer.id}" />
                <label>Name:</label>
                <p>${lecturer.name}</p>
                <label>Gender:</label>
                <input type="text" name="gender" value="${lecturer.lecturerInfo.gender}" class="readonly-field" readonly />
                <label>Email:</label>
                <input type="email" name="email" value="${lecturer.lecturerInfo.mail}" class="readonly-field" readonly />
                <label>Date of Birth:</label>
                <input type="date" name="dob" value="${lecturer.lecturerInfo.dob}" class="readonly-field" readonly />
                <label>Phone:</label>
                <input type="text" name="phone" value="${lecturer.lecturerInfo.phone}" class="readonly-field" readonly />
                <label>Place:</label>
                <input type="text" name="place" value="${lecturer.lecturerInfo.place}" class="readonly-field" readonly />
                <hr />
            </c:forEach>
            <div class="button-container">
                <button type="button" onclick="toggleEdit()" class="edit">Edit</button>
                <button type="submit">Save Changes</button>
            </div>
        </form>
    </div>
</body>
</html>
