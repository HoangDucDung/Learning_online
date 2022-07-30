package controller.management;

import java.util.Random;
import config.AppConfig;
import dao.AccountDAO;
import dao.RoleDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Gender;
import model.Role;
import util.SendEmail;

@WebServlet(name = "AccountInsertController", urlPatterns = {"/management/accountinsert"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)


public class AccountInsertController extends HttpServlet {

    
    
    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        RoleDAO RoleDAO = new RoleDAO();
        ArrayList<Role> allRole_Name = RoleDAO.allRole_Name();
        request.setAttribute("allRole_Name", allRole_Name);
        request.getRequestDispatcher("../view/account-insert.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        String email = request.getParameter("email-address");
        String first_name = request.getParameter("first-name");
        String last_name = request.getParameter("last-name");
        int role = Integer.parseInt(request.getParameter("role"));
        boolean gender = true;
        if (request.getParameter("gender").equalsIgnoreCase("female")) {
            gender = false;
        }
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        boolean status = true;
        if (request.getParameter("status").equalsIgnoreCase("deactive")) {
            status = false;
        }
        String password = new String(generatePassword(6));
        String img = uploadFile(request);
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());

        Account account = new Account();
        account.setAccountID(accountDAO.getAccountLast().getAccountID() + 1);
        account.setFirstName(first_name);
        account.setLastName(last_name);
        account.setEmail(email);
        Role r = new Role();
        r.setId(role);
        account.setRole(r);
        account.setGender(Gender.of(gender));
        account.setPhone(phone);
        account.setAddress(address);
        account.setStatus(status);
        account.setPassword(password);
        account.setProfilePictureUrl(img);
        account.setCreatedTime(timestamp);
        double balance = 10000.00;
        BigDecimal tempBig = new BigDecimal(Double.toString(balance));
        account.setBalance(tempBig);

        boolean isExistAccount = new AccountDAO().isExistAccount(email);

        if (isExistAccount) {
            request.setAttribute("error", "Email has been exist. Please try again!");
            request.getRequestDispatcher("../view/account-insert.jsp").forward(request, response);
        } else {
            accountDAO.addAccount(account);
            Account account1 = accountDAO.getAccount(email, password);
            int accountid = account1.getAccountID();
            String subject = "Your information";
            String message = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head></head>\n"
                    + "<body style=\"color:#000;\">\n"
                    + "    <h3>Welcome to join Elearning</h3>\n"
                    + "    \n"
                    + "Your password is: " + password 
                    + "    \n"
                    + "    <p>Please click here to change your password</p>\n"
                    + "    \n"
                    + "    <form id=\"myForm\" method=\"GET\" action=" + AppConfig.LINK_CHANGE_PASSWORD_WHEN_ADD + ">\n"
                    + "        <input type=\"hidden\" value=" + accountid + " id=\"accountid\" name=\"accountid\">\n"
                    + "        <input type=\"submit\" value=\"Change\" \n"
                    + "            style=\"padding: 10px 15px;color: #fff;background-color: rgb(0, 149, 255);border-radius: 10px;border:none\"\n"
                    + "        >\n"
                    + "    </form>\n"
                    + "\n"
                    + "    <h4>Thank you very much</h4>\n"
                    + "</body>\n"
                    + "</html>";

            SendEmail.sendMail(email, subject, message, AppConfig.USERNAME_EMAIL, AppConfig.PASSWORD_EMAIL);
            request.setAttribute("success", "Add susscessfully !");
            request.getRequestDispatcher("../view/account-insert.jsp").forward(request, response);
        }

    }
    
    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);
            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }
    
    private static char[] generatePassword(int length) {
      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
      String numbers = "1234567890";
      String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
      Random random = new Random();
      char[] password = new char[length];

      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
      password[2] = numbers.charAt(random.nextInt(numbers.length()));
   
      for(int i = 3; i< length ; i++) {
         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
      }
      return password;
   }

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
