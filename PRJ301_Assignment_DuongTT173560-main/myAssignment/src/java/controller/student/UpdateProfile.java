package controller.student;

import controller.auth.BaseRequiredStudentAuthenticationController;
import dal.StudentDBContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import model.StudentInfo;
import model.User;


public class UpdateProfile extends BaseRequiredStudentAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {
        // Handle GET requests if needed, though it seems you do not need to handle GET separately in your case.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {

  try {
            // Retrieve form data
            int id = Integer.parseInt(request.getParameter("id"));
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            Date dob = null;
            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(dobStr);
                dob = new Date(utilDate.getTime());
            }
            String phone = request.getParameter("phone");
            String place = request.getParameter("place");

            // Create a StudentInfo object with updated data
            StudentInfo updatedInfo = new StudentInfo();
            updatedInfo.setSid(id);
            updatedInfo.setGender(gender);
            updatedInfo.setMail(email);
            updatedInfo.setDob(dob);
            updatedInfo.setPhone(phone);
            updatedInfo.setPlace(place);

            // Call DAL method to update student information
            StudentDBContext dbContext = new StudentDBContext(); // Adjust how you get dbContext
            dbContext.updateStudentInfo(updatedInfo);

            // Redirect to a confirmation page or back to the student profile page
            response.sendRedirect("/myAssignment/student/profile");

        } catch (ParseException ex) {
            // Handle parsing exception if date format is incorrect
            response.getWriter().println("Error parsing date.");
        } catch (NumberFormatException ex) {
            // Handle number format exception if ID parsing fails
            response.getWriter().println("Error parsing ID.");
        } catch (Exception ex) {
            // Handle other exceptions
            response.getWriter().println("Error updating student information.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Update Student Profile Servlet";
    }
}
