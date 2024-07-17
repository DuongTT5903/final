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
import java.util.ArrayList;
import model.Lecturer;
import model.Student;
import model.User;

/**
 *
 * @author admin
 */
public class viewProfileLecturer extends BaseRequiredLecturerAuthenticationController {



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
      
        int lid=lecturer.getId();
           ExamDBContext dbContext = new ExamDBContext(/* Pass your connection here */);
        ArrayList<Lecturer> lecturerInfo = dbContext.getLecturersInfoByID(lid);

        request.setAttribute("lecturerInfo", lecturerInfo);
        request.getRequestDispatcher("/view/exam/infor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
      
    }

 

}
