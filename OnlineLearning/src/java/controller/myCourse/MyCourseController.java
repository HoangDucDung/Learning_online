package controller.myCourse;

import dao.CourseAccountDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.CourseAccount;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "MyCourseController", urlPatterns = {"/my-course"})
public class MyCourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }
        int accountID = account.getAccountID();
        ArrayList<CourseAccount> listCourseAccount = new CourseAccountDAO().getListCourseAccount(accountID);

        ArrayList<SubjectCategory> listSC = new SubjectCategoryDAO().getAllSubjectCategory();
        request.setAttribute("listSC", listSC);

        ArrayList<Subject> listSubject = new SubjectDAO().getAllSubjects();
        request.setAttribute("listSubject", listSubject);

        int totalCourse = listCourseAccount.size();
        int numItem = 8;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }
        request.setAttribute("totalpage", totalpage);

        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            if (page < 1 || page > totalpage) {
                page = 1;
            }
        }
        request.setAttribute("page", page);

        ArrayList<CourseAccount> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourseAccount.size(); i++) {
            if (i >= (page - 1) * numItem && i < page * numItem) {
                listCourseCurrentInPage.add(listCourseAccount.get(i));
            }
        }
        request.setAttribute("listCourseAccount", listCourseCurrentInPage);

        request.getRequestDispatcher("view/my-course.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You need to login first");
            return;
        }
        int accountID = account.getAccountID();

        String dateJoin = request.getParameter("datejoin");
        String progress = request.getParameter("progress");
        String txtSearch = request.getParameter("txtSearch");
        String txtSearchId = request.getParameter("arraySearchId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        ArrayList<CourseAccount> listCourseSearchByCategory = new ArrayList<>();
        if (txtSearchId.isEmpty()) {
            listCourseSearchByCategory = new CourseAccountDAO().getListCourseAccount(accountID);
        } else {
            String[] arraySearchId = txtSearchId.split(",");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arraySearchId));
            ArrayList<Integer> listSearchId = new ArrayList<>();
            for (String string : list) {
                listSearchId.add(Integer.parseInt(string));
            }
            listCourseSearchByCategory = new CourseAccountDAO().getListCourseAccountByCategory(accountID, listSearchId);
        }

        CourseAccountDAO courseAccountDao = new CourseAccountDAO();

        ArrayList<CourseAccount> listCourseSearchByValue = new ArrayList<>();
        if (txtSearch.isEmpty()) {
            if (dateJoin.isEmpty()) {
                if (progress.equalsIgnoreCase("All")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccount(accountID);
                } else if (progress.equalsIgnoreCase("In progress")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseByProgress(accountID, 0);
                } else {
                    listCourseSearchByValue = courseAccountDao.getListCourseByProgress(accountID, 1);
                }
            } else {
                Date dateEnroll = Date.valueOf(dateJoin);
                if (progress.equalsIgnoreCase("All")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgress(accountID, dateEnroll, -1);
                } else if (progress.equalsIgnoreCase("In progress")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgress(accountID, dateEnroll, 0);
                } else {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgress(accountID, dateEnroll, 1);
                }
            }
        } else {
            if (dateJoin.isEmpty()) {
                if (progress.equalsIgnoreCase("All")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseByTextAndProgress(accountID, txtSearch, -1);
                } else if (progress.equalsIgnoreCase("In progress")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseByTextAndProgress(accountID, txtSearch, 0);
                } else {
                    listCourseSearchByValue = courseAccountDao.getListCourseByTextAndProgress(accountID, txtSearch, 1);
                }
            } else {
                Date dateEnroll = Date.valueOf(dateJoin);
                if (progress.equalsIgnoreCase("All")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgressAndText(accountID, dateEnroll, -1, txtSearch);
                } else if (progress.equalsIgnoreCase("In progress")) {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgressAndText(accountID, dateEnroll, 0, txtSearch);
                } else {
                    listCourseSearchByValue = courseAccountDao.getListCourseAccountByDateAndProgressAndText(accountID, dateEnroll, 1, txtSearch);
                }
            }
        }

        ArrayList<CourseAccount> listCourseFinal = new ArrayList<>();

        for (CourseAccount courseCate : listCourseSearchByCategory) {
            for (CourseAccount courseValue : listCourseSearchByValue) {
                if (courseCate.getCourseId().getCourseId() == courseValue.getCourseId().getCourseId()) {
                    listCourseFinal.add(courseCate);
                    break;
                }
            }
        }

        int totalCourse = listCourseFinal.size();
        int numItem = 8;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }

        ArrayList<CourseAccount> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourseFinal.size(); i++) {
            if (i >= (pageNum - 1) * numItem && i < pageNum * numItem) {
                listCourseCurrentInPage.add(listCourseFinal.get(i));
            }
        }

        if (listCourseCurrentInPage.isEmpty()) {
            response.getWriter().write("<div id=\"my-course-list\">");
            response.getWriter().write("<div id=\"no-course\">\n"
                    + "                <h2>There is no course you are looking for</h2>\n"
                    + "                <a href=\"courses\">Join a new course</a>\n"
                    + "            </div>");
            response.getWriter().write("</div>");
            response.getWriter().write("<input type=\"hidden\" id=\"page-num\" value=\"" + pageNum + "\">");
        } else {
            response.getWriter().write("<div id=\"my-course-list\">");
            for (CourseAccount courseAccount : listCourseCurrentInPage) {

                response.getWriter().write("<div class=\"my-course-item\">\n"
                        + "                    <div class=\"my-course-item-img\">\n"
                        + "                        <p class=\"option-course\" onclick=\"openOption(this)\">\n"
                        + "                            <i class=\"fa-solid fa-ellipsis-vertical \"></i>\n"
                        + "                        <div class=\"notice-unenroll\">\n"
                        + "                            <p>Unenroll this course?</p>\n"
                        + "                            <form>\n"
                        + "                                <div class=\"holder-button-delete\">\n"
                        + "                                    <input type=\"hidden\" value=\"" + courseAccount.getCourseId().getCourseId() + "\" class=\"id-course-delete\">\n"
                        + "                                    <button type=\"button\" class=\"btn-unenroll\" onclick=\"unenrollCourse(this)\">Unenroll</button>\n"
                        + "                                    <button type=\"button\" class=\"btn-cancel\" onclick=\"closeNotice(this)\">Cancel</button>\n"
                        + "                                </div>\n"
                        + "                            </form>\n"
                        + "                        </div>\n"
                        + "                        </p>\n"
                        + "\n"
                        + "                        <img src='" + courseAccount.getCourseId().getTinyPictureUrl() + "' alt=\"\">\n"
                        + "                        <div class=\"overlay-img\">               \n"
                        + "                            <a href=\"lesson?id=" + courseAccount.getCourseId().getCourseId() + "\" class=\"btn-continue\">Get Started</a>\n"
                        + "                        </div>\n"
                        + "                    </div>\n"
                        + "                    <div class=\"my-course-item-desc\">\n"
                        + "                        <div class=\"my-course-item-title\">" + courseAccount.getCourseId().getName() + "</div>\n"
                        + "                             <ul class=\"ratings\">\n");
                for (int i = 1; i <= courseAccount.getRating(); i++) {
                    response.getWriter().write("<li class=\"star selected\" onclick=\"voteStar(this, " + courseAccount.getCourseId().getCourseId() + ")\"></li>");
                }
                for (int i = courseAccount.getRating() + 1; i <= 5; i++) {
                    response.getWriter().write("<li class=\"star\" onclick=\"voteStar(this, " + courseAccount.getCourseId().getCourseId() + ")\"></li>");
                }
                response.getWriter().write("                            </ul>\n"
                        + ""
                        + "                        <p class=\"my-course-item-date\">" + courseAccount.getEnrollDate() + "</p>\n"
                        + "                        <div class=\"my-course-progress\" style=\"--progress: " + courseAccount.getProgress() + "%\"></div>\n"
                        + "                        <p class=\"text-progress\">" + courseAccount.getProgress() + "% Complete</p>\n"
                        + "                    </div>\n"
                        + "                </div> ");
            }
            response.getWriter().write("</div>");
            response.getWriter().write("<div class=\"pagination\">\n"
                    + "                <ul class=\"pagination-list\">\n"
                    + "                    <li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum - 1 == 0 ? 1 : pageNum - 1) + ")\" class=\"previous-btn\" >Previous</button>\n"
                    + "                    </li>\n");
            for (int i = 1; i <= totalpage; i++) {
                response.getWriter().write("<li>\n"
                        + "                            <button onclick=\"pagination(" + i + ")\" " + (i == pageNum ? ("class = \"paging-active page-num\"") : ("class=\"page-num\"")) + ">" + i + "</button>\n"
                        + "                        </li>");
            }

            response.getWriter().write("<li>\n"
                    + "                        <button onclick=\"pagination(" + (pageNum + 1 > totalpage ? totalpage : pageNum + 1) + ")\" class=\"next-btn\" >Next</button>\n"
                    + "                    </li>\n"
                    + "                </ul>\n"
                    + "                <input type=\"hidden\" id=\"page-num\" value=" + pageNum + ">\n"
                    + "            </div>");
        }
        response.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You need to login first");
            return;
        }
        int accountID = account.getAccountID();
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        new CourseAccountDAO().unenrollCourse(accountID, courseId);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
