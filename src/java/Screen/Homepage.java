package Screen;

import Manager.DBContext;
import ObjectModel.Post;
import ObjectModel.Product;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Homepage extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesh=request.getSession();
        User user=(User)sesh.getAttribute("loggedinuser");
        int userID=0;
        if(user!=null){
            userID=user.getUserID();
            try (Connection con=DBContext.getConnection();PreparedStatement cartCount = con.prepareStatement("select count(*) as count from cart where userID=?")) {
                cartCount.setInt(1, userID);
                try (ResultSet cartCountResult = cartCount.executeQuery()) {
                    if (cartCountResult.next()) {
                        sesh.setAttribute("itemincart", cartCountResult.getInt("count"));
                    }
                }
            }catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try(Connection con=DBContext.getConnection()){
            PreparedStatement getBanner=con.prepareStatement("select * from product where isActive<>0 order by numbersSold desc limit 3");
            ResultSet theBanners=getBanner.executeQuery();
            List<Product> banners=Product.getProduct(theBanners);
            PreparedStatement getProduct=con.prepareStatement("select * from product where isActive<>0 order by numbersSold desc limit 8 offset 3");
            ResultSet theProducts=getProduct.executeQuery();
            List<Product> products=Product.getProduct(theProducts);
            PreparedStatement getPost=con.prepareStatement("select * from post order by updatedDate desc limit 8");
            ResultSet thePosts=getPost.executeQuery();
            List<Post> posts=Post.getPost(thePosts);
            request.setAttribute("banners",banners);
            request.setAttribute("products",products);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("JSP/FrontPage/index.jsp").forward(request,response);
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
