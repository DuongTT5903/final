package controller.student;

import controller.auth.BaseRequiredStudentAuthenticationController;
import dal.GradeDBContext;
import model.Grade;
import model.Student;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ViewMarkController extends BaseRequiredStudentAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        GradeDBContext gradeDBContext = new GradeDBContext();
        int sid = student.getId(); // Assuming student object has an id field

        try {
            ArrayList<Grade> grades = gradeDBContext.listStudentGrades(sid);

            double totalScore = 0.0;
            double totalWeight = 0.0;

            for (Grade grade : grades) {
                totalScore += grade.getScore() * grade.getAssessment().getWeight();
                totalWeight += grade.getAssessment().getWeight();
            }

            double averageGrade = (totalWeight > 0) ? totalScore / totalWeight : 0.0;

            request.setAttribute("grades", grades);
            request.setAttribute("averageGrade", averageGrade);
            request.getRequestDispatcher("/view/student/mark.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error fetching grades", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
        // Handle POST requests if needed
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ViewMark Servlet";
    }
}
