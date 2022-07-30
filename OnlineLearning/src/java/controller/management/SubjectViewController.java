package controller.management;

import dao.AccountDAO;
import dao.CourseDAO;
import dao.DimensionDAO;
import dao.PricePackageDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.Dimension;
import model.CoursePricePackage;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "SubjectViewController", urlPatterns = {"/management/subject-view"})
public class SubjectViewController extends HttpServlet {

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
        ArrayList<Account> accounts = accountDAO.getListAccountCanAccessCourse(courseID);

        request.setAttribute("accounts", accounts);
        request.setAttribute("experts", experts);
        request.setAttribute("subjectCategories", subjectCategories);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/view/subject-view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
