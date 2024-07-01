package Screen;

import Manager.DBContext;
import ObjectModel.User;
import Security.PassHash;
import Security.RegexCheck;
import Security.Salt;
import Security.SessionVerification;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileManagement extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkSession(request, response);
        HttpSession sesh=request.getSession();
        sesh.removeAttribute("editError");
        sesh.removeAttribute("editSuccess");
        request.getRequestDispatcher("JSP/Dashboard/profile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesh = request.getSession();
        String action = request.getParameter("action");
        User user = (User) sesh.getAttribute("loggedinuser");
        int userID = user.getUserID();
        if ("infoChange".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("rePassword");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phoneNumber");
            String first = request.getParameter("firstName");
            String last = request.getParameter("lastName");
            sesh.removeAttribute("editError");
            sesh.removeAttribute("editSuccess");

            Connection conC = null;
            PreparedStatement psC = null;
            ResultSet rsC = null;

            if (password != null && !password.isEmpty() || rePassword != null && !rePassword.isEmpty()) {
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
                if (!password.equals(rePassword)) {
                    sesh.setAttribute("editError", "Passwords should match");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
                if (!RegexCheck.match(password, passwordRegex)) {
                    sesh.setAttribute("editError", "Password must be 8-100 characters long, contains at least a letter, a number and a special character");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            if (email != null && !email.isEmpty()) {
                String emailRegex = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
                if (!RegexCheck.match(email, emailRegex)) {
                    sesh.setAttribute("editError", "Please use correct email format");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            if (phone != null && !phone.isEmpty()) {
                String phoneRegex = "^\\d{7,12}$";
                if (!RegexCheck.match(phone, phoneRegex)) {
                    sesh.setAttribute("editError", "Please use correct phone format");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            List<String> fields = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            try {
                conC = DBContext.getConnection();
                psC = conC.prepareStatement("select userID from user where username=? and userID <> ?");
                psC.setString(1, username);
                psC.setInt(2, userID);
                rsC = psC.executeQuery();
                if (rsC.next()) {
                    sesh.setAttribute("editError", "Username already taken");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                } else {
                    if (username != null && !username.isEmpty()) {
                        if (!username.equals(user.getUsername())) {
                            fields.add("username = ?");
                            values.add(username);
                        } else {
                            sesh.setAttribute("editError", "You can't use old username");
        sesh.removeAttribute("editSuccess");
                            request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                        }
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                conC = DBContext.getConnection();
                psC = conC.prepareStatement("select userID from user where email=? and userID <> ?");
                psC.setString(1, email);
                psC.setInt(2, userID);
                rsC = psC.executeQuery();
                if (rsC.next()) {
                    sesh.setAttribute("editError", "Email already taken");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                } else {
                    if (email != null && !email.isEmpty()) {
                        if (!email.equals(user.getEmail())) {
                            fields.add("email = ?");
                            values.add(email);
                        } else {
                            sesh.setAttribute("editError", "You can't use old email");
        sesh.removeAttribute("editSuccess");
                            request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                        }
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (password != null && !password.isEmpty()) {
                String salt = user.getSalt();
                if (!PassHash.hashPass(password, salt).equals(user.getPassword())) {
                    String newSalt=Salt.generate();
                    String newPass=PassHash.hashPass(password,newSalt);
                    fields.add("salt=?");
                    values.add(newSalt);
                    fields.add("password = ?");
                    values.add(newPass);
                } else {
                    sesh.setAttribute("editError", "You can't use old password");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            if (gender != null && !gender.isEmpty()) {
                fields.add("gender = ?");
                values.add(gender);
            }
            if (phone != null && !phone.isEmpty()) {
                if (!phone.equals(user.getPhoneNumber())) {
                    fields.add("phoneNumber = ?");
                    values.add(phone);
                } else {
                    sesh.setAttribute("editError", "You can't use old phone number");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            if (first != null && !first.isEmpty()) {
                if (!first.equals(user.getFirstName())) {
                    fields.add("firstName = ?");
                    values.add(first);
                } else {
                    sesh.setAttribute("editError", "You can't use old first name");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }
            if (last != null && !last.isEmpty()) {
                if (!last.equals(user.getLastName())) {
                    fields.add("lastName = ?");
                    values.add(last);
                } else {
                    sesh.setAttribute("editError", "You can't use old last name");
        sesh.removeAttribute("editSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
                }
            }

            if (!fields.isEmpty()) {
                try {
                    String sql = "UPDATE user SET " + String.join(", ", fields) + " WHERE userid = ?";
                    values.add(userID);
                    Connection con = DBContext.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql);
                    for (int i = 0; i < values.size(); i++) {
                        ps.setObject(i + 1, values.get(i));
                    }
                    ps.executeUpdate();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try (Connection con = DBContext.getConnection(); PreparedStatement nps = con.prepareStatement("select * from user where userid=?");) {
            nps.setInt(1, userID);
            ResultSet rs = nps.executeQuery();
            if (rs.next()) {
                User user2 = new User();
                user2.summonUser(rs);
                sesh.setAttribute("loggedinuser", user2);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sesh.setAttribute("editSuccess", "Update Success");
        sesh.removeAttribute("editError");
        response.sendRedirect("JSP/Dashboard/editprofile.jsp");
//        request.getRequestDispatcher("ProfileManagement").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
