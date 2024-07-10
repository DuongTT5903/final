/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.exam;

import controller.auth.BaseRequiredLecturerAuthenticationController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Lecturer;
import model.User;

/**
 *
 * @author duong
 */
public class HomeLecturerController extends BaseRequiredLecturerAuthenticationController{



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
        request.getRequestDispatcher("../view/exam/home.jsp").forward(request, response);    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer) throws ServletException, IOException {
       
    }
    
}
