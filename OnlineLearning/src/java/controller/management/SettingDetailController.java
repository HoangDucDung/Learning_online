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

@WebServlet(name = "SettingDetailController", urlPatterns = {"/management/setting-detail"})
public class SettingDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int settingID = Integer.parseInt(request.getParameter("SettingID"));

        SettingDAO settingDAO = new SettingDAO();
        Setting searchby_value = settingDAO.searchby_SubID(settingID);
        ArrayList<Setting> allSettings_DistinctType = settingDAO.getAllSettings_DistinctType();
        request.setAttribute("allSettings_DistinctType", allSettings_DistinctType);
        request.setAttribute("searchby_value", searchby_value);
        request.getRequestDispatcher("../view/setting-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDAO settingDAO = new SettingDAO();
        RoleDAO RoleDAO = new RoleDAO();
        SubjectDAO SubjectDAO = new SubjectDAO();
        QuestionLevelDAO QuestionLevelDAO = new QuestionLevelDAO();
        LessonTypeDAO LessonTypeDAO = new LessonTypeDAO();
        BlogCategoryDAO BlogCategoryDAO = new BlogCategoryDAO();
        BlogSubCategoryDAO BlogSubCategoryDAO = new BlogSubCategoryDAO();
        TestTypeDAO TestTypeDAO = new TestTypeDAO();
        SubjectCategoryDAO SubjectCategoryDAO= new SubjectCategoryDAO();

        int settingid = Integer.parseInt(request.getParameter("settingid"));
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        int order = Integer.parseInt(request.getParameter("order"));
        String status_raw = request.getParameter("status");
        Boolean status;
        if (status_raw.equalsIgnoreCase("active")) {
            status = true;
        } else {
            status = false;
        }
        Setting setting = new Setting();
        setting.setName(value);
        setting.setType(type);
        setting.setOrder(order);
        setting.setStatus(status);
        setting.setSettingID(settingid);
        setting.setId(id);

        if (type.equalsIgnoreCase("SUB_CATEGORY_POST")) {
            settingDAO.updateSetting(setting);
            BlogSubCategory blogSubCategory = new BlogSubCategory();
            blogSubCategory.setBlogSubCategoryId(id);
            blogSubCategory.setSubCategoryName(value);
            blogSubCategory.setOrder(order);
            blogSubCategory.setStatus(status);
            blogSubCategory.setType(type);
            BlogSubCategoryDAO.updateBlogSubCategory(blogSubCategory);
        }
        
        if (type.equalsIgnoreCase("TEST_TYPE")) {
            settingDAO.updateSetting(setting);
            TestType TestType = new TestType();
            TestType.setTestTypeID(id);
            TestType.setTestTypeName(value);
            TestType.setOrder(order);
            TestType.setStatus(status);
            TestType.setType(type);
            TestTypeDAO.updateTestType(TestType);
        }
        
        if (type.equalsIgnoreCase("SUBJECT")) {
            settingDAO.updateSetting(setting);
            Subject Subject = new Subject();
            Subject.setSubjectId(id);
            Subject.setName(value);
            Subject.setOrder(order);
            Subject.setStatus(status);
            Subject.setType(type);
            SubjectDAO.updateSubject(Subject);
        }
        
        if (type.equalsIgnoreCase("SUBJECT_CATEGORY")) {
            settingDAO.updateSetting(setting);
            SubjectCategory SubjectCategory = new SubjectCategory();
            SubjectCategory.setCategoryID(id);
            SubjectCategory.setName(value);
            SubjectCategory.setOrder(order);
            SubjectCategory.setStatus(status);
            SubjectCategory.setType(type);
            SubjectCategoryDAO.updateSubjectCategory(SubjectCategory);
        }
        
        if (type.equalsIgnoreCase("LEVEL_QUESTION")) {
            settingDAO.updateSetting(setting);
            QuestionLevel questionLevel = new QuestionLevel(id, value, order, status, type);
            QuestionLevelDAO.updateQuestionLevel(questionLevel);
        }
        if (type.equalsIgnoreCase("TYPE_LESSON")) {
            settingDAO.updateSetting(setting);
            LessonType lessonType = new LessonType(id, value, order, status, type);
            LessonTypeDAO.updateLessonType(lessonType);
        }
        if (type.equalsIgnoreCase("CATEGORY_POST")) {
            settingDAO.updateSetting(setting);
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setBlogCategoryID(id);
            blogCategory.setName(value);
            blogCategory.setOrder(order);
            blogCategory.setStatus(status);
            blogCategory.setType(type);
            BlogCategoryDAO.updateBlogCategory(blogCategory);
        }
        response.sendRedirect("../management/setting");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
