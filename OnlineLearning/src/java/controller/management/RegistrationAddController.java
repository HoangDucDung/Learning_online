package controller.management;

import dao.AccountDAO;
import dao.CourseAccountDAO;
import dao.CourseDAO;
import dao.PricePackageDAO;
import dao.TransactionHistoryDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.CourseAccount;
import model.CoursePricePackage;
import model.TransactionHistory;

@WebServlet(name = "RegistrationAddController", urlPatterns = {"/management/registration-add"})
public class RegistrationAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id_raw = request.getParameter("id");
            ArrayList<CoursePricePackage> packages = null;
            TransactionHistory th = null;
            if (id_raw != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                th = new TransactionHistoryDAO().getTransactionHistoryById(id);
                Account account = (Account) request.getSession().getAttribute("account");
                if(th.getSaleID().getAccountID() != account.getAccountID()) {
                    response.sendError(403);
                } else {
                    packages = new PricePackageDAO().getListPricePackageOfCourse(th.getCourseID().getCourseId());
                }
            }
            request.setAttribute("th", th);
            request.setAttribute("packages", packages);
            ArrayList<Course> courses = new CourseDAO().getAllCourseForManagement();
            request.setAttribute("courses", courses);

            ArrayList<Account> accounts = new AccountDAO().findAll();
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("../view/registration-add.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("subject"));
        int accountId = Integer.parseInt(request.getParameter("email"));
        int packageId = Integer.parseInt(request.getParameter("package"));
        String note = request.getParameter("note");
        String action = request.getParameter("button");

        Account account = new Account();
        account.setAccountID(accountId);

        Account sale = (Account) request.getSession().getAttribute("account");
        Account updatedBySale = (Account) request.getSession().getAttribute("account");

        Course course = new Course();
        course.setCourseId(courseId);

        CoursePricePackage pricePackage = new PricePackageDAO().getCoursePricePackageByID(packageId);

        TransactionHistory th = new TransactionHistory();
        th.setAccountID(account);
        th.setCourseID(course);
        th.setTransactionTime(Date.valueOf(LocalDate.now()));
        th.setCoursePackageID(pricePackage);
        th.setSaleID(sale);
        th.setUpdatedBySaleID(updatedBySale);
        th.setNote(note);

        if (pricePackage.getSalePrice() == null) {
            th.setAmount(pricePackage.getListPrice());
        } else {
            th.setAmount(pricePackage.getSalePrice());
        }
        if (action.equals("Register")) {
            new TransactionHistoryDAO().insertTransaction(th);

            CourseAccount courseAccount = new CourseAccount();
            courseAccount.setAccountId(account);
            courseAccount.setCourseId(course);
            courseAccount.setEnrollDate(Date.valueOf(LocalDate.now()));
            courseAccount.setProgress(0);
            courseAccount.setRating(1);
            if (new CourseAccountDAO().isRegisterCourse(account.getAccountID(), course.getCourseId()) == false) {
                new CourseAccountDAO().insertCourseAccount(courseAccount);
            }
        } else {
            int transId = Integer.parseInt(request.getParameter("transId"));
            new TransactionHistoryDAO().updateTransaction(transId, th);
        }

        response.sendRedirect("./registration");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        ArrayList<CoursePricePackage> pricePackages = new PricePackageDAO().getListPricePackageOfCourse(courseId);
        String result = "";
        result += "[\n";
        for (int i = 0; i < pricePackages.size(); i++) {
            result += "{";
            result += "\"id\": \"" + pricePackages.get(i).getPriceId() + "\",\n";
            result += "\"name\": \"" + pricePackages.get(i).getName() + "\",\n";
            result += "\"duration\": \"" + pricePackages.get(i).getAccessDuration() + "\",\n";
            result += "\"price\": \"" + pricePackages.get(i).getListPrice() + "\",\n";
            result += "\"salePrice\": \"" + pricePackages.get(i).getSalePrice() + "\"\n";
            if (i == pricePackages.size() - 1) {
                result += "}\n";
            } else {
                result += "},\n";
            }
        }
        result += "]\n";
        response.getWriter().write(result);
        response.getWriter().flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
