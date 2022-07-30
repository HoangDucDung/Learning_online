package controller.management;

import dao.AccountDAO;
import dao.RoleDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
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

@WebServlet(name = "AccountEditController", urlPatterns = {"/management/accountedit"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class AccountEditController extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        AccountDAO AccountDAO = new AccountDAO();
        RoleDAO RoleDAO = new RoleDAO();
        ArrayList<Role> allRole_Name = RoleDAO.allRole_Name();
        Account Accountsearchby_ID = AccountDAO.searchby_ID(id);
        request.setAttribute("Accountsearchby_ID", Accountsearchby_ID);
        request.setAttribute("allRole_Name", allRole_Name);
        request.getRequestDispatcher("../view/account-edit.jsp").forward(request, response);

    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        AccountDAO accountDAO = new AccountDAO();
        
        int accountID = Integer.parseInt(request.getParameter("id"));
        Account Accountby_ID = accountDAO.searchby_ID(accountID);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        boolean gender = true;
        if (request.getParameter("gender").equalsIgnoreCase("female")) {
            gender = false;
        }
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int roleID = Integer.parseInt(request.getParameter("role"));
        boolean status = true;
        if (request.getParameter("status").equalsIgnoreCase("deactive")) {
            status = false;
        }
        String fileName = null;
        if (uploadFile(request).equals("")) {
            fileName = Accountby_ID.getProfilePictureUrl();
        } else {
            fileName = uploadFile(request);
        }
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        Account acc = new Account();

        acc.setAccountID(accountID);
        acc.setFirstName(firstName);
        acc.setLastName(lastName);
        acc.setGender(Gender.of(gender));
        acc.setPhone(phone);
        acc.setAddress(address);
        acc.setStatus(status);
        Role role = new Role();
        role.setId(roleID);
        acc.setRole(role);
        acc.setProfilePictureUrl(fileName);
        acc.setModifiedTime(timestamp);
        accountDAO.editAccount(acc);


        response.sendRedirect("../management/account");
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
