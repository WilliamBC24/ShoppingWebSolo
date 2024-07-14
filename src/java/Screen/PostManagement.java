package Screen;

import Manager.DBContext;
import ObjectModel.Post;
import ObjectModel.User;
import Security.ImgNameGenerator;
import Security.SessionVerification;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostManagement extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 8;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkStaff(request, response);
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
            HttpSession sesh = request.getSession();
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("select * from post where postID=?")) {
                String postID = request.getParameter("postID");
                ps.setString(1, postID);
                ResultSet rs = ps.executeQuery();
                Post onePost = new Post();
                if (rs.next()) {
                    onePost.onePost(rs);
                }
                sesh.setAttribute("onePost", onePost);
                request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("editing".equals(action)) {
            String UPLOAD_DIR = request.getServletContext().getRealPath("img/postImg");;
            String STORE = "http://localhost:8080/stbcStore/img/postImg/";

            HttpSession sesh = request.getSession();
            Post onePost = (Post) sesh.getAttribute("onePost");
            int postID = onePost.getPostID();

            String title = request.getParameter("title");
            String detail = request.getParameter("detail");
            String category = request.getParameter("category");
            Part filePart = request.getPart("image");
            String fileName = getFileName(filePart);
            String filePath = "";
            List<String> fields = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                try (Connection test = DBContext.getConnection(); PreparedStatement pst = test.prepareStatement("select postID from post where title=?")) {
                    pst.setString(1, title);
                    ResultSet rst = pst.executeQuery();
                    if (rst.next()) {
                        request.setAttribute("editError", "Post title taken");
                        request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!title.equals(onePost.getTitle())) {
                    fields.add("title = ?");
                    values.add(title);
                } else {
                    request.setAttribute("editError", "You can't use old title");
                    request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
                    return;
                }
            }
            if (detail != null && !detail.isEmpty()) {
                fields.add("details = ?");
                values.add(detail);
            }
            
            if (filePart != null && filePart.getSize() > 0) {
                if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
                    request.setAttribute("editError", "We only accept .png,.jpg or .jpeg");
                    request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
                    return;
                }
                String mimeType = getServletContext().getMimeType(fileName);
                if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
                    request.setAttribute("editError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
                    request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
                    return;
                }
                String name = ImgNameGenerator.generate();
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                File file = new File(uploadDir, name);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath());
                }
                filePath = STORE + name;
                fields.add("postImg = ?");
                values.add(filePath);
            }
            
            fields.add("category = ?");
            values.add(category);
            
            if (!fields.isEmpty()) {
                try {
                    String sql = "UPDATE post SET " + String.join(", ", fields) + " WHERE postID = ?";
                    values.add(postID);
                    Connection con = DBContext.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql);
                    for (int i = 0; i < values.size(); i++) {
                        ps.setObject(i + 1, values.get(i));
                    }
                    ps.executeUpdate();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try (Connection conz = DBContext.getConnection(); PreparedStatement npsz = conz.prepareStatement("select * from post where postid=?");) {
                npsz.setInt(1, postID);
                ResultSet rsz = npsz.executeQuery();
                if (rsz.next()) {
                    Post post2 = new Post();
                    post2.onePost(rsz);
                    sesh.setAttribute("onePost", post2);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("editSuccess", "Update Success");
            request.getRequestDispatcher("JSP/Dashboard/editpost.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            String UPLOAD_DIR = request.getServletContext().getRealPath("img/postImg");;
            String STORE = "http://localhost:8080/stbcStore/img/postImg/";
            String title = request.getParameter("title");
            String detail = request.getParameter("detail");
            String category = request.getParameter("category");
            Part filePart = request.getPart("image");
            String fileName = getFileName(filePart);
            String filePath = "";

            HttpSession sesh = request.getSession();
            User user = (User) sesh.getAttribute("loggedinuser");
            int userID = user.getUserID();
            Date currentDate = Date.valueOf(LocalDate.now());

            if (title == null || title.isEmpty()
                    || detail == null || detail.isEmpty()
                    || filePart == null || filePart.getSize() == 0) {
                request.setAttribute("addError", "Fields shouldn't be empty");
                request.getRequestDispatcher("JSP/Dashboard/addpost.jsp").forward(request, response);
            }
            if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
                request.setAttribute("addError", "We only accept .png,.jpg or .jpeg");
                request.getRequestDispatcher("JSP/Dashboard/addpost.jsp").forward(request, response);
                return;
            }
            String mimeType = getServletContext().getMimeType(fileName);
            if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
                request.setAttribute("addError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
                request.getRequestDispatcher("JSP/Dashboard/addpost.jsp").forward(request, response);
                return;
            }
            try (Connection test = DBContext.getConnection(); PreparedStatement pst = test.prepareStatement("select postID from post where title=?")) {
                pst.setString(1, title);
                ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    request.setAttribute("addError", "Post title taken");
                    request.getRequestDispatcher("JSP/Dashboard/addpost.jsp").forward(request, response);
                    return;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            String name = ImgNameGenerator.generate();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File file = new File(uploadDir, name);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            }
            filePath = STORE + name;

            String sql = "INSERT INTO Post (userID, updatedDate, title, detail, category, postImg) VALUES (?, ?, ?, ?, ?, ?);";
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userID);
                ps.setDate(2, currentDate);
                ps.setString(3, title);
                ps.setString(4, detail);
                ps.setString(5, category);
                ps.setString(6, filePath);
                ps.executeUpdate();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("addSuccess", "Add Success");
            request.getRequestDispatcher("JSP/Dashboard/addpost.jsp").forward(request, response);
        } else if ("search".equals(action)) {
            HttpSession sesh = request.getSession();
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);

            String search = request.getParameter("searchPost");
            sesh.removeAttribute("searchPost");
            if (search != null) {
                sesh.setAttribute("searchPost", search);
            }
            String sort = (request.getParameter("sort") == null || request.getParameter("sort").isEmpty()) ? "postID" : request.getParameter("sort");
            String order = (request.getParameter("order") == null || request.getParameter("order").isEmpty()) ? "ASC" : request.getParameter("order");
            if (search == null || search.isEmpty()) {
                Connection con;
                PreparedStatement pstm;
                ResultSet rs;
                String sql = "SELECT * FROM post ORDER BY " + sort + " " + order + " LIMIT ? OFFSET ? ";
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
                int totalPosts = getTotalPostsSearch(con, search);
                int totalPages = (int) Math.ceil((double) totalPosts / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("postList", postList);
                request.getRequestDispatcher("JSP/Dashboard/post.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM post order by postID asc LIMIT ? OFFSET ?");) {
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

    private int getTotalPostsSearch(Connection con, String search) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("SELECT count(*) FROM post WHERE title LIKE ?");) {
            pstm.setString(1, '%' + search + '%');
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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
