
package Screen;

import Manager.DBContext;
import ObjectModel.Order;
import ObjectModel.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyOrders extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesh = request.getSession();
        User user = (User) sesh.getAttribute("loggedinuser");
        String username = user.getUsername();
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("select * from orders where username=? limit ? offset ?")) {
            pstm.setString(1, username);
            pstm.setInt(2, ITEMS_PER_PAGE);
            pstm.setInt(3, offset);
            ResultSet rs = pstm.executeQuery();
            List<Order> myOrderList = Order.getOrder(rs);
            if (myOrderList.isEmpty()) {
                System.out.println("nothing here");
            }
            int totalMyOrders = getAllMyOrders(con,username);
            int totalPages = (int) Math.ceil((double) totalMyOrders / ITEMS_PER_PAGE);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("myOrderList", myOrderList);
            request.getRequestDispatcher("JSP/Dashboard/myorders.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getAllMyOrders(Connection con, String username) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM orders where username=?";
        try (PreparedStatement pstm = con.prepareStatement(countQuery);) {
            pstm.setString(1, username);
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
