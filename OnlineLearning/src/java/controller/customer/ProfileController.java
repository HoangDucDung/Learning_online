package controller.customer;

import dao.AccountDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Gender;

@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class ProfileController extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        AccountDAO accountDAO = new AccountDAO();
        String isNoti = "yes";
        request.setAttribute("isNoti", isNoti);
        Account oldAccount = (Account) request.getSession().getAttribute("account");
        int accountID = oldAccount.getAccountID();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        boolean gender = true;
        if (request.getParameter("gender").equalsIgnoreCase("female")) {
            gender = false;
        }
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String fileName = null;
        if (uploadFile(request).equals("")) {
            fileName = oldAccount.getProfilePictureUrl();
        } else {
            fileName = uploadFile(request);
        }
        Account acc = new Account();

        acc.setAccountID(accountID);
        acc.setFirstName(firstName);
        acc.setLastName(lastName);
        acc.setGender(Gender.of(gender));
        acc.setPhone(phone);
        acc.setAddress(address);
        acc.setProfilePictureUrl(fileName);
        accountDAO.editProfile(acc);

        Account accountUpdate = accountDAO.getAccount(oldAccount.getEmail(), oldAccount.getPassword());
        request.getSession().setAttribute("account", accountUpdate);

        request.getRequestDispatcher("view/profile.jsp").forward(request, response);
    }

    public String uploadFile(HttpServletRequest request) throws IOException, ServletException {
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

    public String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
