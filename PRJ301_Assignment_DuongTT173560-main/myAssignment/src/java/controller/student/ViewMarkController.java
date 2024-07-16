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
                checkWeight += grade.getAssessment().getWeight() * 100;
            }
        }

        double averageGrade = 0.0;

        
        if (checkWeight == 100.0) { // Adjusted for multiplication by 100
            averageGrade = (totalWeight > 0) ? totalScore / totalWeight : 0.0;
        } else {
         
            averageGrade = 0.0; 
            
        }
      String formattedAverageGrade = String.format("%.2f", averageGrade);
        request.setAttribute("grades", grades);
        request.setAttribute("formattedAverageGrade", formattedAverageGrade);
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