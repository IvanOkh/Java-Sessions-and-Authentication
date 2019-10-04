/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import models.AccountService;

/**
 *
 * @author 797462
 */
public class LoginServlet extends HttpServlet 
{

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
        {
            HttpSession session = request.getSession();
            
            String ex = request.getParameter("logout");
            
            String currentUser = (String) session.getAttribute("currentlyLoged");
         
            if (ex == null)
            {
                if(currentUser != null)
                {
                    response.sendRedirect("Home");
                }
                
                else
                {
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            }
            else if (ex.equals(""))
            {
                session.invalidate();
                request.setAttribute("inputException", "You have successfully logged out.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            
        }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        // get the input the user entered
        String name = request.getParameter("inputOne");//
        String pass = request.getParameter("inputTwo");//
        
        AccountService accServ = new AccountService();
       
        User userData = accServ.login(name, pass);

        if (userData == null) 
        {
            //display exception warning in ${tag} on login page
            request.setAttribute("inputException", "User info doesn't"+
                                                    " match our records");
            
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } 
        else 
        {
            HttpSession session = request.getSession();//
            session.setAttribute("currentlyLoged", userData.getUserName());
            //head him over to Home page
            response.sendRedirect("Home");
        }
            
        //getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);


    }

}
