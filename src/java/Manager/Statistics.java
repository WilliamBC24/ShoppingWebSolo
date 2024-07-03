/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Manager;

import Screen.Login;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonbui
 */
public class Statistics extends HttpServlet {

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
        response.setContentType("application/json");
        try (Connection con = DBContext.getConnection()) {
            double totalSales = fetchTotalSalesFromDB(con);
            double totalExpenses = fetchTotalExpensesFromDB(con);
            int totalVisitors = fetchTotalVisitorsFromDB(con);
            int totalOrders = fetchTotalOrdersFromDB(con);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("totalSales", totalSales);
            jsonObject.addProperty("totalExpenses", totalExpenses);
            jsonObject.addProperty("totalVisitors", totalVisitors);
            jsonObject.addProperty("totalOrders", totalOrders);

            response.getWriter().write(new Gson().toJson(jsonObject));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double fetchTotalSalesFromDB(Connection con) {
        String sql = "select sum(totalAmount) from orders";
        try (PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
                return rs.getDouble(1); 
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private double fetchTotalExpensesFromDB(Connection con) {
        // Database access logic here
        // Return the total sales value
        return 1000.0; // Dummy value for example
    }

    private int fetchTotalVisitorsFromDB(Connection con) {
        String sql = "select count(userid) from user";
        try (PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int fetchTotalOrdersFromDB(Connection con) {
        String sql = "select count(orderid) from orders";
        try (PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
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
        processRequest(request, response);
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
