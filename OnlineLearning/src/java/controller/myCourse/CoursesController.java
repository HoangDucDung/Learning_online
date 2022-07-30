package controller.myCourse;

import dao.CourseAccountDAO;
import dao.CourseDAO;
import dao.SubjectCategoryDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.CourseAccount;
import model.CoursePricePackage;
import model.Subject;
import model.SubjectCategory;

@WebServlet(name = "CoursesController", urlPatterns = {"/courses"})
public class CoursesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        int accountID = 0;
        ArrayList<CourseAccount> listCourseOfAccount = null;
        if (account != null) {
            accountID = account.getAccountID();
            listCourseOfAccount = new CourseAccountDAO().getListCourseAccount(accountID);
        }
        request.setAttribute("listCourseAccount", listCourseOfAccount);

        ArrayList<SubjectCategory> listSC = new SubjectCategoryDAO().getAllSubjectCategory();
        request.setAttribute("listSC", listSC);

        ArrayList<Subject> listSubject = new SubjectDAO().getAllSubjects();
        request.setAttribute("listSubject", listSubject);

        ArrayList<Course> listTopFeatureCourse = new CourseDAO().getAllTopFeatureCourse();
        request.setAttribute("listTopFeatureCourse", listTopFeatureCourse);

        ArrayList<Course> listCourse = new CourseDAO().getAllCourse();

        int totalCourse = listCourse.size();
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

        ArrayList<Course> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourse.size(); i++) {
            if (i >= (page - 1) * numItem && i < page * numItem) {
                listCourseCurrentInPage.add(listCourse.get(i));
            }
        }
        request.setAttribute("listCourse", listCourseCurrentInPage);

        request.getRequestDispatcher("view/courses.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String txtSearch = request.getParameter("txtSearch");
        String subjectId = request.getParameter("arraySubjectId");
        //String categoryId = request.getParameter("arrayCategoryId");
        String price = request.getParameter("arrayPrice");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        ArrayList<Course> listCourseSearchBySubject = new ArrayList<>();
        if (subjectId.isEmpty()) {
            listCourseSearchBySubject = new CourseDAO().getAllCourse();
        } else {
            String[] arraySubjectId = subjectId.split(",");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(arraySubjectId));
            ArrayList<Integer> listSearchId = new ArrayList<>();
            for (String string : list) {
                listSearchId.add(Integer.parseInt(string));
            }
            // modify this function
            listCourseSearchBySubject = new CourseDAO().getListCourseBySubject(listSearchId);
        }

        ArrayList<Course> listCourseSearchByText = new ArrayList<>();
        if (txtSearch.isEmpty()) {
            listCourseSearchByText = new CourseDAO().getAllCourse();
        } else {
            listCourseSearchByText = new CourseDAO().getListCourseByText(txtSearch);
        }

        ArrayList<Course> listCourseSearchByPrice = new ArrayList<>();
        if (price.isEmpty()) {
            listCourseSearchByPrice = new CourseDAO().getAllCourse();
        } else {
            String[] arraySearchId = price.split(",");
            ArrayList<String> listPrice = new ArrayList<>();
            listPrice.addAll(Arrays.asList(arraySearchId));
            listCourseSearchByPrice = new CourseDAO().getListCourseByPrice(listPrice);
        }

        ArrayList<Course> listCourseTemp = new ArrayList<>();
        for (Course courseCate : listCourseSearchBySubject) {
            for (Course courseTxt : listCourseSearchByText) {
                if (courseCate.getCourseId() == courseTxt.getCourseId()) {
                    listCourseTemp.add(courseCate);
                    break;
                }
            }
        }
        ArrayList<Course> listCourseTempII = new ArrayList<>();
        for (Course courseTemp : listCourseTemp) {
            for (Course coursePrice : listCourseSearchByPrice) {
                if (courseTemp.getCourseId() == coursePrice.getCourseId()) {
                    listCourseTempII.add(courseTemp);
                }
            }
        }
        if (status.equalsIgnoreCase("Highest Rated")) {
            Collections.sort(listCourseTempII, new Comparator<Course>() {
                @Override
                public int compare(Course t, Course t1) {
                    return t1.getStar() - t.getStar();
                }
            });
        } else if (status.equalsIgnoreCase("Newest")) {
            Collections.sort(listCourseTempII, new Comparator<Course>() {
                @Override
                public int compare(Course t, Course t1) {
                    return t.getCreatedDate().before(t1.getCreatedDate()) ? 1 : -1;
                }
            });
        }

        int totalCourse = listCourseTempII.size();
        int numItem = 8;
        int totalpage = 0;
        if (totalCourse % numItem == 0) {
            totalpage = (int) totalCourse / numItem;
        } else {
            totalpage = (int) totalCourse / numItem + 1;
        }

        ArrayList<Course> listCourseCurrentInPage = new ArrayList<>();
        for (int i = 0; i < listCourseTempII.size(); i++) {
            if (i >= (pageNum - 1) * numItem && i < pageNum * numItem) {
                listCourseCurrentInPage.add(listCourseTempII.get(i));
            }
        }
        Account account = (Account) request.getSession().getAttribute("account");
        int accountID = 0;
        ArrayList<CourseAccount> listCourseAccount = new ArrayList<>();
        if (account != null) {
            accountID = account.getAccountID();
            listCourseAccount = new CourseAccountDAO().getListCourseAccount(accountID);
        }

        if (listCourseCurrentInPage.isEmpty()) {
            response.getWriter().write("<section class=\"page_404\">\n"
                    + "	<div class=\"container\">\n"
                    + "		<div class=\"row\">	\n"
                    + "		<div class=\"col-sm-12 \">\n"
                    + "		<div class=\"col-sm-10 col-sm-offset-1  text-center\">\n"
                    + "		<div class=\"four_zero_four_bg\">\n"
                    + "			<p class=\"text-center text-notify\">No found course your are looking for</p>\n"
                    + "		\n"
                    + "		\n"
                    + "		</div>\n"
                    + "		\n"
                    + "		<div class=\"contant_box_404\">\n"
                    + "		<a href=\"home\" class=\"link_404\">Go to Home</a>\n"
                    + "	</div>\n"
                    + "		</div>\n"
                    + "		</div>\n"
                    + "		</div>\n"
                    + "	</div>\n"
                    + "</section>");
            response.getWriter().write("<input type=\"hidden\" id=\"page-num\" value=\"" + pageNum + "\">");
        } else {
            response.getWriter().write("<div id=\"c-course-list\">");
            for (Course course : listCourseCurrentInPage) {
                response.getWriter().write("<div class=\"c-course-item\">\n");
                if (!course.getListPrice().isEmpty()) {
                    response.getWriter().write("                                <div class=\"price-container\">\n"
                            + "                                    <table class=\"table table-striped\">\n"
                            + "                                        <thead>\n"
                            + "                                            <tr>\n"
                            + "                                                <th scope=\"col\">Access Package</th>\n"
                            + "                                                <th scope=\"col\">Price</th>\n"
                            + "                                                <th scope=\"col\">Sale price</th>\n"
                            + "                                            </tr>\n"
                            + "                                        </thead>\n"
                            + "                                        <tbody>\n");
                    for (CoursePricePackage coursePrice : course.getListPrice()) {
                        response.getWriter().write("                                                <tr>\n"
                                + "                                                    <td>" + coursePrice.getName() + "</td>\n"
                                + "                                                    <td style=\"text-decoration: line-through\">$ " + coursePrice.getListPrice() + "</td>\n"
                                + "                                                    <td>$ " + coursePrice.getSalePrice() + "</td>\n"
                                + "                                                </tr>\n"
                                + "                                            </c:forEach>                                      \n");
                    }

                    response.getWriter().write("                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n");
                }
                response.getWriter().write("<a style=\"width:27%; margin: auto 0\" href=\"course-detail?id=" + course.getCourseId() + "\"");
                if (!course.getListPrice().isEmpty()) {
                    response.getWriter().write("onmouseover=\"displayPrice(this)\" onmouseout=\"hiddenPrice(this)\"");
                }
                response.getWriter().write(">\n"
                        + "                                <div class=\"c-course-item-img\">\n"
                        + "                                    <img src='" + course.getTinyPictureUrl() + "' alt=\"\">\n"
                        + "                                </div></a>\n"
                        + "                                <div class=\"c-course-item-intro justify-between\">\n"
                        + "                                    <div class=\"c-course-item-intro-text\">\n"
                        + "<a href=\"course-detail?id=" + course.getCourseId() + "\">\n"
                        + "                                        <h5 class=\"c-course-item-title\">" + course.getName() + "</h5>\n"
                        + "                                        <p class=\"c-course-item-desc\">" + course.getDescription() + "</p>\n"
                        + "                                        <ul class=\"ratings\">\n"
                );
                for (int i = 0; i < 5; i++) {
                    if (i < course.getStar()) {
                        response.getWriter().write("<i class=\"c-star fa-solid fa-star selected\"></i>\n");
                    } else {
                        response.getWriter().write("<i class=\"c-star fa-solid fa-star \"></i>\n");
                    }
                }
                response.getWriter().write("<p class=\"c-course-isLearning\">(" + course.getNumberPeopleLearning() + ")</p>\n"
                        + "                                        </ul>\n"
                        + "                                        <p class=\"c-course-item-author\">" + course.getInstructorId().getFirstName() + " " + course.getInstructorId().getLastName() + "</p>\n"
                        + "                                        <p class=\"c-course-subject\">\n"
                        + "                                            <i class=\"fa-solid fa-book\"></i> : ");
                for (int i = 0; i < course.getListSubject().size(); i++) {
                    if (i == 0) {
                        response.getWriter().write(course.getListSubject().get(i).getName());
                    } else {
                        response.getWriter().write(", " + course.getListSubject().get(i).getName());
                    }
                }
                response.getWriter().write("</p></a>");
                response.getWriter().write("");
                boolean isRegister = false;
                for (CourseAccount courseAcc : listCourseAccount) {
                    if (courseAcc.getCourseId().getCourseId() == course.getCourseId()) {
                        isRegister = true;
                        break;
                    }
                }
                if (isRegister == true) {
                    response.getWriter().write("<a href=\"#\" class=\"btn-for-course\">Continue</a></div>");
                } else {
                    response.getWriter().write("<a href=\"#\" class=\"btn-for-course\">Register</a></div>");
                }

                if (isRegister == true) {
                    response.getWriter().write("<div class=\"c-course-item-intro-registered\">\n"
                            + "                                            <h5>Registered</h5>\n"
                            + "                                        </div>");
                } else {
                    BigDecimal num = new BigDecimal("0");
                    if (course.getPrice().compareTo(num) <= 0) {
                        response.getWriter().write("<div class=\"c-course-item-intro-price\">\n"
                                + "                                           <span class=\"tag-free\">Free</span>\n"
                                + "                                        </div>");
                    }
                }
                response.getWriter().write("</div></div></a>");
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
    public String getServletInfo() {
        return "Short description";
    }

}
