package Screen;

import Manager.Email;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomerSupport extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String message=request.getParameter("message");
        if(name==null&&name.isEmpty()||email==null&&email.isEmpty()||phone==null&&phone.isEmpty()||message==null&&message.isEmpty()){
            request.getRequestDispatcher("JSP/FrontPage/contact.jsp").forward(request, response);
        }
        request.getRequestDispatcher("JSP/FrontPage/contact.jsp").forward(request, response);
        Email mailer=new Email();
        mailer.sendSupportEmail(name,email,phone,message);
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
