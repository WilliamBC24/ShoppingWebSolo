package Screen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Manager.DBContext;
import ObjectModel.User;
import Security.PassHash;
import Security.RegexCheck;
import Security.Salt;
import Security.SessionVerification;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagement extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkSession(request, response);
        HttpSession sesh=request.getSession();
        sesh.removeAttribute("userEditError");
        sesh.removeAttribute("userEditSuccess");
        Connection con;
        PreparedStatement pstm;
        ResultSet rs;
        try {
            con = DBContext.getConnection();
            pstm = con.prepareStatement("select * from user");
            rs = pstm.executeQuery();
            List<User> userList = User.getUser(rs);
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("username"));
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("delete from user where userID=?");) {
                ps.setInt(1, userID);
                int a = ps.executeUpdate();
                processRequest(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("edit".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("username"));
            HttpSession sesh = request.getSession();
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("select * from user where userID=?")) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                User userE = new User();
                if (rs.next()) {
                    userE.summonUser(rs);
                    sesh.setAttribute("editchosenuser", userE);
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
                processRequest(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("change".equals(action)) {
            HttpSession sesh = request.getSession();
            sesh.removeAttribute("userEditError");
            sesh.removeAttribute("userEditSuccess");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("rePassword");
            String gender = request.getParameter("gender");
            String phoneNumber = request.getParameter("phoneNumber");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String accessLevel = request.getParameter("accessLevel");
            User user = (User) sesh.getAttribute("editchosenuser");
            int userID = user.getUserID();
            Connection conC = null;
            PreparedStatement psC = null;
            ResultSet rsC = null;

            if (password != null && !password.isEmpty() || rePassword != null && !rePassword.isEmpty()) {
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
                if (!password.equals(rePassword)) {
                    sesh.setAttribute("userEditError", "Passwords should match");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
                if (!RegexCheck.match(password, passwordRegex)) {
                    sesh.setAttribute("userEditError", "Password must be 8-100 characters long, contains at least a letter, a number and a special character");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (email != null && !email.isEmpty()) {
                String emailRegex = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
                if (!RegexCheck.match(email, emailRegex)) {
                    sesh.setAttribute("userEditError", "Please use correct email format");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                String phoneRegex = "^\\d{7,12}$";
                if (!RegexCheck.match(phoneNumber, phoneRegex)) {
                    sesh.setAttribute("userEditError", "Please use correct phone format");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
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
                    sesh.setAttribute("userEditError", "Username already taken");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                } else {
                    if (username != null && !username.isEmpty()) {
                        if (!username.equals(user.getUsername())) {
                            fields.add("username = ?");
                            values.add(username);
                        } else {
                            sesh.setAttribute("userEditError", "You can't use old username");
                            sesh.removeAttribute("userEditSuccess");
                            request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                        }
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (email != null && !email.isEmpty()) {
                if (!email.equals(user.getEmail())) {
                    fields.add("email = ?");
                    values.add(email);
                } else {
                    sesh.setAttribute("userEditError", "You can't use old email");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
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
                    sesh.setAttribute("userEditError", "You can't use old password");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (gender != null && !gender.isEmpty()) {
                fields.add("gender = ?");
                values.add(gender);
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                if (!phoneNumber.equals(user.getPhoneNumber())) {
                    fields.add("phoneNumber = ?");
                    values.add(phoneNumber);
                } else {
                    sesh.setAttribute("userEditError", "You can't use old phone number");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (firstName != null && !firstName.isEmpty()) {
                if (!firstName.equals(user.getFirstName())) {
                    fields.add("firstName = ?");
                    values.add(firstName);
                } else {
                    sesh.setAttribute("userEditError", "You can't use old first name");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (lastName != null && !lastName.isEmpty()) {
                if (!lastName.equals(user.getLastName())) {
                    fields.add("lastName = ?");
                    values.add(lastName);
                } else {
                    sesh.setAttribute("userEditError", "You can't use old last name");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
            }
            if (accessLevel != null && !accessLevel.isEmpty()) {
                fields.add("accessLevel = ?");
                values.add(accessLevel);
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
            try (Connection conz = DBContext.getConnection(); PreparedStatement npsz = conz.prepareStatement("select * from user where userid=?");) {
            npsz.setInt(1, userID);
            ResultSet rsz = npsz.executeQuery();
            if (rsz.next()) {
                User user2 = new User();
                user2.summonUser(rsz);
                sesh.setAttribute("editchosenuser", user2);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
            sesh.setAttribute("userEditSuccess", "Update Success");
            sesh.removeAttribute("userEditError");
            request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
