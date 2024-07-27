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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PostListing extends HttpServlet {
    
   private static final int ITEMS_PER_PAGE = 8;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");
        if("details".equals(action)){
            String postID=request.getParameter("post");
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM post WHERE postID = ?");) {
                pstm.setString(1, postID);
                
                ResultSet rs = pstm.executeQuery();

                if(rs.next()){
                    Post post = new Post();
                    post.onePost(rs);
                    request.setAttribute("post", post);
                    request.getRequestDispatcher("JSP/FrontPage/postdetails.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM post ORDER BY updatedDate DESC LIMIT ? OFFSET ?");) {
                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Post> postList = Post.getPost(rs);
                int totalPosts = getTotalPosts(con);
                int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);                    
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("postList", postList);
                request.getRequestDispatcher("JSP/FrontPage/post.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

    private int getTotalPosts(Connection con) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM post";
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
