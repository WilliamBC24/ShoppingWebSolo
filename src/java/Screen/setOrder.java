package Screen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import Manager.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ObjectModel.Cart;
import ObjectModel.User;
import jakarta.servlet.http.HttpSession;


public class setOrder extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesh=request.getSession();
        User user=(User)sesh.getAttribute("loggedinuser");
        int userID=user.getUserID();
        String username=user.getUsername();
        try(Connection con =DBContext.getConnection()){
            String getProduct="SELECT * FROM cart WHERE userID=?";
            PreparedStatement pstm=con.prepareStatement(getProduct);
            pstm.setInt(1, userID);
            ResultSet rs=pstm.executeQuery();
            List<Cart> cartList=Cart.getCart(rs);
            
            String insertOrder="INSERT INTO orders (orderDate, totalAmount, status, username) VALUES (?, ?,?,?)";
            PreparedStatement pstm1=con.prepareStatement(insertOrder);
            pstm1.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            pstm1.setDouble(2, 0);
            pstm1.setInt(3, 0);
            pstm1.setString(4, username);
            pstm1.executeUpdate();
            int orderID=0;
            String getOrderID="SELECT MAX(orderID) FROM orders";
            PreparedStatement pstm4=con.prepareStatement(getOrderID);
            ResultSet rs4=pstm4.executeQuery();
            if(rs4.next()){
                orderID=rs4.getInt(1);
            }            

            for(Cart cart:cartList){
                int theID=cart.getProductID();
                String getProductName="SELECT c.productID, c.quantity, p.title, p.priceOut FROM cart c JOIN product p ON c.productID = p.productID WHERE c.productID=?;";
                PreparedStatement pstm2=con.prepareStatement(getProductName);
                pstm2.setInt(1, theID);
                ResultSet rs2=pstm2.executeQuery();
                while(rs2.next()){
                    String title=rs2.getString("title");
                    double price=rs2.getDouble("priceOut");
                    int quantity=rs2.getInt("quantity");
                    String insertOrderDetail="INSERT INTO orderdetails (orderID, amount, price, productName) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstm3=con.prepareStatement(insertOrderDetail);
                    pstm3.setInt(1, orderID);
                    pstm3.setInt(2, quantity);
                    pstm3.setDouble(3, price);
                    pstm3.setString(4, title);
                    pstm3.executeUpdate();
                }
           }
           double totalAmount=0.0;
                PreparedStatement getPrice=con.prepareStatement("select round(sum(price*amount),2) from orderdetails where orderID=?");
                getPrice.setInt(1,orderID);
                ResultSet price=getPrice.executeQuery();
                if(price.next()){
                    totalAmount=price.getDouble(1);
                }

                String updateTotal="UPDATE orders SET totalAmount=? WHERE orderID=?";
                PreparedStatement pstm6=con.prepareStatement(updateTotal);
                pstm6.setDouble(1, totalAmount);
                pstm6.setInt(2, orderID);
                pstm6.executeUpdate();

           String deleteCart="DELETE FROM cart WHERE userID=?";
           PreparedStatement pstm5=con.prepareStatement(deleteCart);
           pstm5.setInt(1, userID);
           pstm5.executeUpdate();
           request.getRequestDispatcher("JSP/Dashboard/cart.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
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
