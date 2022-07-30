package controller.login;

import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})


public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie emailCookie = getCookie(cookies, "cookemail");
        Cookie passCookie = getCookie(cookies, "cookpass");
        Cookie rememberCookie = getCookie(cookies, "cookrem");
        String email = (emailCookie != null) ? emailCookie.getValue() : "";
        String pass = (passCookie != null) ? passCookie.getValue() : "";
        String remember = (rememberCookie != null) ? rememberCookie.getValue() : "";

        request.setAttribute("emailCookie", email);
        request.setAttribute("passCookie", pass);
        request.setAttribute("rememberCookie", remember);

        request.getRequestDispatcher("view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailAddress = request.getParameter("email-address");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        Account account = new AccountDAO().getAccountForLogin(emailAddress, password);

        if (account == null) {
            request.setAttribute("error", "Email or password is incorrect");
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
        } else {
            if (remember != null) {
                Cookie cUserName = new Cookie("cookemail", emailAddress.trim());
                Cookie cPassword = new Cookie("cookpass", password.trim());
                Cookie cRemember = new Cookie("cookrem", remember.trim());
                cUserName.setMaxAge(60 * 60 * 24 * 15);
                cPassword.setMaxAge(60 * 60 * 24 * 15);
                cRemember.setMaxAge(60 * 60 * 24 * 15);
                response.addCookie(cUserName);
                response.addCookie(cPassword);
                response.addCookie(cRemember);
            }
            request.getSession().setAttribute("account", account);
            response.sendRedirect("home");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public Cookie getCookie(Cookie[] cookie, String name) {
        if (cookie == null) {
            return null;
        } else {
            for (Cookie ck : cookie) {
                if (ck.getName().equals(name)) {
                    return ck;
                }
            }
        }
        return null;
    }

}
