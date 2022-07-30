/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.management;

import dao.QuizDAO;
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
import model.QuizLesson;
import model.Subject;
import model.TestType;

/**
 *
 * @author duc21
 */
@WebServlet(name = "QuizInsertController", urlPatterns = {"/management/quizinsert"})
public class QuizInsertController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO SubjectDAO = new SubjectDAO();
        ArrayList<Subject> allSubjectName = SubjectDAO.getAllSubjectName();
        QuizDAO QuizDAO = new QuizDAO();
        ArrayList<QuizLesson> allQuizLevel = QuizDAO.getAllQuizLevel();
        TestTypeDAO TestTypeDAO = new TestTypeDAO();
        ArrayList<TestType> allTestTypes = TestTypeDAO.getAllTestTypes();

        request.setAttribute("allTestTypes",allTestTypes);
        request.setAttribute("Lid", request.getParameter("id"));
        request.setAttribute("allSubjectName", allSubjectName);
        request.setAttribute("allQuizLevel", allQuizLevel);
        request.getRequestDispatcher("../view/quiz-insert.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO QuizDAO = new QuizDAO();
        String id_raw = request.getParameter("id");
        String name = request.getParameter("name");
        int subjectID = Integer.parseInt(request.getParameter("subject"));
        String level = request.getParameter("level");
        int totalques = Integer.parseInt(request.getParameter("totalques"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        int passrate = Integer.parseInt(request.getParameter("passrate"));
        String type = request.getParameter("type");
        QuizLesson quizLessonLast = QuizDAO.getQuizLessonLast();
        TestTypeDAO TestTypeDAO = new TestTypeDAO();
        
        QuizLesson QuizLesson = new QuizLesson();
        System.out.println("gfhjrnhg" + quizLessonLast.getQuizID()+1);
        int id = Integer.parseInt(id_raw);
        QuizLesson.setLessonID(id);
        
        
        QuizLesson.setName(name);
        QuizLesson.setSubjectID(subjectID);
        QuizLesson.setLevel(level);
        QuizLesson.setPassScore(passrate);
        QuizLesson.setExamTimeInMinute(duration);
        QuizLesson.setType(TestTypeDAO.getTestTypeByID(Integer.parseInt(type)).getTestTypeName());
        TestType TestType = new TestType();
        TestType.setTestTypeID(Integer.parseInt(type));
        QuizLesson.setTestype(TestType);
        QuizLesson.setTotalQues(totalques);
        
        System.out.println("abc: "+ QuizLesson.getLessonID());
        
        QuizDAO.insertQuizLesson(QuizLesson);
        response.sendRedirect("../management/quizsetting");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
