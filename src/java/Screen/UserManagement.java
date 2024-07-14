package Screen;

import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagement extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkAdmin(request, response);
        HttpSession sesh = request.getSession();
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("username"));
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("delete from user where userID=?");) {
                ps.setInt(1, userID);
                int a = ps.executeUpdate();
                try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM user LIMIT ? OFFSET ?");) {
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    ResultSet rs = pstm.executeQuery();
                    List<User> userList = User.getUser(rs);
                    if (userList.isEmpty()) {
                        System.out.println("nothing herer");
                    }
                    int totalUsers = getTotalUsers(con);
                    int totalPages = (int) Math.ceil((double) totalUsers / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("userList", userList);
                    request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("edit".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("username"));
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("select * from user where userID=?")) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                User userE = new User();
                if (rs.next()) {
                    userE.summonUser(rs);
                    sesh.setAttribute("editchosenuser", userE);
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                }
                request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("change".equals(action)) {
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
            Connection conC;
            PreparedStatement psC;
            ResultSet rsC;

            if (password != null && !password.isEmpty() || rePassword != null && !rePassword.isEmpty()) {
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
                if (!password.equals(rePassword)) {
                    request.setAttribute("userEditError", "Passwords should match");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
                if (!RegexCheck.match(password, passwordRegex)) {
                    request.setAttribute("userEditError", "Password must be 8-100 characters long, contains at least a letter, a number and a special character");
                    sesh.removeAttribute("userEditSuccess");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
            }
            if (email != null && !email.isEmpty()) {
                String emailRegex = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
                if (!RegexCheck.match(email, emailRegex)) {
                    request.setAttribute("userEditError", "Please use correct email format");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                String phoneRegex = "^\\d{7,12}$";
                if (!RegexCheck.match(phoneNumber, phoneRegex)) {
                    request.setAttribute("userEditError", "Please use correct phone format");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
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
                    request.setAttribute("userEditError", "Username already taken");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                } else {
                    if (username != null && !username.isEmpty()) {
                        if (!username.equals(user.getUsername())) {
                            fields.add("username = ?");
                            values.add(username);
                        } else {
                            request.setAttribute("userEditError", "You can't use old username");
                            request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                            return;
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
                    request.setAttribute("userEditError", "You can't use old email");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
            }
            if (password != null && !password.isEmpty()) {
                String salt = user.getSalt();
                if (!PassHash.hashPass(password, salt).equals(user.getPassword())) {
                    String newSalt = Salt.generate();
                    String newPass = PassHash.hashPass(password, newSalt);
                    fields.add("salt=?");
                    values.add(newSalt);
                    fields.add("password = ?");
                    values.add(newPass);
                } else {
                    request.setAttribute("userEditError", "You can't use old password");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
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
                    request.setAttribute("userEditError", "You can't use old phone number");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
            }
            if (firstName != null && !firstName.isEmpty()) {
                if (!firstName.equals(user.getFirstName())) {
                    fields.add("firstName = ?");
                    values.add(firstName);
                } else {
                    request.setAttribute("userEditError", "You can't use old first name");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
                }
            }
            if (lastName != null && !lastName.isEmpty()) {
                if (!lastName.equals(user.getLastName())) {
                    fields.add("lastName = ?");
                    values.add(lastName);
                } else {
                    request.setAttribute("userEditError", "You can't use old last name");
                    request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
                    return;
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
            request.setAttribute("userEditSuccess", "Update Success");
            request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
        } else if ("search".equals(action)) {
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);

            String search = request.getParameter("searchUser");
            sesh.removeAttribute("searchUser");
            if (search != null) {
                sesh.setAttribute("searchUser", search);
            }
            String sort = (request.getParameter("sort") == null || request.getParameter("sort").isEmpty()) ? "username" : request.getParameter("sort");
            String order = (request.getParameter("order") == null || request.getParameter("order").isEmpty()) ? "ASC" : request.getParameter("order");
            if (search == null || search.isEmpty()) {
                Connection con;
                PreparedStatement pstm;
                ResultSet rs;
                String sql = "SELECT * FROM user ORDER BY " + sort + " " + order + " LIMIT ? OFFSET ? ";
                try {
                    con = DBContext.getConnection();
                    pstm = con.prepareStatement(sql);
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    rs = pstm.executeQuery();
                    List<User> userList = User.getUser(rs);
                    int totalUsers = getTotalUsers(con);
                    int totalPages = (int) Math.ceil((double) totalUsers / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("userList", userList);
                    request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            String sql = "SELECT * FROM user WHERE username LIKE ?";
            sql += "ORDER BY " + sort + " " + order;
            ResultSet rs;
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
                ps.setString(1, '%' + search + '%');
                rs = ps.executeQuery();
                List<User> userList = User.getUser(rs);
                int totalUsers = getTotalUsersSearch(con,search);
                int totalPages = (int) Math.ceil((double) totalUsers / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM user order by username asc LIMIT ? OFFSET ? ");) {
                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<User> userList = User.getUser(rs);
                if (userList.isEmpty()) {
                    System.out.println("nothing herer");
                }
                int totalUsers = getTotalUsers(con);
                int totalPages = (int) Math.ceil((double) totalUsers / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("JSP/Dashboard/user.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getTotalUsers(Connection con) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM user";
        try (PreparedStatement pstm = con.prepareStatement(countQuery); ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private int getTotalUsersSearch(Connection con, String search) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("SELECT count(*) FROM user WHERE username LIKE ?");) {
            pstm.setString(1, '%' + search + '%');
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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
