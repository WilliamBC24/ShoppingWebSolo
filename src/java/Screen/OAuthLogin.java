package Screen;

import Manager.DBContext;
import Manager.Email;
import Manager.OAuthUser;
import ObjectModel.User;
import Security.SessionVerification;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OAuthLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.alreadyLoggedIn(request,response);
        Connection connection;
        final String LOGIN_STATUS = "loginstatus";
        final String LOGIN = "JSP/Login/login.jsp";
        final String DASH = "index.jsp";
        final String OAUTH_STATUS = "oauthstatus";
        final String OAUTH = "JSP/OAuthInfo/oauth.jsp";
        String username = request.getParameter("username").trim();
        String firstname = request.getParameter("firstname").trim();
        String lastname = request.getParameter("lastname").trim();
        String phonenumber = request.getParameter("phonenumber").trim();
        String gender = request.getParameter("gender");
        HttpSession session = request.getSession();
        OAuthUser user = (OAuthUser) session.getAttribute("user");
        String oauthID = user.getId();
        String oauthEmail = user.getEmail();
        String oauthPic = user.getPicture();
        if (user == null) {
            request.setAttribute(LOGIN_STATUS, "There was an error. Please try again later.");
            request.getRequestDispatcher(LOGIN).forward(request, response);
        }
        try {
            connection = DBContext.getConnection();
            PreparedStatement checkUser = connection.prepareStatement("select username from user where username=?");
            checkUser.setString(1, username);
            ResultSet rUser = checkUser.executeQuery();
            if (rUser.next()){
                if (rUser.getString("username").equals(username)) {
                    request.setAttribute(OAUTH_STATUS, "Username taken.");
                    request.getRequestDispatcher(OAUTH).forward(request, response);
                }
            }
            String addUser = "insert into user(username, email, gender, phonenumber, avatarImg, firstname, lastname, verifiedStatus, accessLevel, googleid, oauthprovider) values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement add = connection.prepareStatement(addUser);
            add.setString(1, username);
            add.setString(2, oauthEmail);
            add.setString(3, gender);
            add.setString(4, phonenumber);
            add.setString(5, oauthPic);
            add.setString(6, firstname);
            add.setString(7, lastname);
            add.setInt(8, 1);
            add.setInt(9, 1);
            add.setString(10, oauthID);
            add.setString(11, "google");
            int success = add.executeUpdate();
            if (success > 0) {
                Email sendEmail = new Email();
                sendEmail.welcomeEmail(oauthEmail);
                request.setAttribute(LOGIN_STATUS, "oauth success");
                PreparedStatement news=connection.prepareStatement("select * from user where username=?");
                news.setString(1,username);
                ResultSet rNew=news.executeQuery();
                if(rNew.next()){
                    User newUser=new User();
                    newUser.summonUser(rNew);
                    session.setAttribute("loggedinuser",newUser);
                }
                request.getRequestDispatcher(DASH).forward(request, response);
            } else {
                request.setAttribute(LOGIN_STATUS, "An error occured. Please try again later");
                request.getRequestDispatcher(LOGIN).forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request,response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
