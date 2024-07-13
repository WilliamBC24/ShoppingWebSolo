package Security;

import ObjectModel.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionVerification {

    public static void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedinuser") == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    public static void checkStaff(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedinuser");
        if (user == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            if (user.getAccessLevel() < 2) {
                request.getRequestDispatcher("ProfileManagement").forward(request, response);
            }
        }
    }

    public static void checkAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedinuser");
        if (user == null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            if (user.getAccessLevel() < 3) {
                request.getRequestDispatcher("ProfileManagement").forward(request, response);
            }
        }
    }

    public static void alreadyLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedinuser");
        if (user != null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
