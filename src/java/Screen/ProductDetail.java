package Screen;

import Manager.DBContext;
import ObjectModel.Feedback;
import ObjectModel.Product;
import ObjectModel.User;
import Security.ImgNameGenerator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductDetail extends HttpServlet {
    
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
            String amount=request.getParameter("quantity");
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
                                try (PreparedStatement pstm2 = con.prepareStatement("UPDATE cart SET quantity = quantity + ? WHERE userID = ? AND productID = ?");) {
                                    pstm2.setInt(1, Integer.parseInt(amount));
                                    pstm2.setInt(2, userID);
                                    pstm2.setString(3, productID);
                                    pstm2.executeUpdate();
                                    try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock - ? WHERE productID = ?");) {
                                        pstm3.setString(1, amount);
                                        pstm3.setString(2, productID);
                                        pstm3.executeUpdate();
                                    }
                                }
                            }else{
                                try (PreparedStatement pstm2 = con.prepareStatement("INSERT INTO cart (userID, productID,quantity) VALUES (?, ?, ?)");) {
                                    pstm2.setInt(1, userID);
                                    pstm2.setString(2, productID);
                                    pstm2.setString(3, amount);
                                    pstm2.executeUpdate();
                                    try (PreparedStatement pstm3 = con.prepareStatement("UPDATE product SET quantityInStock = quantityInStock - ? WHERE productID = ?");) {
                                        pstm3.setString(1, amount);
                                        pstm3.setString(2, productID);
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
                response.sendRedirect("http://localhost:8080/stbcStore/ProductListing?product=" + productID + "&action=details");

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("review".equals(action)){
            String UPLOAD_DIR;
            final String STORE = "http://localhost:8080/stbcStore/img/";

            HttpSession sesh=request.getSession();
            User user=(User)sesh.getAttribute("loggedinuser");
            String username="";
            if(user!=null){
                username=user.getUsername();
            }

            String reviewText=request.getParameter("reviewText");
            String productName=request.getParameter("title");
            String productID=request.getParameter("productID");
            String star=request.getParameter("star");
            UPLOAD_DIR = getServletContext().getRealPath("img");   
            String newImgPath="";
            Part reviewImage=request.getPart("reviewImage");
            if(reviewImage!=null&&reviewImage.getSize()>0){
                String fileName = getFileName(reviewImage);
        if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
            request.setAttribute("editError", "We only accept .png,.jpg or .jpeg");
            request.getRequestDispatcher("JSP/FrontPage/productdetails.jsp").forward(request, response);
            return;
        }
        String mimeType = getServletContext().getMimeType(fileName);
        if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
            request.setAttribute("editError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
            request.getRequestDispatcher("JSP/FrontPage/productdetails.jsp").forward(request, response);
            return; 
        }
        

            File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String newName = ImgNameGenerator.generate();
        File file = new File(uploadDir, newName);
        try (InputStream input = reviewImage.getInputStream()) {
            Files.copy(input, file.toPath());
        }
        newImgPath = STORE + newName;
            }
        try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("INSERT INTO feedback (productName,feedbackDetail,attachedImg,star,username,feedbackDate) VALUES (?, ?, ?, ?, ?, ?)");) {
            pstm.setString(1, productName);
            pstm.setString(2, reviewText);
            pstm.setString(3, newImgPath);
            pstm.setString(4, star);
            pstm.setString(5, username);
            pstm.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            pstm.executeUpdate();
            response.sendRedirect("http://localhost:8080/stbcStore/ProductListing?product=" + productID + "&action=details");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
            String productID=request.getParameter("productID");
            System.out.println(productID);
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM product WHERE productID = ?");) {
                pstm.setString(1, productID);
                ResultSet rs = pstm.executeQuery();
                if(rs.next()){
                    Product product = new Product();
                    product.oneProduct(rs);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("JSP/FrontPage/productdetails.jsp").forward(request, response);
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
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
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
