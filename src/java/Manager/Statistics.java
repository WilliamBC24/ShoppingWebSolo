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

public class Statistics extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (Connection con = DBContext.getConnection()) {
            String totalSales = fetchTotalSalesFromDB(con);
            String totalExpenses = fetchTotalExpensesFromDB(con);
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

    private String fetchTotalSalesFromDB(Connection con) {
        String sql = "SELECT FORMAT(SUM((priceOut * numbersSold) - (priceIn * quantityInStock)), 2) AS totalProfit FROM Product;";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    private String fetchTotalExpensesFromDB(Connection con) {
        String sql = "SELECT FORMAT(SUM((priceIn * quantityInStock)), 2) AS totalProfit FROM Product;";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    private int fetchTotalVisitorsFromDB(Connection con) {
        String sql = "select count(userid) from user";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
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
