package Screen;

import Manager.DBContext;
import ObjectModel.Feedback;
import ObjectModel.Product;
import ObjectModel.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductListing extends HttpServlet {
    
    private static final int ITEMS_PER_PAGE = 8;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");
        if("add".equals(action)){
            HttpSession sesh=request.getSession();
            String productID=request.getParameter("productID");
            User user=(User)sesh.getAttribute("loggedinuser");
            if(user==null){
                request.getRequestDispatcher("JSP/Login/login.jsp").forward(request, response);
                return;
            }
            int userID=user.getUserID();
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
                response.sendRedirect("ProductListing");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("details".equals(action)){
            HttpSession sesh=request.getSession();
            User user=(User)sesh.getAttribute("loggedinuser");
            String username="";
            if(user!=null){
                username=user.getUsername();
            }
            String productID=request.getParameter("product");
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM product WHERE productID = ?");) {
                pstm.setString(1, productID);
                
                ResultSet rs = pstm.executeQuery();

                if(rs.next()){
                    Product product = new Product();
                    product.oneProduct(rs);
                    request.setAttribute("product", product);
                    PreparedStatement canReview=null;
                    if(!username.equals("")){
                    canReview=con.prepareStatement("SELECT od.productName,od.orderID FROM orderdetails od INNER JOIN orders o ON od.orderID = o.orderID INNER JOIN product p ON od.productName = p.title WHERE o.username = ? AND p.productID = ?;");
                    canReview.setString(1,username);
                    canReview.setString(2,productID);
                    ResultSet yesReview=canReview.executeQuery();
                    if (yesReview.next()) {
                        
                        PreparedStatement checkReview=con.prepareStatement("SELECT * FROM feedback WHERE productName = ? AND username = ?");
                        checkReview.setString(1, product.getTitle());
                        checkReview.setString(2, username);
                        ResultSet rs1 = checkReview.executeQuery();
                        if(rs1.next()){
                            request.setAttribute("canReview", "no");
                        }else{
                        request.setAttribute("canReview", "yes");
                        }
                    } else {
                        request.setAttribute("canReview", "no"); 
                    }
                    }else{
                        request.setAttribute("canReview", "no");
                    }
                    PreparedStatement getName=con.prepareStatement("SELECT title FROM product WHERE productID = ?");
                getName.setString(1, productID);
                ResultSet rs1 = getName.executeQuery();
                String title="";
                if(rs1.next()){
                    title=rs1.getString("title");
                }
                PreparedStatement getFeedback=con.prepareStatement("SELECT * FROM feedback WHERE productName = ?");
                getFeedback.setString(1, title);
                ResultSet rs2 = getFeedback.executeQuery();
                List<Feedback> feedbackList = Feedback.getFeedback(rs2);
                request.setAttribute("feedbackList", feedbackList);
                
                    request.getRequestDispatcher("JSP/FrontPage/productdetails.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("search".equals(action)){
            String search=request.getParameter("search");
            String sql = "SELECT * FROM product WHERE title LIKE ? and isActive <> 0 ";
            System.out.println(sql);
            ResultSet rs;
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
                ps.setString(1, '%' + search + '%');
                rs = ps.executeQuery();
                List<Product> productList = Product.getProduct(rs);
                int totalProducts = getTotalProductsSearch(con, search);
                int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("productList", productList);
                request.getRequestDispatcher("JSP/FrontPage/product.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM product WHERE isActive <> 0 ORDER BY numbersSold ASC LIMIT ? OFFSET ?");) {
                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Product> productList = Product.getProduct(rs);
                int totalProducts = getTotalProducts(con);
                int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);                    
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("productList", productList);
                request.getRequestDispatcher("JSP/FrontPage/product.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    private int getTotalProductsSearch(Connection con, String search) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("SELECT count(*) FROM product WHERE title LIKE ? and isActive <> 0 ");) {
            pstm.setString(1, '%' + search + '%');
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    private int getTotalProducts(Connection con) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM product where isActive <> 0";
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
