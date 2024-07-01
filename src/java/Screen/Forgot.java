package Screen;

import Manager.DBContext;
import Manager.Email;
import Security.PassHash;
import Security.ResetCode;
import Security.Salt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Forgot extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Forgot</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Forgot at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        final String INPUTCODE = "JSP/Forgot/InputCode/input.jsp";
        HttpSession sesh = request.getSession();
        if ("Continue".equals(action)) {
            final String FORGET = "JSP/Forgot/forgot.jsp";
            String email = request.getParameter("email").trim();
            sesh.setAttribute("resetmail", email);
            String userID = "";
            Connection con;
            PreparedStatement pstm;
            ResultSet rs = null;

            try {
                con = DBContext.getConnection();
                pstm = con.prepareStatement("select userid from user where email=? and OAuthProvider='none'");
                pstm.setString(1, email);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    userID = rs.getString("userid");
                    ResetCode codeGen = new ResetCode();
                    String code = codeGen.generateCode();
                    Email sendMail = new Email();
                    sendMail.resetMail(email, code);
                    sesh.setAttribute("userIDR", userID);
                    sesh.setAttribute("thecode", code);
                    request.getRequestDispatcher(INPUTCODE).forward(request, response);
                } else {
                    sesh.setAttribute("nouser", "There is no user with this email");
                    request.getRequestDispatcher(FORGET).forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("Submit".equals(action)) {
            final String RESETAP = "JSP/Forgot/InputCode/Reset/reset.jsp";
            String code = request.getParameter("code").trim();
            String realCode = (String) sesh.getAttribute("thecode");
            if (code.equals(realCode)) {
                request.getRequestDispatcher(RESETAP).forward(request, response);
            } else {
                sesh.setAttribute("error", "Wrong code");
                request.getRequestDispatcher(INPUTCODE).forward(request, response);
            }
        }else if("Reset".equals(action)){
            String newPass=request.getParameter("password");
            String newSalt=Salt.generate();
            String newHashedPass=PassHash.hashPass(newPass,newSalt);
            String userID=(String) sesh.getAttribute("userIDR");
            int userIDR=Integer.parseInt(userID);
            final String LOGIN = "JSP/Login/login.jsp";
            final String LOGIN_STATUS = "loginstatus";
            try{
                Connection connection=DBContext.getConnection();
                PreparedStatement pstm=connection.prepareStatement("UPDATE user SET password = ?, salt=? WHERE userid = ?;");
                pstm.setString(1,newHashedPass);
                pstm.setString(2, newSalt);
                pstm.setInt(3, userIDR);
                int success=pstm.executeUpdate();
                if(success>0){
                    sesh.setAttribute(LOGIN_STATUS,"Reset password success. Proceed with login.");
                    request.getRequestDispatcher(LOGIN).forward(request, response);
                }else{
                    sesh.setAttribute(LOGIN_STATUS,"There was an error. Please try again later");
                    request.getRequestDispatcher(LOGIN).forward(request, response);
                }
            }catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
