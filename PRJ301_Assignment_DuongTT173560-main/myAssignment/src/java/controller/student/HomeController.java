/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.student;

import controller.auth.BaseRequiredStudentAuthenticationController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Student;
import model.User;

/**
 *
 * @author duong
 */
public class HomeController extends BaseRequiredStudentAuthenticationController {

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {

        request.getRequestDispatcher("/view/student/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student) throws ServletException, IOException {
       
    }
    
}
