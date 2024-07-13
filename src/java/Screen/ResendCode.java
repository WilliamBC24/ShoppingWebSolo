package Screen;

import Manager.Email;
import Security.ResetCode;
import Security.SessionVerification;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ResendCode extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.alreadyLoggedIn(request, response);
        HttpSession sesh = request.getSession();
        String email = (String) sesh.getAttribute("resetmail");
        ResetCode codeGen = new ResetCode();
        String code = codeGen.generateCode();
        Email sendMail = new Email();
        sendMail.resetMail(email, code);
        sesh.setAttribute("thecode", code);
        request.getRequestDispatcher("JSP/Forgot/InputCode/input.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
