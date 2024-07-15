package Manager;

import Screen.Login;
import Manager.DBContext;
import ObjectModel.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Form;

public class OAuth extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String error = request.getParameter("error");
        if ("access_denied".equals(error)) {
            request.getRequestDispatcher("JSP/Login/login.jsp").forward(request, response);
        }
        String code = request.getParameter("code");
        String accessToken = getToken(code);
        OAuthUser user = getUserInfo(accessToken);
        HttpSession session = request.getSession();
        Connection connection = null;
        PreparedStatement checkExist = null;
        ResultSet rExist = null;
        try {
            connection = DBContext.getConnection();
            String oauthEmail = user.getEmail();
            checkExist = connection.prepareStatement(
                    "select username,googleid,oauthprovider, userid,username, accessLevel from user where email=?");
            checkExist.setString(1, oauthEmail);
            rExist = checkExist.executeQuery();
            if (rExist.next()) {
                if (rExist.getString("oauthprovider").equals("google")
                        && rExist.getString("googleid").equals(user.getId())) {
                    request.setAttribute("loginstatus", "oauth success");
                    PreparedStatement pszz=connection.prepareStatement(
                    "select * from user where email=?");
                    pszz.setString(1, oauthEmail);
                    ResultSet rszz=pszz.executeQuery();
                    User userz = new User();
                    if(rszz.next()){
                        userz.summonUser(rszz);
                    }
                    session.setAttribute("loggedinuser", userz);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    request.setAttribute("loginstatus", "There is already an account with this email");
                    request.getRequestDispatcher("JSP/Login/login.jsp").forward(request, response);
                }
            } else {
                session.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/OAuthInfo/oauth.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(OAuthConst.GET_TOKEN)
                .bodyForm(Form.form().add("client_id", OAuthConst.CLIENT_ID)
                        .add("client_secret", OAuthConst.CLIENT_SECRET)
                        .add("redirect_uri", OAuthConst.REDIRECT_URI).add("code", code)
                        .add("grant_type", OAuthConst.GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }

    public static OAuthUser getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = OAuthConst.GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        return new Gson().fromJson(response, OAuthUser.class);
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
