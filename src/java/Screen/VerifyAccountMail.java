package Screen;

import Manager.Email;
import ObjectModel.User;
import Security.Token;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyAccountMail extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesh = request.getSession();
        User user = (User) sesh.getAttribute("loggedinuser");
        String email=user.getEmail();
        Token tokenGen = new Token();
        String token = tokenGen.generateToken();
        sesh.setAttribute("verifytoken",token);
        Email sendMail = new Email();
        sendMail.verifyMail(email, token);
        sesh.setAttribute("verifyToken", token);
        request.getRequestDispatcher("JSP/VerifyAccount/verify.jsp").forward(request, response);
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
