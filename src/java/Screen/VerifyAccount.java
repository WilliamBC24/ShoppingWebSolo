package Screen;

import Manager.DBContext;
import ObjectModel.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifyAccount extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesh = request.getSession();
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        User user = (User) sesh.getAttribute("loggedinuser");
        String actualEmail = user.getEmail();
        String actualToken = (String) sesh.getAttribute("verifyToken");
        int userID = user.getUserID();
        if (email.equals(actualEmail) && token.equals(actualToken)) {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = DBContext.getConnection();
                ps = con.prepareStatement("update user set verifiedStatus=1 where email=?");
                ps.setString(1, email);
                ps.executeUpdate();
                ps = con.prepareStatement("select * from user where userid=?");
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User user2 = new User();
                    user2.summonUser(rs);
                    sesh.setAttribute("loggedinuser", user2);
                }
                request.getRequestDispatcher("JSP/Dashboard/profile.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
