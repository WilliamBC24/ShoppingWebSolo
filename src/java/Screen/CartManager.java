package Screen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

import Manager.DBContext;
import ObjectModel.Cart;
import ObjectModel.Product;
import ObjectModel.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class CartManager extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesh=request.getSession();  
        User user=(User)sesh.getAttribute("loggedinuser");
        int userID=user.getUserID();
        String action=request.getParameter("action");
        
        if("plus".equals(action)){
            String productID=request.getParameter("productID");
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT quantityInStock FROM product WHERE productID = ?");) {
                pstm.setString(1, productID);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    int stock=rs.getInt("quantityInStock");
                    if(stock>0){
                        try (PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM cart WHERE userID = ? AND productID = ?");) {
                            pstm1.setInt(1, userID);
                            pstm1.setString(2, productID);
                            ResultSet rs1 = pstm1.executeQuery(); 
                            if(rs1.next()){
                                try (PreparedStatement pstm2 = con.prepareStatement("UPDATE cart SET quantity = quantity + 1 WHERE userID = ? AND productID = ?");) {
                                    pstm2.setInt(1, userID);
                                    pstm2.setString(2, productID);
                                    pstm2.executeUpdate();
                                    try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock - 1 WHERE productID = ?");) {
                                        pstm3.setString(1, productID);
                                        pstm3.executeUpdate();
                                    }
                                }
                            }else{
                                try (PreparedStatement pstm2 = con.prepareStatement("INSERT INTO cart (userID, productID,quantity) VALUES (?, ?, 1)");) {
                                    pstm2.setInt(1, userID);
                                    pstm2.setString(2, productID);
                                    pstm2.executeUpdate();
                                    try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock - 1 WHERE productID = ?");) {
                                        pstm3.setString(1, productID);
                                        pstm3.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
                try (PreparedStatement cartCount = con.prepareStatement("select count(*) as count from cart where userID=?")) {
                    cartCount.setInt(1, userID);
                    try (ResultSet cartCountResult = cartCount.executeQuery()) {
                        if (cartCountResult.next()) {
                            sesh.setAttribute("itemincart", cartCountResult.getInt("count"));
                        }
                    }
                }
                response.sendRedirect("CartManager");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("minus".equals(action)){
            String productID=request.getParameter("productID");
            try (Connection con=DBContext.getConnection();PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM cart WHERE userID = ? AND productID = ?");) {
                pstm1.setInt(1, userID);
                pstm1.setString(2, productID);
                ResultSet rs1 = pstm1.executeQuery(); 
                if(rs1.next()){
                    try (PreparedStatement pstm2 = con.prepareStatement("UPDATE cart SET quantity = quantity - 1 WHERE userID = ? AND productID = ?");) {
                        pstm2.setInt(1, userID);
                        pstm2.setString(2, productID);
                        pstm2.executeUpdate();
                        try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock + 1 WHERE productID = ?");) {
                            pstm3.setString(1, productID);
                            pstm3.executeUpdate();
                        }
                        try(PreparedStatement pstm4=con.prepareStatement("DELETE FROM cart WHERE userID=? and quantity=0")){
                            pstm4.setInt(1, userID);
                            pstm4.executeUpdate();
                        }
                    }
                }
                response.sendRedirect("CartManager");
            }catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("remove".equals(action)){
            String productID=request.getParameter("productID");
            try (Connection con=DBContext.getConnection();PreparedStatement pstm1 = con.prepareStatement("SELECT quantity FROM cart WHERE userID = ? AND productID = ?");) {
                pstm1.setInt(1, userID);
                pstm1.setString(2, productID);
                ResultSet rs1 = pstm1.executeQuery(); 
                if(rs1.next()){
                    String quantity=rs1.getString("quantity");
                    try (PreparedStatement pstm2 = con.prepareStatement("delete from cart WHERE userID = ? AND productID = ?");) {
                        pstm2.setInt(1, userID);
                        pstm2.setString(2, productID);
                        pstm2.executeUpdate();
                        try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock + ? WHERE productID = ?");) {
                            pstm3.setString(1, quantity);
                            pstm3.setString(2, productID);
                            pstm3.executeUpdate();
                        }
                        try(PreparedStatement pstm4=con.prepareStatement("DELETE FROM cart WHERE userID=? and quantity=0")){
                            pstm4.setInt(1, userID);
                            pstm4.executeUpdate();
                        }
                    }
                }
                response.sendRedirect("CartManager");
            }catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try(Connection con=DBContext.getConnection();PreparedStatement ps=con.prepareStatement("select * from cart where userid=? and quantity>0")){
                ps.setInt(1, userID);
                ResultSet rs=ps.executeQuery();
                List<Cart> cartList=Cart.getCart(rs);
                List<Product> productList = new ArrayList<>();
    
            for (Cart cart : cartList) {
                int productID = cart.getProductID(); 
                PreparedStatement getProduct = con.prepareStatement("SELECT * FROM product WHERE productID = ? and isActive<>0");
                getProduct.setInt(1, productID);
                ResultSet productResult = getProduct.executeQuery();
                if (productResult.next()) {
                    Product product = new Product();
                    product.oneProduct(productResult);
                    productList.add(product);
                }
    
            }
            PreparedStatement total=con.prepareStatement("SELECT ROUND(SUM(c.quantity * p.priceOut),2) AS total FROM cart c JOIN product p ON c.productID = p.productID WHERE c.userID = ?;");
            total.setInt(1, userID);
            ResultSet totalResult=total.executeQuery();
            if(totalResult.next()){
                int realTotal=totalResult.getInt("total")*1000;
                request.setAttribute("total", totalResult.getInt("total"));
                request.setAttribute("realTotal", realTotal);
            }
            request.setAttribute("cartList", cartList);
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("JSP/Dashboard/cart.jsp").forward(request, response);
    
            }catch(Exception e){
                e.printStackTrace();
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
