package Screen;

import Manager.DBContext;
import ObjectModel.Order;
import Security.SessionVerification;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderManagement extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        SessionVerification.checkStaff(request, response);
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            String orderID = request.getParameter("orderID");
            String status = request.getParameter("status");
            Connection con = DBContext.getConnection();
            PreparedStatement ps = con.prepareStatement("update orders set status=? where orderID=?");
            ps.setString(1, status);
            ps.setString(2, orderID);
            ps.executeUpdate();
            try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM orders LIMIT ? OFFSET ?");) {

                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Order> orderList = Order.getOrder(rs);
                if (orderList.isEmpty()) {
                    System.out.println("nothing herer");
                }
                int totalOrders = getTotalOrders(con);
                int totalPages = (int) Math.ceil((double) totalOrders / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("orderList", orderList);
                request.getRequestDispatcher("JSP/Dashboard/order.jsp").forward(request, response);
            }
        } else if ("search".equals(action)) {
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);

            String search = request.getParameter("search");
            String sort = (request.getParameter("sort") == null || request.getParameter("sort").isEmpty()) ? "username" : request.getParameter("sort");
            String order = (request.getParameter("order")==null||request.getParameter("order").isEmpty())?"ASC":request.getParameter("order");
            if (search == null || search.isEmpty()) {
                Connection con;
                PreparedStatement pstm;
                ResultSet rs;
                String sql = "SELECT * FROM orders ORDER BY " + sort + " " + order + " LIMIT ? OFFSET ?";
                try {
                    con = DBContext.getConnection();
                    pstm = con.prepareStatement(sql);
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    rs = pstm.executeQuery();
                    List<Order> orderList = Order.getOrder(rs);
                    int totalOrders = getTotalOrders(con);
                    int totalPages = (int) Math.ceil((double) totalOrders / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("orderList", orderList);
                    request.getRequestDispatcher("JSP/Dashboard/order.jsp").forward(request, response);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            String sql = "SELECT * FROM orders WHERE username LIKE ?";
            sql += "ORDER BY " + sort + " " + order;
            ResultSet rs;
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
                ps.setString(1, '%' + search + '%');
                rs = ps.executeQuery();
                List<Order> orderList = Order.getOrder(rs);
                int totalOrders = getTotalOrders(con);
                int totalPages = (int) Math.ceil((double) totalOrders / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("orderList", orderList);
                request.getRequestDispatcher("JSP/Dashboard/order.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM orders LIMIT ? OFFSET ?");) {

                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Order> orderList = Order.getOrder(rs);
                if (orderList.isEmpty()) {
                    System.out.println("nothing herer");
                }
                int totalOrders = getTotalOrders(con);
                int totalPages = (int) Math.ceil((double) totalOrders / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("orderList", orderList);
                request.getRequestDispatcher("JSP/Dashboard/order.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getTotalOrders(Connection con) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM orders";
        try (PreparedStatement pstm = con.prepareStatement(countQuery); ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OrderManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OrderManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
