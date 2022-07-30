package controller.quiz;

import dao.AnswerDAO;
import dao.CompletedLessonDAO;
import dao.CompletedQuestionDAO;
import dao.CourseDAO;
import dao.LessonDAO;
import dao.QuestionDAO;
import dao.QuizLessonDAO;
import dao.QuizSessionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Answer;
import model.CompletedLesson;
import model.CompletedQuestion;
import model.Course;
import model.Lesson;
import model.Question;
import model.QuizLesson;
import model.QuizSession;

@WebServlet(name = "ReviewQuizController", urlPatterns = {"/reviewquiz"})
public class ReviewQuizController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/review-quiz.jsp").forward(request, response);;
    } 
    
    private void getResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lessonID = Integer.parseInt(request.getParameter("lID"));
        CompletedLesson CompletedLesson = new CompletedLessonDAO().CompletedLesson(lessonID);
        QuizLesson quiz = new QuizLessonDAO().findById(lessonID);
        QuizSessionDAO QuizSessionDAO = new QuizSessionDAO();
            Account acc = (Account) request.getSession().getAttribute("account");
            QuizSession find = QuizSessionDAO.find(acc.getAccountID(), lessonID);
            if(find != null){
            request.setAttribute("find", find);
            request.setAttribute("lastDoing", CompletedLesson.getEndTime());
            request.setAttribute("score", CompletedLesson.getScore());
            request.setAttribute("total", quiz.getTotalQues());
            request.setAttribute("passScore", quiz.getPassScore());
            }
    }

    private void getQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lessonID = Integer.parseInt(request.getParameter("lID"));
        Account account = (Account)request.getSession().getAttribute("account");
        if (account == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You need to login first");
            return;
        }
        int accountID = account.getAccountID();  
        ArrayList<CompletedQuestion> list = new CompletedQuestionDAO().listAllQuizReview(accountID, lessonID);
        ArrayList<Question> total = new QuestionDAO().total(lessonID);
        ArrayList<Answer> answerList = new AnswerDAO().listAllAnsByQues(lessonID, total.get(0).getId());
        resultColor(request, response, total);

        ArrayList<CompletedQuestion> selectedQues = new CompletedQuestionDAO().listSelectedByQuiz(accountID, lessonID, total.get(0).getId());

        Lesson lesson = new LessonDAO().getLessonByID(lessonID);
        request.setAttribute("lesson", lesson);
        if(selectedQues.size()!=0){
            request.setAttribute("selectAns", selectedQues);
        } else {
            request.setAttribute("selectAns", null);
        }
        
        Course course = new CourseDAO().getCourseByLessonID(lessonID);
        request.setAttribute("course", course);
        
        float average = 10/(float)total.size();
        request.setAttribute("average", average);
        request.setAttribute("score", scoreOfQuestion(request, response, total.get(0).getId(), lessonID, average));

        request.setAttribute("AnswerQuiz", answerList);
        request.setAttribute("FirstQuiz", total.get(0));
        request.setAttribute("SecondQuiz", total.get(1));

        request.setAttribute("totoalQuiz", total);
        request.setAttribute("lessonID", lessonID);
        request.setAttribute("note", answerList.get(0).getExplain());

    }

    private void resultColor(HttpServletRequest request, HttpServletResponse response, ArrayList<Question> list) {
        int lessonID = Integer.parseInt(request.getParameter("lID"));
        ArrayList<String> type = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Answer> ans = new AnswerDAO().listquestionbyQuestionID(lessonID, list.get(i).getId());
            ArrayList<CompletedQuestion> comp = new CompletedQuestionDAO().getSelectedAnswerByQuestion(12, list.get(i).getId());
            if (comp.size() != 0 && comp.size() <= ans.size()) {
                if (ans.size() > 1) {
                    int count = 0;
                    if (ans.size() == comp.size()) {
                        for (int j = 0; j < ans.size(); j++) {
                            if (ans.get(j).getAnswerID() == comp.get(j).getSelectedAnswerID()) {
                                count++;
                            }
                        }
                    }
                    if (count == ans.size()) {
                        type.add("true-answer");
                    } else {
                        type.add("wrong-answer");
                    }
                } else {
                    if (ans.get(0).getAnswerID() == comp.get(0).getSelectedAnswerID() && comp.get(0).getSelectedAnswerID() != 0) {
                        type.add("true-answer");
                    } else if (ans.get(0).getAnswerID() != comp.get(0).getSelectedAnswerID() && comp.get(0).getSelectedAnswerID() != 0) {
                        type.add("wrong-answer");
                    }
                }
            } else {
                if(comp.size() >= ans.size()) type.add("wrong-answer");
                else type.add("");
            }

        }

        request.setAttribute("type", type);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getQuestion(request, response);
        getResult(request, response);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getQuestionPost(request, response);
    }
    
    private String scoreOfQuestion(HttpServletRequest request, HttpServletResponse response, int questionID,int lessonID, float average)
            throws ServletException, IOException {
        float score = 0;
            ArrayList<Answer> ans = new AnswerDAO().listquestionbyQuestionID(lessonID, questionID);
            ArrayList<CompletedQuestion> comp = new CompletedQuestionDAO().getSelectedAnswerByQuestion(12, questionID);
            if (comp.size() != 0 && comp.size() <= ans.size()) {
                if (ans.size() > 1) {
                    float subscore = average/(float)ans.size();
                    if (ans.size() == comp.size()) {
                        for (int j = 0; j < ans.size(); j++) {
                            for (int k = 0; k < ans.size(); k++) { 
                                System.out.println(ans.get(j).getAnswerID()+", "+comp.get(k).getSelectedAnswerID());
                                if (ans.get(j).getAnswerID() == comp.get(k).getSelectedAnswerID()) {
                                    score += subscore;
                                }
                            }
                        }
                    }
                } else {
                    if (ans.get(0).getAnswerID() == comp.get(0).getSelectedAnswerID() && comp.get(0).getSelectedAnswerID() != 0) {
                        score = average;
                    }
                }
            } else {
                score = 0;
            }
            
            String formattedScore = String.format("%.02f", score);
            return formattedScore;
    }

    private void getQuestionPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        int lessonID = Integer.parseInt(request.getParameter("lID"));
        int quiz = Integer.parseInt(request.getParameter("questionID"));
        int quizNumber = Integer.parseInt(request.getParameter("quizNumber"));
        
        ArrayList<Question> total = new QuestionDAO().total(lessonID);
        
        float average = 10/(float)(total.size());
        
        String score = scoreOfQuestion(request, response, quiz, lessonID, average);
        
        ArrayList<Answer> answerList = new AnswerDAO().listAllAnsByQues(lessonID, quiz);
        
        ArrayList<Question> list = new QuestionDAO().listQuestions(lessonID, quiz);
        
        ArrayList<CompletedQuestion> selectedQues = new CompletedQuestionDAO().listSelectedByQuiz(12, lessonID, quiz);
        PrintWriter out = response.getWriter();
        out.println("<div class=\"review-container-left-left\">\n"
                + "                        <h3 class=\"review-container-left-left-title\">Question <span>" + quizNumber + "</span></h3>\n"
                + "                        <span>Complete</span>\n"
                + "                    </div>\n"
                + "                    <div class=\"review-container-left-right\">\n"
                + "                        <div class=\"text-question\">\n"
                + "                            <p>\n"
                + "                                " + list.get(0).getText() + "\n"
                + "                            </p>\n"
                + "                        </div>\n"
                + "                        <div class=\"select-choice\">\n"
                + "                            <div class=\"answer\">\n"
                + "                                <ul>");
        if (selectedQues.size() != 0) {
            for (Answer ans : answerList) {
                out.println("                                            <li ");
                for (CompletedQuestion selectedQue : selectedQues) {
                    out.println((ans.getAnswerID() == selectedQue.getSelectedAnswerID() && ans.getStatus() == 0 ? "class='wrong-choice'" : "")
                            +                                                               (ans.getStatus() == 1 ? "class='right-choice'" : ""));
                }
                    out.println(">\n"
                            + "                                                <input type=\"radio\" ");
                for (CompletedQuestion selectedQue : selectedQues) {
                    out.println(ans.getAnswerID() == selectedQue.getSelectedAnswerID() ? "checked" : "");
                }
                out.println(" disabled>\n"
                            + "                                                <label>"+ ans.getAnswerText() + "</label>\n"
                            + "                                            </li>");
            }
        } else {
            for (Answer ans : answerList) {
                out.println("<li class=\""+ (ans.getStatus() == 1 ? "right-choice" : "") +"\">\n" +
"                                                <input type=\"radio\" disabled>\n" +
"                                                <label>"+ ans.getAnswerText() + "</label>\n" +
"                                                </span>\n" +
"                                            </li>");
            }
        }
        out.println("                                </ul>\n" +
"                            </div>\n" +
"                        </div>\n" +
"                        <div class=\"answer-desc\">"+ answerList.get(0).getExplain() +"</div>\n" +
"                        <div class=\"next-previous-question\">\n" +
"                            <button id=\"btn_next\" class=\"btn-green\" onclick=\"showQuiz("+ total.get((quizNumber == 1 ? (0) : (quizNumber - 2))).getId() + ", " + ((quizNumber == 1 ? (1) : (quizNumber - 1))) + "," + lessonID + ")\"> <i class=\"fa-solid fa-left-long\"></i> Previous Question</button>\n" +
"                            <button id=\"btn_next\" class=\"btn-green\" onclick=\"showQuiz("+ total.get((quizNumber == total.size() ? (quizNumber - 1) : quizNumber)).getId() + ", " + ((quizNumber == total.size() ? (quizNumber) : quizNumber + 1)) + "," + lessonID +")\">Next <i class=\"fa-solid fa-right-long\"></i></button>\n" +
"                        </div>\n" +
"                    </div>");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

