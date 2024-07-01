/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Screen;

import Manager.DBContext;
import ObjectModel.User;
import Security.ImgNameGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonbui
 */
public class AvatarChange extends HttpServlet {

    public String UPLOAD_DIR;
    public final String STORE = "http://localhost:8080/stbcStore/img/";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UPLOAD_DIR = request.getServletContext().getRealPath("img");
        HttpSession sesh = request.getSession();
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg")) {
            sesh.setAttribute("editError", "We only accept .png,.jpg or .jpeg");
            sesh.removeAttribute("editSuccess");
            request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
            return;
        }
        String mimeType = getServletContext().getMimeType(fileName);
        if (mimeType == null || (!mimeType.equals("image/png") && !mimeType.equals("image/jpeg"))) {
            sesh.setAttribute("editError", "Invalid file type. We only accept .png, .jpg, or .jpeg files.");
            sesh.removeAttribute("editSuccess");
            request.getRequestDispatcher("JSP/Dashboard/editprofile.jsp").forward(request, response);
            return; 
        }
        User user = (User) sesh.getAttribute("loggedinuser");
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
                System.out.println(user2.getAvatarImg());
                sesh.setAttribute("loggedinuser", user2);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sesh.setAttribute("editSuccess", "Update Success");
        sesh.removeAttribute("editError");
        request.getRequestDispatcher("ProfileManagement").forward(request, response);
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
