package controller.management;

import dao.BlogCategoryDAO;
import dao.BlogSubCategoryDAO;
import dao.LessonTypeDAO;
import dao.QuestionLevelDAO;
import dao.RoleDAO;
import dao.SettingDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import dao.TestTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BlogCategory;
import model.BlogSubCategory;
import model.LessonType;
import model.QuestionLevel;
import model.Role;
import model.Setting;
import model.Subject;
import model.SubjectCategory;
import model.TestType;

@WebServlet(name = "SettingAddController", urlPatterns = {"/management/setting-insert"})
public class SettingAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDAO settingDAO = new SettingDAO();
        BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
        SubjectDAO SubjectDAO = new SubjectDAO();
        SubjectCategoryDAO SubjectCategoryDAO = new SubjectCategoryDAO();
        
        ArrayList<BlogCategory> allBlogCategory = blogCategoryDAO.getAllBlogCategory();
        ArrayList<SubjectCategory> allSubjectCategorys = SubjectCategoryDAO.getAllSubjectCategory();
        ArrayList<Setting> allSettings_DistinctType = settingDAO.getAllSettings_DistinctType();
        request.setAttribute("allSettings_DistinctType", allSettings_DistinctType);
        request.setAttribute("allBlogCategory", allBlogCategory);
        request.setAttribute("allSubjectMainCategories", allSubjectCategorys);
        request.getRequestDispatcher("../view/setting-insert.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDAO settingDAO = new SettingDAO();
        String type = request.getParameter("type");
        int superblog = 0;
        int mainsubject = 0;
        if(type.equalsIgnoreCase("SUB_CATEGORY_POST")){
            superblog = Integer.parseInt(request.getParameter("superblog"));
        }
        
        if(type.equalsIgnoreCase("SUBJECT")){
            mainsubject = Integer.parseInt(request.getParameter("mainsubject"));
        }
        
        
        String name = request.getParameter("value");
        
        int order = Integer.parseInt(request.getParameter("order"));
        String status_raw = request.getParameter("status");
        Boolean status;
        if (status_raw.equalsIgnoreCase("active")) {
            status = true;
        } else {
            status = false;
        }
        Setting setting = new Setting();
        setting.setName(name);
        setting.setType(type);
        setting.setStatus(status);
        setting.setOrder(order);
        
        if (type.equalsIgnoreCase("TEST_TYPE")) {
            TestType testType = new TestType();
            testType.setTestTypeName(name);
            testType.setOrder(order);
            testType.setStatus(status);
            testType.setType(type);
            TestTypeDAO testTypeDAO = new TestTypeDAO();
            TestType TestTypeLast = testTypeDAO.getTestTypeLast();
            testTypeDAO.insertTestType(testType);
            setting.setId(TestTypeLast.getTestTypeID()+ 1);
            settingDAO.insertSetting(setting);
        }
        
        if (type.equalsIgnoreCase("LEVEL_QUESTION")) {
            QuestionLevel questionLevel = new QuestionLevel();
            questionLevel.setLevelName(name);
            questionLevel.setOrder(order);
            questionLevel.setStatus(status);
            questionLevel.setType(type);
            QuestionLevelDAO questionLevelDAO = new QuestionLevelDAO();
            QuestionLevel QuestionLevelLast = questionLevelDAO.getQuestionLevelLast();
            questionLevelDAO.insertQuestionLevel(questionLevel);
            setting.setId(QuestionLevelLast.getId() + 1);
            settingDAO.insertSetting(setting);
        }
        if (type.equalsIgnoreCase("TYPE_LESSON")) {
            LessonType lessonType = new LessonType();
            lessonType.setName(name);
            lessonType.setOrder(order);
            lessonType.setStatus(status);
            lessonType.setType(type);
            LessonTypeDAO lessonTypeDAO = new LessonTypeDAO();
            LessonType LessonTypeLast = lessonTypeDAO.getLessonTypeLast();
            lessonTypeDAO.insertLessonType(lessonType);
            setting.setId(LessonTypeLast.getLessonTypeID() + 1);
            settingDAO.insertSetting(setting);
        }
        if (type.equalsIgnoreCase("CATEGORY_POST")) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setName(name);
            blogCategory.setOrder(order);
            blogCategory.setStatus(status);
            blogCategory.setType(type);
            BlogCategoryDAO blogCategoryDAO = new BlogCategoryDAO();
            BlogCategory BlogCategoryLast = blogCategoryDAO.getBlogCategoryLast();
            blogCategoryDAO.insertBlogCategory(blogCategory);
            setting.setId(BlogCategoryLast.getBlogCategoryID() + 1);
            settingDAO.insertSetting(setting);
        }
        
        if (type.equalsIgnoreCase("SUB_CATEGORY_POST")) {
            BlogCategory blogCategory = new BlogCategory();
            BlogSubCategory blogSubCategory = new BlogSubCategory();
            
            blogCategory.setBlogCategoryID(superblog);
            
            blogSubCategory.setBlogCategoryId(blogCategory);
            blogSubCategory.setSubCategoryName(name);
            blogSubCategory.setOrder(order);
            blogSubCategory.setStatus(status);
            blogSubCategory.setType(type);
            
            BlogSubCategoryDAO blogSubCategoryDAO = new BlogSubCategoryDAO();
            BlogSubCategory BlogSubCategoryLast = blogSubCategoryDAO.getBlogSubCategoryLast();
            blogSubCategoryDAO.insertBlogSubCategory(blogSubCategory);
            setting.setId(BlogSubCategoryLast.getBlogSubCategoryId()+ 1);
            settingDAO.insertSetting(setting);
        }
        
        if (type.equalsIgnoreCase("SUBJECT")) {
            SubjectCategory SubjectCategory = new SubjectCategory();
            SubjectCategory.setCategoryID(mainsubject);
            
            Subject subject = new Subject();
            subject.setCategoryID(SubjectCategory);
            subject.setName(name);
            subject.setOrder(order);
            subject.setStatus(status);
            subject.setType(type);
            
            SubjectDAO subjectDAO = new SubjectDAO();
            Subject subjectLast = subjectDAO.getSubjectLast();
            subjectDAO.insertSubject(subject);
            setting.setId(subjectLast.getSubjectId()+ 1);
            settingDAO.insertSetting(setting);
        }
        
        if (type.equalsIgnoreCase("SUBJECT_CATEGORY")) {
            SubjectCategory subjectCategory = new SubjectCategory();
            
            subjectCategory.setName(name);
            subjectCategory.setOrder(order);
            subjectCategory.setStatus(status);
            subjectCategory.setType(type);
            
            SubjectCategoryDAO subjectCategoryDAO = new SubjectCategoryDAO();
            SubjectCategory SubjectCategoryLast = subjectCategoryDAO.getSubjectCategoryLast();
            subjectCategoryDAO.insertSubjectCategory(subjectCategory);
            setting.setId(SubjectCategoryLast.getCategoryID()+ 1);
            settingDAO.insertSetting(setting);
        }
        response.sendRedirect("../management/setting");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
