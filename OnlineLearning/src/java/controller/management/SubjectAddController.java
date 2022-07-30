package controller.management;

import dao.AccountDAO;
import dao.CourseDAO;
import dao.DimensionDAO;
import dao.PricePackageDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Course;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "SubjectAddController", urlPatterns = {"/management/subject-add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class SubjectAddController extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        AccountDAO accountDAO = new AccountDAO();

        SubjectCategoryDAO subjectCategoryDAO = new SubjectCategoryDAO();
        ArrayList<SubjectCategory> subjectCategories = subjectCategoryDAO.getAllSubjectCategory();
        SubjectDAO subjectDAO = new SubjectDAO();
        ArrayList<Subject> subjects = subjectDAO.getAllSubjects();
        ArrayList<Account> experts = accountDAO.getListExpert();

        request.setAttribute("experts", experts);
        request.setAttribute("subjects", subjects);
        request.setAttribute("subjectCategories", subjectCategories);
        request.getRequestDispatcher("/view/subject-add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");

        String name = request.getParameter("name");
        String[] categories = request.getParameterValues("categoryID");
        ArrayList<Integer> categoryID = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            String s = categories[i];
            categoryID.add(Integer.parseInt(s));
        }
        String[] expertCanAccess = null;
        ArrayList<Integer> expertIDCanAccess = new ArrayList<>();
        if (request.getParameterValues("expertCanAccess") != null) {
            expertCanAccess = request.getParameterValues("expertCanAccess");
            for (int i = 0; i < expertCanAccess.length; i++) {
                String expertCanAcces = expertCanAccess[i];
                expertIDCanAccess.add(Integer.parseInt(expertCanAcces));
            }
        }
        boolean featured = Boolean.parseBoolean(request.getParameter("featured"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String description = request.getParameter("description");
        String video = request.getParameter("video");
        String content = request.getParameter("content");
        String[] objectives = request.getParameterValues("objectives");

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setInstructorId(account);
        String img = uploadFile(request);
        course.setThumbnailUrl(img);
        course.setTinyPictureUrl(img);
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());
        course.setCreatedDate(date);
        course.setPrice(BigDecimal.ZERO);
        course.setFeatured(featured);
        course.setStatus(status);
        course.setVideoIntroduce(video);
        course.setAboutCourse(content);

        CourseDAO courseDAO = new CourseDAO();
        courseDAO.insertCourse(course);

        int idNewCourse = courseDAO.getNewestCourse().getCourseId();
        courseDAO.insertObjectives(objectives, idNewCourse);
        courseDAO.insertCourseToCategory(categoryID, idNewCourse);
        AccountDAO accountDAO = new AccountDAO();
        if (expertCanAccess != null) {
            accountDAO.insertListAccountCanAccessSubject(idNewCourse, expertIDCanAccess);
        }
        if (account.getRole().getId() == 1) {
            accountDAO.insertAccountCanAccessSubject(idNewCourse, account.getId());
        }

        response.sendRedirect("course");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
