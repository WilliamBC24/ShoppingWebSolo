package Screen;

import Manager.DBContext;
import ObjectModel.Feedback;
import ObjectModel.Product;
import ObjectModel.User;
import Security.ImgNameGenerator;
import Security.SessionVerification;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductManagement extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionVerification.checkStaff(request, response);
        String page = request.getParameter("page");
        int currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        int offset = (currentPage - 1) * ITEMS_PER_PAGE;
        request.setAttribute("currentPage", currentPage);
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String product = request.getParameter("productID");
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("update product set isActive=0 where productID=?");) {
                ps.setString(1, product);
                int a = ps.executeUpdate();
                try (PreparedStatement pstm = con.prepareStatement("SELECT * FROM product where isActive <> 0 order by title asc LIMIT ? OFFSET ? ");) {
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    ResultSet rs = pstm.executeQuery();
                    List<Product> productList = Product.getProduct(rs);
                    int totalProducts = getTotalProducts(con);
                    int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("productList", productList);
                    request.getRequestDispatcher("JSP/Dashboard/product.jsp").forward(request, response);

                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("edit".equals(action)) {
            HttpSession sesh = request.getSession();
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement("select * from product where productID=?")) {
                String productID = request.getParameter("productID");
                ps.setString(1, productID);
                ResultSet rs = ps.executeQuery();
                Product oneProduct = new Product();
                if (rs.next()) {
                    oneProduct.oneProduct(rs);
                }
                sesh.setAttribute("oneProduct", oneProduct);
                request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("editing".equals(action)) {
            String UPLOAD_DIR = request.getServletContext().getRealPath("img/productImg");;
            String STORE = "http://localhost:8080/stbcStore/img/productImg/";

            HttpSession sesh = request.getSession();
            Product product = (Product) sesh.getAttribute("oneProduct");
            int productID = product.getProductID();

            String title = request.getParameter("title");
            String details = request.getParameter("details");
            String quantityInStock = request.getParameter("quantityInStock");
            String priceIn = request.getParameter("priceIn");
            String priceOut = request.getParameter("priceOut");
            String gender = request.getParameter("gender");
            String season = request.getParameter("season");
            String category = request.getParameter("category");
            Part filePart = request.getPart("image");

            String filePath = "";
            List<String> fields = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                try (Connection test = DBContext.getConnection(); PreparedStatement pst = test.prepareStatement("select productID from product where title=? and isActive <> 0")) {
                    pst.setString(1, title);
                    ResultSet rst = pst.executeQuery();
                    if (rst.next()) {
                        request.setAttribute("addError", "Product title taken");
                        request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!title.equals(product.getTitle())) {
                    fields.add("title = ?");
                    values.add(title);
                } else {
                    request.setAttribute("editError", "You can't use old title");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    return;
                }
            }
            if (details != null && !details.isEmpty()) {
                fields.add("details = ?");
                values.add(details);
            }
            if (quantityInStock != null && !quantityInStock.isEmpty()) {
                try {
                    if (Integer.parseInt(quantityInStock) <= 0) {
                        request.setAttribute("addError", "Don't use number less than or equal to 0");
                        request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                        return;
                    } else {
                        fields.add("quantityInStock = ?");
                        values.add(quantityInStock);
                    }
                } catch (ServletException | IOException | NumberFormatException e) {
                    request.setAttribute("addError", "Wrong input");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    return;
                }
            }
            if (priceIn != null && !priceIn.isEmpty()) {
                try {
                    if (Integer.parseInt(priceIn) <= 0) {
                        request.setAttribute("addError", "Don't use number less than or equal to 0");
                        request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                        return;
                    } else {
                        fields.add("priceIn = ?");
                        values.add(priceIn);
                    }
                } catch (ServletException | IOException | NumberFormatException e) {
                    request.setAttribute("addError", "Wrong input");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    return;
                }
            }
            if (priceOut != null && !priceOut.isEmpty()) {
                try {
                    if (Integer.parseInt(priceOut) <= 0) {
                        request.setAttribute("addError", "Don't use number less than or equal to 0");
                        request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                        return;
                    } else {
                        fields.add("priceOut = ?");
                        values.add(priceOut);
                    }
                } catch (ServletException | IOException | NumberFormatException e) {
                    request.setAttribute("addError", "Wrong input");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    return;
                }
            }
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
                    request.setAttribute("addError", "We only accept .png,.jpg or .jpeg");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
                    return;
                }
                String mimeType = getServletContext().getMimeType(fileName);
                if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
                    request.setAttribute("addError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
                    request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
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
                fields.add("productImg = ?");
                values.add(filePath);
            }
            fields.add("gender = ?");
            values.add(gender);
            fields.add("season = ?");
            values.add(season);
            fields.add("category = ?");
            values.add(category);

            if (!fields.isEmpty()) {
                try {
                    String sql = "UPDATE product SET " + String.join(", ", fields) + " WHERE productid = ?";
                    values.add(productID);
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

            try (Connection conz = DBContext.getConnection(); PreparedStatement npsz = conz.prepareStatement("select * from product where productid=?");) {
                npsz.setInt(1, productID);
                ResultSet rsz = npsz.executeQuery();
                if (rsz.next()) {
                    Product product2 = new Product();
                    product2.oneProduct(rsz);
                    sesh.setAttribute("oneProduct", product2);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("editSuccess", "Update Success");
            request.getRequestDispatcher("JSP/Dashboard/editproduct.jsp").forward(request, response);
        } else if ("search".equals(action)) {
            HttpSession sesh = request.getSession();
            page = request.getParameter("page");
            currentPage = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
            offset = (currentPage - 1) * ITEMS_PER_PAGE;
            request.setAttribute("currentPage", currentPage);
            String search = request.getParameter("searchProduct");
            sesh.removeAttribute("searchProduct");
            if (search != null) {
                sesh.setAttribute("searchProduct", search);
            }

            String sort = (request.getParameter("sort") == null || request.getParameter("sort").isEmpty()) ? "title" : request.getParameter("sort");
            String order = (request.getParameter("order") == null || request.getParameter("order").isEmpty()) ? "ASC" : request.getParameter("order");
            if (search == null || search.isEmpty()) {
                Connection con;
                PreparedStatement pstm;
                ResultSet rs;
                String sql = "SELECT * FROM product where isActive <> 0 ORDER BY " + sort + " " + order + " LIMIT ? OFFSET ?";
                try {
                    con = DBContext.getConnection();
                    pstm = con.prepareStatement(sql);
                    pstm.setInt(1, ITEMS_PER_PAGE);
                    pstm.setInt(2, offset);
                    rs = pstm.executeQuery();
                    List<Product> productList = Product.getProduct(rs);
                    int totalProducts = getTotalProducts(con);
                    int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("productList", productList);
                    request.getRequestDispatcher("JSP/Dashboard/product.jsp").forward(request, response);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

            String sql = "SELECT * FROM product WHERE title LIKE ? and isActive <> 0 ";
            sql += "ORDER BY " + sort + " " + order;
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
                request.getRequestDispatcher("JSP/Dashboard/product.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("add".equals(action)) {
            String UPLOAD_DIR = request.getServletContext().getRealPath("img/productImg");;
            String STORE = "http://localhost:8080/stbcStore/img/productImg/";

            String title = request.getParameter("title");
            String details = request.getParameter("details");
            String quantityInStock = request.getParameter("quantityInStock");
            String priceIn = request.getParameter("priceIn");
            String priceOut = request.getParameter("priceOut");
            String gender = request.getParameter("gender");
            String season = request.getParameter("season");
            String category = request.getParameter("category");
            Part filePart = request.getPart("image");

            String filePath = "";

            if (title == null || title.isEmpty()
                    || details == null || details.isEmpty()
                    || quantityInStock == null || quantityInStock.isEmpty()
                    || priceIn == null || priceIn.isEmpty()
                    || priceOut == null || priceOut.isEmpty()
                    || filePart == null || filePart.getSize() == 0) {
                request.setAttribute("addError", "Fields shouldn't be empty");
                request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
            }
            try (Connection test = DBContext.getConnection(); PreparedStatement pst = test.prepareStatement("select productID from product where title=? and isActive <> 0")) {
                pst.setString(1, title);
                ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    request.setAttribute("addError", "Product title taken");
                    request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
                    return;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (Integer.parseInt(quantityInStock) <= 0 || Integer.parseInt(priceIn) <= 0 || Integer.parseInt(priceOut) <= 0) {
                    request.setAttribute("addError", "Don't use number less than or equal to 0");
                    request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
                    return;
                }
            } catch (ServletException | IOException | NumberFormatException e) {
                request.setAttribute("addError", "Wrong input");
                request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
            }

            String fileName = getFileName(filePart);
            if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
                request.setAttribute("addError", "We only accept .png,.jpg or .jpeg");
                request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
                return;
            }
            String mimeType = getServletContext().getMimeType(fileName);
            if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
                request.setAttribute("addError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
                request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
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

            String sql = "INSERT INTO product (title, priceIn, priceOut, numbersSold, details, quantityInStock, productImg, gender, season, category) VALUES (?, ?, ?, 0, ?, ?, ?, ?, ?, ?);";
            try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, priceIn);
                ps.setString(3, priceOut);
                ps.setString(4, details);
                ps.setString(5, quantityInStock);
                ps.setString(6, filePath);
                ps.setString(7, gender);
                ps.setString(8, season);
                ps.setString(9, category);
                ps.executeUpdate();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("addSuccess", "Add Success");
            request.getRequestDispatcher("JSP/Dashboard/addproduct.jsp").forward(request, response);
        } else if ("feedback".equals(action)) {
            String productName = request.getParameter("productName");
            request.setAttribute("productName",productName);
            int offsetFeedback = (currentPage - 1) * ITEMS_PER_PAGE;
            String sql = "select * from feedback where productName=? ORDER BY username ASC LIMIT ? OFFSET ?";
            try(Connection con=DBContext.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
                ps.setString(1,productName);
                ps.setInt(2,ITEMS_PER_PAGE);
                ps.setInt(3,offsetFeedback);
                ResultSet rs=ps.executeQuery();
                List<Feedback> feedbackList=Feedback.getFeedback(rs);
                int totalFeedback = getTotalFeedback(con,productName);
                int totalPages = (int) Math.ceil((double) totalFeedback / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("feedbackList", feedbackList);
                request.getRequestDispatcher("JSP/Dashboard/productfeedback.jsp").forward(request, response);
            }catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try (Connection con = DBContext.getConnection(); PreparedStatement pstm = con.prepareStatement("SELECT * FROM product WHERE isActive <> 0 ORDER BY title ASC LIMIT ? OFFSET ?");) {
                pstm.setInt(1, ITEMS_PER_PAGE);
                pstm.setInt(2, offset);
                ResultSet rs = pstm.executeQuery();
                List<Product> productList = Product.getProduct(rs);
                int totalProducts = getTotalProducts(con);
                int totalPages = (int) Math.ceil((double) totalProducts / ITEMS_PER_PAGE);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("productList", productList);
                request.getRequestDispatcher("JSP/Dashboard/product.jsp").forward(request, response);
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
    private int getTotalFeedback(Connection con,String productName) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM feedback where productName=?";
        try (PreparedStatement pstm = con.prepareStatement(countQuery); ) {
            pstm.setString(1,productName);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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
