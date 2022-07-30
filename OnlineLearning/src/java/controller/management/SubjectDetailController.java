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
import model.Dimension;
import model.CoursePricePackage;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "SubjectDetailController", urlPatterns = {"/management/subject-detail"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class SubjectDetailController extends HttpServlet {
    
    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        AccountDAO accountDAO = new AccountDAO();
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        
        PricePackageDAO pricePackageDAO = new PricePackageDAO();
        ArrayList<CoursePricePackage> pricePackages = pricePackageDAO.getAllPricePackages(courseID);
        DimensionDAO dimensionDAO = new DimensionDAO();
        ArrayList<Dimension> dimensions = dimensionDAO.getDimensionsByCourseID(courseID);
        
        SubjectDAO subjectDAO = new SubjectDAO();
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getSubjectByCourseID(courseID);
        
        request.setAttribute("course", course);
        request.setAttribute("dimensions", dimensions);
        request.setAttribute("pricePackages", pricePackages);
        request.setAttribute("courseID", courseID);
        
        SubjectCategoryDAO subjectCategoryDAO = new SubjectCategoryDAO();
        ArrayList<SubjectCategory> subjectCategories = subjectCategoryDAO.getAllSubjectCategory();
        ArrayList<Subject> subjects = subjectDAO.getAllSubjects();
        ArrayList<Account> experts = accountDAO.getListExpert();
        
        request.setAttribute("experts", experts);
        request.setAttribute("subjectCategories", subjectCategories);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/view/subject-detail.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("id-deactive") != null || request.getParameter("id-active") != null) {
            changeStatusPricePackage(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("utf-8");
            editSubject(request, response);
        }
    }
    
    private void editSubject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int courseID = Integer.parseInt(request.getParameter("courseID"));
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
        course.setCourseId(courseID);
        course.setName(name);
        course.setDescription(description);
        String img = null;
        if (uploadFile(request).equals("")) {
            img = new CourseDAO().getSubjectByCourseID(courseID).getThumbnailUrl();
        } else {
            img = uploadFile(request);
        }
        course.setThumbnailUrl(img);
        course.setTinyPictureUrl(img);
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());
        course.setModifiedDate(date);
        course.setPrice(BigDecimal.ZERO);
        course.setFeatured(featured);
        course.setStatus(status);
        course.setVideoIntroduce(video);
        course.setAboutCourse(content);
        
        CourseDAO courseDAO = new CourseDAO();
        courseDAO.updateCourse(course);
        
        courseDAO.deleteObjectives(courseID);
        courseDAO.insertObjectives(objectives, courseID);
        courseDAO.deleteCategoryOfCourse(courseID);
        courseDAO.insertCourseToCategory(categoryID, courseID);
        
        AccountDAO accountDAO = new AccountDAO();
        if (expertCanAccess != null) {
            accountDAO.deleteAllAccountCanAccessSubject(courseID);
            accountDAO.insertListAccountCanAccessSubject(courseID, expertIDCanAccess);
        } else {
            accountDAO.deleteAllAccountCanAccessSubject(courseID);
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
    
    private void changeStatusPricePackage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PricePackageDAO pricePackageDAO = new PricePackageDAO();
        if (request.getParameter("id-deactive") != null) {
            pricePackageDAO.updateStatusPricePackage(Integer.parseInt(request.getParameter("id-deactive")), false);
        }
        if (request.getParameter("id-active") != null) {
            pricePackageDAO.updateStatusPricePackage(Integer.parseInt(request.getParameter("id-active")), true);
        }
        response.getWriter().flush();
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("dimensionID") != null) {
            int idDelete = Integer.parseInt(request.getParameter("dimensionID"));
            new DimensionDAO().deleteDimension(idDelete);
        } else {
            int idDelete = Integer.parseInt(request.getParameter("priceID"));
            new PricePackageDAO().deletePricePackage(idDelete);
        }
        response.setStatus(200);
        response.flushBuffer();
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
