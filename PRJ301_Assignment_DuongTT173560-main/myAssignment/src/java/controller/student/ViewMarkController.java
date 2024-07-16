package controller.student;

import controller.auth.BaseRequiredStudentAuthenticationController;
import dal.CourseDBContext;
import dal.GradeDBContext;
import model.Grade;
import model.Student;
import model.User;
import model.Course;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewMarkController extends BaseRequiredStudentAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        CourseDBContext courseDBContext = new CourseDBContext();
        int sid = student.getId(); // Assuming student object has an id field

        ArrayList<Course> courses = courseDBContext.getCoursesByStudent(sid);
        request.setAttribute("courses", courses); // Set courses in request attribute

        // Forward to JSP page to display courses and marks
        request.getRequestDispatcher("/view/student/mark.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
    // Handle POST requests if needed
    int sid = student.getId(); // Assuming student object has an id field
    int cid = Integer.parseInt(request.getParameter("cid"));

    GradeDBContext gradeDBContext = new GradeDBContext();

    try {
        ArrayList<Grade> grades = gradeDBContext.listStudentGrades(sid, cid);

        double totalScore = 0.0;
        double totalWeight = 0.0;
        double checkWeight = 0.0; // Initialize checkWeight

        for (Grade grade : grades) {
            if (grade.getAssessment() != null) {
                totalScore += grade.getScore() * grade.getAssessment().getWeight();
                totalWeight += grade.getAssessment().getWeight();
                checkWeight += grade.getAssessment().getWeight() * 100; // Multiply weight by 100
            }
        }

        double averageGrade = 0.0;

        // Check if checkWeight equals 100 before calculating averageGrade
        if (checkWeight == 100.0) { // Adjusted for multiplication by 100
            averageGrade = (totalWeight > 0) ? totalScore / totalWeight : 0.0;
        } else {
            // Handle the case where checkWeight is not 10000 (equivalent to 100 after multiplication by 100)
            // You might want to set averageGrade to some default value or handle it differently
            averageGrade = 0.0; // Set to 0 or handle as needed
            // You can also set an error message or log that weights do not sum up to 100%
            // request.setAttribute("error", "Assessment weights do not sum up to 100%");
        }

        request.setAttribute("grades", grades);
        request.setAttribute("averageGrade", averageGrade);
        request.getRequestDispatcher("/view/student/mark.jsp").forward(request, response);
    } catch (Exception e) {
        throw new ServletException("Error fetching grades", e);
    }
}



    @Override
    public String getServletInfo() {
        return "ViewMark Servlet";
    }
}
