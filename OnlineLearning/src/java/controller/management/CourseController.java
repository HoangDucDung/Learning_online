package controller.management;

import dao.CourseDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "CourseController", urlPatterns = {"/management/course"})
public class CourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        ArrayList<Course> listCourse = new CourseDAO().getAllCourseNoCondition();

        ArrayList<SubjectCategory> listSC = new SubjectCategoryDAO().getAllSubjectCategory();
        request.setAttribute("listSC", listSC);

        ArrayList<Subject> listSubject = new SubjectDAO().getAllSubjects();
        request.setAttribute("listSubject", listSubject);

        int totalTrans = listCourse.size();
        int numItem = 8;
        int totalpage = 0;
        if (totalTrans % numItem == 0) {
            totalpage = (int) totalTrans / numItem;
        } else {
            totalpage = (int) totalTrans / numItem + 1;
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

        ArrayList<Course> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourse.size(); i++) {
            if (i >= (page - 1) * numItem && i < page * numItem) {
                listCourseCurrentInPage.add(listCourse.get(i));
            }
        }
        request.setAttribute("listCourse", listCourseCurrentInPage);

        Account acc = (Account) request.getSession().getAttribute("account");
        request.setAttribute("roleAccount", acc.getRole().getId());

        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Integer> listCourseIdCanAccess = courseDAO.getListCourseIdCanAccess(acc.getId());
        request.setAttribute("listCourseIdCanAccess", listCourseIdCanAccess);

        RequestDispatcher dispatcher = request.getRequestDispatcher("../view/course-management.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        Account acc = (Account) request.getSession().getAttribute("account");
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Integer> listCourseIdCanAccess = courseDAO.getListCourseIdCanAccess(acc.getId());

        String status = request.getParameter("status");
        String txtSearch = request.getParameter("txtSearch");
        String txtSearchId = request.getParameter("arraySearchId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        ArrayList<Course> listCourseSearchByCategory = new ArrayList<>();
        if (txtSearchId.isEmpty()) {
            listCourseSearchByCategory = new CourseDAO().getAllCourseNoCondition();
        } else {
            String[] arraySearchId = txtSearchId.split(",");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arraySearchId));
            ArrayList<Integer> listSearchId = new ArrayList<>();
            for (String string : list) {
                listSearchId.add(Integer.parseInt(string));
            }
            listCourseSearchByCategory = new CourseDAO().getCoursesBySubject(listSearchId);
        }

        ArrayList<Course> listCourseByTxt = new ArrayList<>();
        if (txtSearch.isEmpty()) {
            listCourseByTxt = new CourseDAO().getAllCourseNoCondition();
        } else {
            listCourseByTxt = new CourseDAO().getCoursesByTxt(txtSearch);
        }

        ArrayList<Course> listCourseByStatus = new ArrayList<>();
        if (status.isEmpty()) {
            listCourseByStatus = new CourseDAO().getAllCourseNoCondition();
        } else {
            listCourseByStatus = new CourseDAO().getCoursesByStatus(status);
        }

        ArrayList<Course> listTemp = new ArrayList<>();

        for (Course courseCate : listCourseSearchByCategory) {
            for (Course courseValue : listCourseByStatus) {
                if (courseCate.getCourseId() == courseValue.getCourseId()) {
                    listTemp.add(courseCate);
                    break;
                }
            }
        }

        ArrayList<Course> listCourseFinal = new ArrayList<>();

        for (Course courseCate : listTemp) {
            for (Course courseValue : listCourseByTxt) {
                if (courseCate.getCourseId() == courseValue.getCourseId()) {
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

        ArrayList<Course> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourseFinal.size(); i++) {
            if (i >= (pageNum - 1) * numItem && i < pageNum * numItem) {
                listCourseCurrentInPage.add(listCourseFinal.get(i));
            }
        }

        response.getWriter().write("<table class=\"table table-striped\" id=\"table\">\n"
                + "                                    <thead>\n"
                + "                                        <tr>\n"
                + "                                            <th>ID</th>\n"
                + "                                            <th style=\"width:300px\">Name</th>\n"
                + "                                            <th>Category</th>\n"
                + "                                            <th>Number of lessons</th>\n"
                + "                                            <th>Lesson</th>\n"
                + "                                            <th>Owner</th>\n"
                + "                                            <th>Status</th>\n"
                + "                                            <th>Action</th>\n"
                + "\n"
                + "                                        </tr>\n"
                + "                                    </thead>");
        if (!listCourseCurrentInPage.isEmpty()) {
            response.getWriter().write("                                   <tbody>\n");
            for (Course c : listCourseCurrentInPage) {
                response.getWriter().write("<tr>\n"
                        + "                                                <td>" + c.getCourseId() + "</td>\n"
                        + "                                                <td>" + c.getName() + "</td>\n<td>");
                for (Subject subject : c.getListSubject()) {
                    response.getWriter().write(subject.getName() + ", ");
                }

                response.getWriter().write("       </td><td>" + c.getNumberLesson() + "</td>\n<td><a href=\"./lesson-list?Cid=" + c.getCourseId() + "\">Lesson List</a></td>\n"
                        + "                                                <td>" + c.getInstructorId().getFirstName() + " " + c.getInstructorId().getLastName() + "</td>\n");
                if (c.isStatus() == true) {
                    response.getWriter().write("<td>Publish</td>\n");
                } else {
                    response.getWriter().write("<td>Unpublish</td>\n");
                }
                response.getWriter().write("<td>");
                if (acc.getRole().getId() == 4) {
                    response.getWriter().write("<a class=\"text-primary\" href=\"./subject-view?courseID=" + c.getCourseId() + "\">View</a> / <a class=\"text-primary\" href=\"./subject-detail?courseID=" + c.getCourseId() + "\">Edit</a> / <button class=\"text-danger\" onclick=\"deleteSubject(" + c.getCourseId() + ", this)\">Delete</button></td>\n");
                }
                for (int id : listCourseIdCanAccess) {
                    if (id == c.getCourseId()) {
                        response.getWriter().write("<a class=\"text-primary\" href=\"./subject-view?courseID=" + c.getCourseId() + "\">View</a> / <a class=\"text-primary\" href=\"./subject-detail?courseID=" + c.getCourseId() + "\">Edit</a> / <button class=\"text-danger\" onclick=\"deleteSubject(" + c.getCourseId() + ", this)\">Delete</button></td>\n");
                    }
                }
                response.getWriter().write("</tr>");
            }
            response.getWriter().write("</tbody></table>");
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
        } else {
            response.getWriter().write("<input type=\"hidden\" id=\"page-num\" value=\"" + pageNum + "\">");

        }
        response.getWriter().flush();

        response.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("courseID"));

        CourseDAO courseDAO = new CourseDAO();
        courseDAO.deleteCourse(id);

        response.setStatus(200);
        response.flushBuffer();
    }
}
