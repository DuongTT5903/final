/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.exam;


import controller.auth.BaseRequiredLecturerAuthenticationController;
import dal.ExamDBContext;
import dal.StudentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Lecturer;
import model.LecturerInfo;
import model.Student;
import model.User;

/**
 *
 * @author admin
 */
public class UpdateProfileLecturer extends BaseRequiredLecturerAuthenticationController {



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
 

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
      
  
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
     
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
            LecturerInfo  updatedInfo = new LecturerInfo();
            updatedInfo.setLid(id);
            updatedInfo.setGender(gender);
            updatedInfo.setMail(email);
            updatedInfo.setDob(dob);
            updatedInfo.setPhone(phone);
            updatedInfo.setPlace(place);

            // Call DAL method to update student information
            ExamDBContext dbContext = new ExamDBContext(); 
            dbContext.updateLecturerInfo(updatedInfo);

            // Redirect to a confirmation page or back to the student profile page
            response.sendRedirect("/myAssignment/lecturer/profile");

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
    }

 


