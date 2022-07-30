/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.management;

import dao.QuizDAO;
import dao.QuizSessionDAO;
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
import model.QuizLesson;
import model.QuizSession;
import model.Subject;

/**
 *
 * @author duc21
 */
@WebServlet(name = "QuizEditController", urlPatterns = {"/management/quizedit"})
public class QuizEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizID = Integer.parseInt(request.getParameter("quizID"));

        QuizDAO QuizDAO = new QuizDAO();
        SubjectDAO SubjectDAO = new SubjectDAO();
        QuizLesson quizLessonDetail = QuizDAO.getQuizLessonDetail(quizID);
        Subject subjectNamebyID = SubjectDAO.getSubjectNamebyID(quizLessonDetail.getSubjectID());
        ArrayList<Subject> allSubjectName = SubjectDAO.getAllSubjectName();
        ArrayList<QuizLesson> allQuizLevel = QuizDAO.getAllQuizLevel();
        
        request.setAttribute("allQuizLevel", allQuizLevel);
        request.setAttribute("allSubjectName", allSubjectName);
        request.setAttribute("subjectNamebyID", subjectNamebyID);
        request.setAttribute("quizLessonDetail", quizLessonDetail);
        request.getRequestDispatcher("../view/quiz-edit.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizID = Integer.parseInt(request.getParameter("quizid"));
        int lessonID = Integer.parseInt(request.getParameter("lessonid"));
        String name = request.getParameter("name");
        int subjectID = Integer.parseInt(request.getParameter("subject"));
        String level = request.getParameter("level");
        int totalques = Integer.parseInt(request.getParameter("totalques"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        int passrate = Integer.parseInt(request.getParameter("passrate"));
        String type = request.getParameter("type");
        
        QuizLesson QuizLesson = new QuizLesson();
        QuizLesson.setLessonID(lessonID);
        QuizLesson.setQuizID(quizID);
        QuizLesson.setName(name);
        QuizLesson.setSubjectID(subjectID);
        QuizLesson.setLevel(level);
        QuizLesson.setPassScore(passrate);
        QuizLesson.setExamTimeInMinute(duration);
        QuizLesson.setType(type);
        QuizLesson.setTotalQues(totalques);
        System.out.println("wghgkfkg" + QuizLesson);
        
        QuizDAO QuizDAO = new QuizDAO();
        QuizDAO.updateQuiz(QuizLesson);
        response.sendRedirect("../management/quizsetting");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
