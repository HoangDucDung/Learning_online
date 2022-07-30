package controller.management;

import dao.LessonDAO;
import dao.LessonTypeDAO;
import dao.SubLessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Lesson;
import model.LessonType;
import model.SubLesson;

@WebServlet(name = "LessonListManagemet", urlPatterns = {"/management/lesson-list"})
public class LessonListManagemet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/view/lesson-list-management.jsp").forward(request, response);
    }

    private static void getAllLessonByCourse(HttpServletRequest request, HttpServletResponse response) {
        int Cid = Integer.parseInt(request.getParameter("Cid"));
        ArrayList<Lesson> llist = new LessonDAO().getListLessonByCId(Cid);
        request.setAttribute("lessonlist", llist);
        request.setAttribute("Cid", Cid);
    }

    private static void getAllLesson(HttpServletRequest request, HttpServletResponse response) {
        int Cid = Integer.parseInt(request.getParameter("Cid"));

        ArrayList<LessonType> ltList = new LessonTypeDAO().getAllLessonTypes();

        ArrayList<SubLesson> slList = new SubLessonDAO().getListSubLessonByCourseID(Cid);

        request.setAttribute("ltList", ltList);
        request.setAttribute("slList", slList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getAllLessonByCourse(request, response);
        getAllLesson(request, response);
        String subjectID = request.getParameter("Sid");
        request.setAttribute("subjectID", subjectID);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        insertTableByFilter(request, response);
    }

    private static void insertTableByFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean checkls = true;
        boolean checklt = true;
        int courseID = Integer.parseInt(request.getParameter("Cid"));
        int stt = Integer.parseInt(request.getParameter("status"));
        if (request.getParameter("checkls").equalsIgnoreCase("false")) {
            checkls = false;
        }
        if (request.getParameter("checklt").equalsIgnoreCase("false")) {
            checklt = false;
        }
        ArrayList<Integer> arrlt = new ArrayList<>();
        ArrayList<Integer> arrls = new ArrayList<>();
        if (request.getParameter("arrayltID") != null) {
            arrlt = convertStringToArl(request, response, request.getParameter("arrayltID"));
        }
        if (request.getParameter("arraylsID") != null) {
            arrls = convertStringToArl(request, response, request.getParameter("arraylsID"));
        }

        ArrayList<Lesson> llist = new LessonDAO().getListLessonByCondition(courseID, arrlt, arrls, checklt, checkls, stt);

        PrintWriter out = response.getWriter();
        for (Lesson lesson : llist) {
            out.println("                                            <tr>\n"
                    + "                                                <td>" + lesson.getId() + "</td>\n"
                    + "                                                <td>" + lesson.getName() + "</td>\n"
                    + "                                                 " + (lesson.getVideoUrl() == null ? "<td></td>" : "<td><video  style=\"width: 200px;height: 120px;\" src=\"../" + lesson.getVideoUrl() + "\" controls></video></td>")
                    + "                                                \n<td>" + lesson.getStartLearningTime() + "</td>\n"
                    + "                                                <td>" + lesson.getLessonTypeID().getName() + "</td>\n"
                    + "                                                <td>" + lesson.getSubLessonID().getName() + "</td>\n"
                    + "                                                 " + (lesson.isStatus() == true ? "<td>Published</td>" : "<td>Unpublished</td>")
                    + "                                                \n<td>\n"
                    + "                                                       <div class=\"context\">\n"
                    + "                                                        <a href=\"lesson-detail?Lid=" + lesson.getId() + "&type=view\"class=\"text-primary\">View</a>/\n"
                    + "                                                        <a href=\"lesson-detail?Lid=" + lesson.getId() + "\"class=\"text-primary\">Edit</a>/\n"
                    + "                                                        <a href=\"\" class=\"text-danger\" id=\"" + lesson.getId() + "\" >Delete</a>\n"
                    + "                                                        </div>\n"
                    + "                                                  </td>\n"
                    + "                                            </tr>");

        }
    }

    private static ArrayList<Integer> convertStringToArl(HttpServletRequest request, HttpServletResponse response, String str) {
        String[] splitStr = str.split(",");

        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 0; i < splitStr.length; i++) {
            if (!splitStr[i].equals("")) {
                arr.add(Integer.parseInt(splitStr[i]));
            }
        }

        return arr;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Lesson lesson = new LessonDAO().getAllLessonByID(Integer.parseInt(request.getParameter("Lid")));
        new LessonDAO().updateOrderByDelete(lesson.getOrder(), lesson.getCourseID().getCourseId());
        new LessonDAO().deleteLesson(Integer.parseInt(request.getParameter("Lid")));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
