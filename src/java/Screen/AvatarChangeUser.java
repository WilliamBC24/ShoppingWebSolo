
package Screen;

import Manager.DBContext;
import ObjectModel.User;
import Security.ImgNameGenerator;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AvatarChangeUser extends HttpServlet {

    public String UPLOAD_DIR;
    public final String STORE = "http://localhost:8080/stbcStore/img/";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UPLOAD_DIR = request.getServletContext().getRealPath("img");
        HttpSession sesh = request.getSession();
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
            sesh.setAttribute("userEditError", "We only accept .png,.jpg or .jpeg");
            sesh.removeAttribute("userEditSuccess");
            request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
        }
        String mimeType = getServletContext().getMimeType(fileName);
        if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
            sesh.setAttribute("userEditError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
            sesh.removeAttribute("userEditSuccess");
            request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
            return; 
        }
        User user = (User) sesh.getAttribute("editchosenuser");
        int userID = user.getUserID();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String newName = ImgNameGenerator.generate();
        File file = new File(uploadDir, newName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }
        String newImgPath = STORE + newName;
        try {
            Connection con = DBContext.getConnection();
            PreparedStatement ps = con.prepareStatement("update user set avatarImg=? where userid=?");
            ps.setString(1, newImgPath);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DBContext.getConnection(); PreparedStatement nps = con.prepareStatement("select * from user where userid=?");) {
            nps.setInt(1, userID);
            ResultSet rs = nps.executeQuery();
            if (rs.next()) {
                User user2 = new User();
                user2.summonUser(rs);
                sesh.setAttribute("editchosenuser", user2);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sesh.setAttribute("userEditSuccess", "Update Success");
        sesh.removeAttribute("userEditError");
        request.getRequestDispatcher("JSP/Dashboard/edituser.jsp").forward(request, response);
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
