package controller.management;

import dao.CourseDAO;
import dao.DimensionDAO;
import dao.LessonDAO;
import dao.QuestionDAO;
import dao.QuestionLevelDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Course;
import model.Dimension;
import model.Lesson;
import model.Question;
import model.QuestionLevel;


@WebServlet(name = "QuestionListController", urlPatterns = {"/management/QuestionList"})
public class QuestionListController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/question-list-management.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getAllFileter(request, response);
        processRequest(request, response);
    }

    private static void getAllFileter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lessonID = 0;
        if (request.getParameter("Lid") != null) {
            lessonID = Integer.parseInt(request.getParameter("Lid"));
        }

        ArrayList<Lesson> lessonlist = new LessonDAO().getLessonTypeQuiz();
        ArrayList<QuestionLevel> questionlevel = new QuestionLevelDAO().getAllQuestionLevels();
        ArrayList<Course> courselist = new CourseDAO().getAllCourse();

        ArrayList<Question> questions = new QuestionDAO().QuestionByLessonId(lessonID);
        request.setAttribute("lessonlist", lessonlist);
        request.setAttribute("courselist", courselist);
        request.setAttribute("questionlevel", questionlevel);
        request.setAttribute("questions", questions);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        insertTableByFilter(request, response);
    }

    private static void insertTableByFilter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Integer> arrayLesson = convertStringToArl(request, response, request.getParameter("arrayLesson"));
        ArrayList<String> arrayDimension = convertString(request, response, request.getParameter("arrayDimension"));
        int stt = Integer.parseInt(request.getParameter("status"));
        int level = Integer.parseInt(request.getParameter("level"));

        ArrayList<Question> questions = new QuestionDAO().QuestionByFilter(arrayLesson, arrayDimension, stt, level);
        ArrayList<Lesson> lessonlist = new LessonDAO().getLessonTypeQuiz();
        ArrayList<Dimension> dimensionlist = new DimensionDAO().getAllDimensions();

        PrintWriter out = response.getWriter();
        for (Question question : questions) {
            out.print("<tr>\n"
                    + "                                                <td>" + question.getId() + "</td>\n"
                    + "                                                <td  style=\"width: 300px\">" + question.getQuestionText() + "</td>");
            for (Lesson lesson : lessonlist) {
                out.print(question.getLessonId() == lesson.getId() ? "<td>" + lesson.getName() + "</td>" : "");
            }
            out.print("<td>" + question.getLevel().getLevelName() + "</td>\n"
                    + "                                                " + (question.isActive() == true ? "<td>Published</td>" : "<td>Unpublished</td>") + "\n"
                    + "                                                <td>\n"
                    + "                                                    <div class=\"context\">\n"
                    + "                                                        <a href=\"#\" class=\"text-primary\">View</a>/\n"
                    + "                                                        <a href=\"#\" class=\"text-primary\">Edit</a>/\n"
                    + "                                                        <a href=\"#\" class=\"text-danger\" onclick=\"deleteQuestion("+question.getId()+",this)\">Delete</a>\n"
                    + "                                                    </div>\n"
                    + "                                                </td>\n"
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

    private static ArrayList<String> convertString(HttpServletRequest request, HttpServletResponse response, String str) {
        String[] splitStr = str.split(",");

        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < splitStr.length; i++) {
            if (!splitStr[i].equals("")) {
                arr.add(splitStr[i]);
            }
        }

        return arr;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("QID: " + request.getParameter("Qid"));
        new QuestionDAO().deleteQuestion(Integer.parseInt(request.getParameter("Qid")));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
