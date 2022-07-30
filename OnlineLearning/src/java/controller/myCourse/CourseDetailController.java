package controller.myCourse;

import dao.AccountDAO;
import dao.CourseAccountDAO;
import dao.CourseDAO;
import dao.LessonDAO;
import dao.PricePackageDAO;
import dao.SubLessonDAO;
import dao.TransactionHistoryDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.CourseAccount;
import model.CoursePricePackage;
import model.SubLesson;
import model.TransactionHistory;

@WebServlet(name = "CourseDetailController", urlPatterns = {"/course-detail"})
public class CourseDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        try {
            int accountID = 0;
            int courseId = Integer.parseInt(request.getParameter("id"));
            if (account != null) {
                accountID = account.getAccountID();
            }

            Course course = new CourseDAO().getCourseByCourseId(courseId);
            request.setAttribute("course", course);
            
            boolean isCourseRegister = new CourseDAO().isRegister(accountID, courseId);
            request.setAttribute("isRegister", isCourseRegister);
            
            int numberLesson = new LessonDAO().getTotalLessonInCourse(courseId);
            request.setAttribute("numberLesson", numberLesson);
            
            int numberQuiz = new LessonDAO().getTotalQuizInCourse(courseId);
            request.setAttribute("numberQuiz", numberQuiz);
            
            ArrayList<SubLesson> listSubLesson = new SubLessonDAO().getListSubLessonByCourseID(courseId);
            request.setAttribute("listSubLesson", listSubLesson);
            
            ArrayList<Course> listTopFeatureCourse = new CourseDAO().getTopFeatureCourse(courseId);
            request.setAttribute("listTopFeatureCourse", listTopFeatureCourse);
            
            ArrayList<CoursePricePackage> listPrice = new PricePackageDAO().getListPricePackageOfCourse(courseId);
            request.setAttribute("listPrice", listPrice);
            
            request.getRequestDispatcher("view/course-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(404);
        }

       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        int priceId = Integer.parseInt(request.getParameter("price-package"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        
        CourseAccount courseAccount = new CourseAccount();   
        courseAccount.setAccountId(account);
        Course course = new Course();
        course.setCourseId(courseId);
        courseAccount.setCourseId(course);
        courseAccount.setEnrollDate(Date.valueOf(LocalDate.now()));
        new CourseAccountDAO().insertCourseAccount(courseAccount);
        
        CoursePricePackage pricePackage = new PricePackageDAO().getCoursePricePackageByID(priceId);
        
        TransactionHistory th = new TransactionHistory();
        th.setAccountID(account);
        th.setCourseID(course);
        if(pricePackage.getSalePrice() == null) {
            th.setAmount(pricePackage.getListPrice());
        } else {
            th.setAmount(pricePackage.getSalePrice());
        }
        th.setTransactionTime(Date.valueOf(LocalDate.now()));
        th.setCoursePackageID(pricePackage);
        Account sale = new Account();
        sale.setAccountID(12);
        th.setSaleID(sale);
        th.setUpdatedBySaleID(sale);
        th.setNote("Created by user");
        new TransactionHistoryDAO().insertTransaction(th);
        BigDecimal price = pricePackage.getSalePrice() == null ? pricePackage.getListPrice() : pricePackage.getSalePrice();
        
        account.setBalance(account.getBalance().subtract(price));
        new AccountDAO().minusBalance(account);
        Account updateAccount = new AccountDAO().getAccount(account.getEmail(), account.getPassword());
        request.getSession().removeAttribute("account");
        request.getSession().setAttribute("account", updateAccount);
        
        response.sendRedirect("my-course");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
