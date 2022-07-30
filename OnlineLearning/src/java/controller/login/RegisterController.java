package controller.login;

import config.AppConfig;
import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.SendEmail;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})


public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {      
        
        String email = request.getParameter("email-address");
        String fname = request.getParameter("first-name");
        String lname = request.getParameter("last-name");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        boolean isExistAccount = new AccountDAO().isExistAccount(email);

        if (isExistAccount) {
            request.setAttribute("error", "Account has been exist. Please try again!");
            request.getRequestDispatcher("view/register.jsp").forward(request, response);
        } else {
            String subject = "Verify your account";
            String message = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head></head>\n"
                    + "<body style=\"color:#000;\">\n"
                    + "    <h3>Welcome to join Elearning</h3>\n"
                    + "    <p>Please click here to verify your account</p>\n"
                    + "    \n"
                    + "    <form id=\"myForm\" method=\"POST\" action=" + AppConfig.LINK_VERIFY + ">\n"
                    + "        <input type=\"hidden\" value=" + email + " id=\"email\" name=\"email\">\n"
                    + "        <input type=\"hidden\" value=" + fname + " id=\"f-name\" name=\"f-name\">\n"
                    + "        <input type=\"hidden\" value=" + lname + " id=\"l-name\" name=\"l-name\">\n"
                    + "        <input type=\"hidden\" value=" + gender + " id=\"gender\" name=\"gender\">\n"
                    + "        <input type=\"hidden\" value=" + phone + " id=\"phone\" name=\"phone\">\n"
                    + "        <input type=\"hidden\" value=" + password + " id=\"password\" name=\"password\">\n"
                    + "        <input type=\"submit\" value=\"Verify\" \n"
                    + "            style=\"padding: 10px 15px;color: #fff;background-color: rgb(0, 149, 255);border-radius: 10px;border:none\"\n"
                    + "        >\n"
                    + "    </form>\n"
                    + "\n"
                    + "    <h4>Thank you very much</h4>\n"
                    + "</body>\n"
                    + "</html>";

            SendEmail.sendMail(email, subject, message, AppConfig.USERNAME_EMAIL, AppConfig.PASSWORD_EMAIL);
            request.setAttribute("success", "Verification link has been sent to your email");
            request.getRequestDispatcher("view/register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
