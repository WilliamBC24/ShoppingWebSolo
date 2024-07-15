
package Screen;

import Manager.DBContext;
import ObjectModel.Order;
import ObjectModel.OrderDetails;
import ObjectModel.User;
import Security.SessionVerification;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyOrders extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkSession(request,response);
        HttpSession sesh = request.getSession();
        User user = (User) sesh.getAttribute("loggedinuser");
        String username = user.getUsername();
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action=request.getParameter("action");
        if("view".equals(action)){
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);

            String usernamez = request.getParameter("usernamez");
            request.setAttribute("usernamez", usernamez);
            String orderID = request.getParameter("orderID");
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM orderdetails where orderID=? LIMIT ? OFFSET ?")) {
                ps.setString(1, orderID);
                ps.setInt(2, ITEMS_PER_PAGE);
                ps.setInt(3, offset);
                ResultSet rs = ps.executeQuery();
                List<OrderDetails> orderDetails = OrderDetails.getOrderDetails(rs);
                List<String> images = new ArrayList<>();
                PreparedStatement psi = con.prepareStatement("SELECT p.productImg FROM orderdetails od JOIN product p ON od.productName = p.title where orderID=? LIMIT ? OFFSET ?");
                psi.setString(1, orderID);
                psi.setInt(2, ITEMS_PER_PAGE);
                psi.setInt(3, offset);
                ResultSet rsi = psi.executeQuery();
                while (rsi.next()) {
                    String productImg = rsi.getString("productImg");
                    images.add(productImg);
                    System.out.println("Product Image Path: " + productImg);
                }
                
                double totalAmount=0.0;
                PreparedStatement getPrice=con.prepareStatement("select round(sum(price*amount),2) from orderdetails where orderID=?");
                getPrice.setString(1,orderID);
                ResultSet price=getPrice.executeQuery();
                if(price.next()){
                    totalAmount=price.getDouble(1);
                }
                request.setAttribute("totalAmount",totalAmount);
                
                int totalOrders = getTotalOrdersView(con,orderID);
                int totalPages = (int) Math.ceil((double) totalOrders / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("orderDetails", orderDetails);
                request.setAttribute("images", images);
                request.getRequestDispatcher("JSP/Dashboard/myorderdetails.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
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
        
    }
private int getTotalOrdersView(Connection con,String orderID) throws SQLException {
        String countQuery = "SELECT count(*) FROM orderdetails where orderID=?";
        try (PreparedStatement pstm = con.prepareStatement(countQuery); ) {
            pstm.setString(1,orderID);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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
