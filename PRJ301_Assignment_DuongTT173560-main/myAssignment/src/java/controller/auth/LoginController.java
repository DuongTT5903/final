package controller.auth;

import dal.UserDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.Role;

public class LoginController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        UserDBContext dbContext = new UserDBContext();
        List<Role> roles = dbContext.getRoles();
        
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleidString = request.getParameter("roleid");
        int roleid = Integer.parseInt(roleidString);

        UserDBContext db = new UserDBContext();
        User user = db.getUserByUsernamePassword(username, password, roleid);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("roleid", roleid);

            // Redirect based on role ID
            if (roleid == 1) {
                response.sendRedirect("student/home");
            } else  if (roleid == 2)  {
                // Add other role-based redirections as needed
                      response.sendRedirect("lecturer/home");            }
        } else {
            response.getWriter().println("Login failed!");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
