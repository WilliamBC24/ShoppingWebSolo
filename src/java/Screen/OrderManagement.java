/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Screen;

import Manager.DBContext;
import ObjectModel.Order;
import ObjectModel.Post;
import Security.SessionVerification;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author sonbui
 */
public class OrderManagement extends HttpServlet {
    private static final int ITEMS_PER_PAGE = 5;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkSession(request, response);
        Connection con;
        PreparedStatement pstm;
        ResultSet rs;
        String page=request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        try {
            con = DBContext.getConnection();
            pstm = con.prepareStatement("SELECT * FROM orders LIMIT ? OFFSET ?");
            pstm.setInt(1, ITEMS_PER_PAGE);
            pstm.setInt(2, offset);
            rs = pstm.executeQuery();
            List<Order> orderList = Order.getOrder(rs);
            if(orderList.isEmpty()){
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

    private int getTotalOrders(Connection con) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM orders";
        try (PreparedStatement pstm = con.prepareStatement(countQuery);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String order = request.getParameter("order");
        if ("delete".equals(action)) {
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("delete from orders where orderID=?");) {
                ps.setString(1, order);
                int a = ps.executeUpdate();
                processRequest(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("edit".equals(action)) {
            processRequest(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
