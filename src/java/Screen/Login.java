package Screen;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Security.PassHash;
import Manager.DBContext;
import Manager.Email;
import ObjectModel.User;
import Security.Salt;
import Security.SessionVerification;
import Security.Token;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.alreadyLoggedIn(request,response);
        String action = request.getParameter("action");
        if ("Login".equals(action)) {
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password");
            String salt;
            HttpSession session = request.getSession();
            Connection connection;
            try {
                connection = DBContext.getConnection();
                String stm = "select * from user where username=?";
                PreparedStatement pstm = connection.prepareStatement(stm);
                pstm.setString(1, username);
                ResultSet rs = pstm.executeQuery();
                final String LOGIN_STATUS = "loginstatus";
                final String INDEX = "Homepage";
                final String LOGIN = "JSP/Login/login.jsp";
                if (rs.next()) {
                    String storedPass = rs.getString("password");
                    salt = rs.getString("salt");
                    if (storedPass.equals(PassHash.hashPass(password, salt))) {
                        User user = new User();
                        user.summonUser(rs);
                        //select count  from cart where userID=this user id
                        try (PreparedStatement cartCount = connection.prepareStatement("select count(*) as count from cart where userID=?")) {
                            cartCount.setInt(1, user.getUserID());
                            try (ResultSet cartCountResult = cartCount.executeQuery()) {
                                if (cartCountResult.next()) {
                                    session.setAttribute("itemincart", cartCountResult.getInt("count"));
                                }
                            }
                        }
                        session.setAttribute("loggedinuser", user);
                        request.getRequestDispatcher(INDEX).forward(request, response);
                    } else {
                        request.setAttribute(LOGIN_STATUS, "Wrong password.");
                        request.getRequestDispatcher(LOGIN).forward(request, response);
                    }
                } else {
                    request.setAttribute(LOGIN_STATUS, "User does not exist. Please check your username.");
                    request.getRequestDispatcher(LOGIN).forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("Register".equals(action)) {
            Connection connection;
            String username = request.getParameter("username").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password");
            String salt;
            String hashedpass;
            String firstname = request.getParameter("firstname").trim();
            String lastname = request.getParameter("lastname").trim();
            String gender = request.getParameter("gender");
            String phonenumber = request.getParameter("phonenumber").trim();
            String token;

            salt = Salt.generate();
            hashedpass = PassHash.hashPass(password, salt);

            final String ERROR = "error";
            final String GOBACK = "JSP/Register/register.jsp";

            if (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other")) {
                request.setAttribute(ERROR, "Don't alter the form");
                request.getRequestDispatcher(GOBACK).forward(request, response);
            }

            try {
                connection = DBContext.getConnection();

                //select usernames, mails, phones and check, if exists then redirect and send error
                try (PreparedStatement check = connection.prepareStatement("select username,email from user where username=? or email=?")) {
                    check.setString(1, username);
                    check.setString(2, email);
                    try (ResultSet rs = check.executeQuery()) {
                        if (rs.next()) {
                            if (rs.getString("username").equals(username)) {
                                request.setAttribute(ERROR, "Username taken");
                                request.getRequestDispatcher(GOBACK).forward(request, response);
                            }
                            if (rs.getString("email").equals(email)) {
                                request.setAttribute(ERROR, "This email is already used by another account");
                                request.getRequestDispatcher(GOBACK).forward(request, response);
                            }
                        }
                    }
                }
                //add user after validating
                token = Token.generateToken();
                String addUser = "insert into user(username, password, salt, email, gender, phonenumber, avatarImg, firstname, lastname, verifyToken, verifiedStatus, accessLevel, googleID, OAuthProvider) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstm = connection.prepareStatement(addUser);
                pstm.setString(1, username);
                pstm.setString(2, hashedpass);
                pstm.setString(3, salt);
                pstm.setString(4, email);
                pstm.setString(5, gender);
                pstm.setString(6, phonenumber);
                pstm.setString(7, "/img/avatar.png");
                pstm.setString(8, firstname);
                pstm.setString(9, lastname);
                pstm.setString(10, token);
                pstm.setInt(11, 0);
                pstm.setInt(12, 1);
                pstm.setString(13, "none");
                pstm.setString(14, "none");
                int addSuccess = pstm.executeUpdate();
                if (addSuccess > 0) {
                    request.getRequestDispatcher("JSP/Login/login.jsp").forward(request,response);
                    Email sendEmail = new Email();
                    sendEmail.welcomeEmail(email);
                } else {
                    request.setAttribute(ERROR, "An error occured. Please try again");
                    request.getRequestDispatcher(GOBACK).forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            SessionVerification.alreadyLoggedIn(request,response);
            request.getRequestDispatcher("JSP/Login/login.jsp").forward(request, response);
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
