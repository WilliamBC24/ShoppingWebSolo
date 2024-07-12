package Screen;

import Manager.DBContext;
import ObjectModel.Post;
import Security.SessionVerification;
import java.io.IOException;
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

public class PostManagement extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkSession(request, response);
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");
        String post = request.getParameter("post");
        if ("delete".equals(action)) {
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("delete from post where postID=?");) {
                ps.setString(1, post);
                int a = ps.executeUpdate();
                try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM post LIMIT ? OFFSET ?");) {
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    ResultSet rs = pstm.executeQuery();
                    List<Post> postList = Post.getPost(rs);
                    if (postList.isEmpty()) {
                        System.out.println("nothing herer");
                    }
                    int totalPosts = getTotalPosts(con);
                    int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("postList", postList);
                    request.getRequestDispatcher("JSP/Dashboard/post.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("edit".equals(action)) {

        } else if ("search".equals(action)) {
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);

            String search = request.getParameter("search");
            String sort = (request.getParameter("sort") == null || request.getParameter("sort").isEmpty()) ? "postID" : request.getParameter("sort");
            String order = (request.getParameter("order")==null||request.getParameter("order").isEmpty())?"ASC":request.getParameter("order");
            if (search == null || search.isEmpty()) {
                Connection con;
                PreparedStatement pstm;
                ResultSet rs;
                String sql = "SELECT * FROM post ORDER BY " + sort + " " + order + " LIMIT ? OFFSET ?";
                try {
                    con = DBContext.getConnection();
                    pstm = con.prepareStatement(sql);
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    rs = pstm.executeQuery();
                    List<Post> postList = Post.getPost(rs);
                    int totalPosts = getTotalPosts(con);
                    int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("postList", postList);
                    request.getRequestDispatcher("JSP/Dashboard/post.jsp").forward(request, response);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            String sql = "SELECT * FROM post WHERE title LIKE ?";
            sql += "ORDER BY " + sort + " " + order;
            ResultSet rs;
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
                ps.setString(1, '%' + search + '%');
                rs = ps.executeQuery();
                List<Post> postList = Post.getPost(rs);
                int totalPosts = getTotalPosts(con);
                int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("postList", postList);
                request.getRequestDispatcher("JSP/Dashboard/post.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM post LIMIT ? OFFSET ?");) {
                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Post> postList = Post.getPost(rs);
                if (postList.isEmpty()) {
                    System.out.println("nothing herer");
                }
                int totalPosts = getTotalPosts(con);
                int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("postList", postList);
                request.getRequestDispatcher("JSP/Dashboard/post.jsp").forward(request, response);
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
